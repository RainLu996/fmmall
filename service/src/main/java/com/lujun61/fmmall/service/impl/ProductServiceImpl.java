package com.lujun61.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.beans.entity.*;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.*;
import com.lujun61.fmmall.service.ProductService;
import com.lujun61.fmmall.vo.ResultVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    // SLF4J日志规范：项目中引入了哪个实现类，这里就会以对应的实现类记录日志
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    ProductMapper productMapper;

    @Resource
    ProductImgMapper productImgMapper;

    @Resource
    ProductSkuMapper productSkuMapper;

    @Resource
    ProductParamsMapper productParamsMapper;

    @Resource
    ProductCommentsMapper productCommentsMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RestHighLevelClient restHighLevelClient;

    @Resource
    private ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryRecommendProducts() {
        List<ProductDetail> productDetails = productMapper.selectRecommendProducts();

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", productDetails);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryProductBaseInfoById(String productId) throws JsonProcessingException {

        // 1、首先根据商品id查询Redis
        String productJson = (String) stringRedisTemplate.boundHashOps("product").get(productId);

        // 2、若从Redis中查询到了数据，则直接返回给Controller
        if (productJson != null) {  // 继续从Redis中读取商品信息

            // 获取商品详细信息
            Product product = objectMapper.readValue(productJson, Product.class);

            // 获取商品图片详细信息
            String productImgsJson = (String) stringRedisTemplate.boundHashOps("productImgs").get(productId);
            JavaType javaType1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
            List<ProductImg> productImgs = objectMapper.readValue(productImgsJson, javaType1);

            // 获取商品套餐详细信息
            String productSkusJson = (String) stringRedisTemplate.boundHashOps("productSkus").get(productId);
            JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
            List<ProductSku> productSkus = objectMapper.readValue(productSkusJson, javaType2);

            // 封装为map
            HashMap<String, Object> baseProductInfo = new HashMap<>();
            baseProductInfo.put("product", product);
            baseProductInfo.put("productImgs", productImgs);
            baseProductInfo.put("productSkus", productSkus);

            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", baseProductInfo);

            // 3、若未从Redis中查询到数据，则查询数据库：封装数据返回给Controller以及缓存至Redis
        } else {

            Product product = productMapper.selectProductByPrimaryKeyAndStatus(productId, 1);

            // 数据库中存在商品信息
            if (product != null) {

                stringRedisTemplate.boundHashOps("product").put(productId, objectMapper.writeValueAsString(product));

                List<ProductImg> productImgs = productImgMapper.selectProductImgsByProductId(productId);
                stringRedisTemplate.boundHashOps("productImgs").put(productId, objectMapper.writeValueAsString(productImgs));

                List<ProductSku> productSkus = productSkuMapper.selectProductSkuByPrimaryKeyAndStatus(productId, 1);
                stringRedisTemplate.boundHashOps("productSkus").put(productId, objectMapper.writeValueAsString(productSkus));

                HashMap<String, Object> baseProductInfo = new HashMap<>();
                baseProductInfo.put("product", product);
                baseProductInfo.put("productImgs", productImgs);
                baseProductInfo.put("productSkus", productSkus);

                return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", baseProductInfo);
            } else {
                return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "所查询的商品不存在", null);
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryProductParamsById(String productId) {
        ProductParams productParams = productParamsMapper.selectProductParamsByProductId(productId);

        if (productParams != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", productParams);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "三无产品", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryDetailProductCommentsByProductId(String productId) {
        List<DetailProductComments> detailProductComments = productCommentsMapper.selectDetailProductCommentsByProductId(productId);


        if (detailProductComments != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", detailProductComments);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "商品暂无评论", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo pageQueryDetailProductCommentsByProductId(String productId, int pageNum, int pageSize) {

        int count = productCommentsMapper.selectProductCommentCountByProductId(productId);

        int start = (pageNum - 1) * pageSize;

        int pageCount = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;

        List<DetailProductComments> detailProductComments = productCommentsMapper.pageSelectDetailProductCommentsByProductId(productId, start, pageSize);

        if (detailProductComments != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", new PageHelper<>(pageCount, count, detailProductComments));
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "商品暂无评论", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo getMultiClassificationCommentsCountByProductId(String productId) {

        int count = productCommentsMapper.selectProductCommentCountByProductId(productId);

        if (count != 0) {
            int niceCount = productCommentsMapper.selectCountForNiceCommentByProductId(productId);

            int midCount = productCommentsMapper.selectCountForMidCommentByProductId(productId);

            int badCount = count - niceCount - midCount;

            double favorableRate = ((double) niceCount / count) * 100;

            String favorableRateStr = String.format("%.2f", favorableRate);

            Map<String, Object> commentsCountMap = new HashMap<>();
            commentsCountMap.put("count", count);
            commentsCountMap.put("niceCount", niceCount);
            commentsCountMap.put("midCount", midCount);
            commentsCountMap.put("badCount", badCount);
            commentsCountMap.put("favorableRate", favorableRateStr);

            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", commentsCountMap);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "商品暂无评论", null);
        }
    }

    @Override
    public ResultVo pageQueryDetailProductByCategoryId(String categoryId, int pageNum, int pageSize) {

        int start = (pageNum - 1) * pageSize;
        int count = productMapper.selectCountDetailProductByCategoryId(Integer.parseInt(categoryId));
        int pageCount = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;

        List<ProductDetail> productDetails = productMapper.selectDetailProductByCategoryId(Integer.parseInt(categoryId), start, pageSize);

        if (productDetails != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", new PageHelper<>(pageCount, count, productDetails));
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "此类别下无商品", null);
        }
    }

    @Override
    public ResultVo queryBrandByCategoryId(String categoryId) {
        List<String> brands = productParamsMapper.selectBrandByCategoryId(Integer.parseInt(categoryId));

        if (brands != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", brands);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "此类别下无商品", null);
        }
    }

    @Override
    public ResultVo pageVagueQueryDetailProduct(String keyword, int pageNum, int pageSize) {

        int start = (pageNum - 1) * pageSize;

        PageHelper<Product4ES> pageHelper = new PageHelper<>();

        /* 从ES中获取数据 */
        try {
            // 获取索引
            SearchRequest searchRequest = new SearchRequest("product_search_index");

            // 封装查询条件
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.multiMatchQuery(keyword, "productName", "productSkuName"));

            // 分页显示
            sourceBuilder.from(start);
            sourceBuilder.size(pageSize);

            // 高亮显示
            HighlightBuilder HLBuilder = new HighlightBuilder();
            HighlightBuilder.Field field1 = new HighlightBuilder.Field("productName");
            HighlightBuilder.Field field2 = new HighlightBuilder.Field("productSkuName");
            HLBuilder.field(field1);
            HLBuilder.field(field2);
            HLBuilder.preTags("<lable style='color: red; font-weight: bold'>");
            HLBuilder.postTags("</lable>");
            sourceBuilder.highlighter(HLBuilder);

            searchRequest.source(sourceBuilder);

            // 执行检索
            SearchResponse res = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            // 获取查询结果数据集
            SearchHits hits = res.getHits();

            // 获取命中数据条数
            int count = (int) hits.getTotalHits().value;
            int pageCount = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;

            Iterator<SearchHit> SHIterator = hits.iterator();
            List<Product4ES> product4ESList = new LinkedList<>();
            while (SHIterator.hasNext()) {
                SearchHit product = SHIterator.next();

                // 获取高亮字段
                Map<String, HighlightField> highlightFields = product.getHighlightFields();
                HighlightField HLproductName = highlightFields.get("productName");
                HighlightField HLproductSkuName = highlightFields.get("productSkuName");

                String productSkuName = null;
                if (HLproductSkuName != null) {
                    String fragment1 = Arrays.toString(HLproductSkuName.fragments());
                    productSkuName = fragment1.replace("[", "").replace("]", "");
                }
                String productName = null;
                if (HLproductName != null) {
                    String fragment2 = Arrays.toString(HLproductName.fragments());
                    productName = fragment2.replace("[", "").replace("]", "");
                }

                // 将高亮字段重新设置到封装对象中
                Product4ES product4ES = objectMapper.readValue(product.getSourceAsString(), Product4ES.class);
                product4ES.setProductSkuName(productSkuName);
                product4ES.setProductName(productName);

                // 封装最终 高亮商品信息 集合
                product4ESList.add(product4ES);
            }

            // 封装返回数据
            pageHelper.setCount(count);
            pageHelper.setPageCount(pageCount);
            pageHelper.setList(product4ESList);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (pageHelper.getCount() > 0) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", pageHelper);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "无类似商品", null);
        }

    }

    @Override
    public ResultVo vagueQueryBrand(String keyword) {

        List<String> brands = productParamsMapper.vagueSelectBrand("%" + keyword + "%");

        if (brands != null) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", brands);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "无类似商品", null);
        }
    }

    @Override
    public List<ProductDetail> selectProductForES() {
        return productMapper.selectProductForES();
    }
}
