package com.lujun61.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.ApiApplication;
import com.lujun61.beans.entity.Product4ES;
import com.lujun61.beans.entity.ProductDetail;
import com.lujun61.beans.entity.ProductSku;
import com.lujun61.fmmall.service.ProductService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jun Lu
 * @description 从数据库向ES中导入数据
 * @date 2022-08-24 22:01:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class})
public class ImportDataToES {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ProductService productService;

    @Test
    public void importData() {

        // 从数据库中获取需要存入ES中的数据
        List<ProductDetail> productDetails = productService.selectProductForES();

        // 封装数据
        List<Product4ES> product4ESList = new LinkedList<>();
        Product4ES product4ES = null;
        for (ProductDetail productDetail : productDetails) {
            product4ES = new Product4ES();
            product4ES.setProductId(productDetail.getProductId());
            product4ES.setProductName(productDetail.getProductName());
            product4ES.setSoldNum(productDetail.getSoldNum());

            List<ProductSku> skus = productDetail.getSkus();
            product4ES.setProductSkuName(skus.size() > 0 ? skus.get(0).getSkuName() : "");
            product4ES.setProductImg(skus.size() > 0 ? skus.get(0).getSkuImg() : "");
            product4ES.setProSkuPrice(skus.size() > 0 ? skus.get(0).getSellPrice() : 0);

            product4ESList.add(product4ES);
        }

        try {
            // 创建索引
            CreateIndexRequest ci = new CreateIndexRequest("product_search_index");
            restHighLevelClient.indices().create(ci, RequestOptions.DEFAULT);

            // 向ES中添加数据
            IndexRequest indexRequest = new IndexRequest("product_search_index");
            for (Product4ES ele : product4ESList) {
                indexRequest.id(ele.getProductId());
                indexRequest.source(objectMapper.writeValueAsString(ele), XContentType.JSON);
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
