package com.lujun61.fmmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.service.ProductService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/product")
@Api(value = "提供商品信息相关的接口", tags = "商品管理")
public class ProductController {

    @Resource
    ProductService productService;

    @GetMapping("/detail-info/{pid}")
    @ApiOperation("商品基本信息查询接⼝")
    public ResultVo topSixProducts(@PathVariable("pid") String pid, @RequestHeader("token") String token) {
        ResultVo resultVo = new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "fail", null);

        try {
            resultVo = productService.queryProductBaseInfoById(pid);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultVo;
    }

    @GetMapping("/params/{pid}")
    @ApiOperation("商品参数信息查询接⼝")
    public ResultVo productParams(@PathVariable("pid") String pid) {
        return productService.queryProductParamsById(pid);
    }

    @GetMapping("/comments/{pid}")
    @ApiOperation("商品评论信息查询接⼝")
    public ResultVo productDetailComments(@PathVariable("pid") String pid) {
        return productService.queryDetailProductCommentsByProductId(pid);
    }

    @GetMapping("/comments_page/{pid}")
    @ApiOperation("商品评论信息分页查询接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页显示条数", required = true)
    })
    public ResultVo pageProductDetailComments(@PathVariable("pid") String pid, int pageNum, int pageSize
    ) {
        return productService.pageQueryDetailProductCommentsByProductId(pid, pageNum, pageSize);
    }

    @GetMapping("/multicomments_num/{pid}")
    @ApiOperation("商品多类别评论数量查询接⼝")
    public ResultVo multiCommentsStatistic(@PathVariable("pid") String pid) {
        return productService.getMultiClassificationCommentsCountByProductId(pid);
    }

    @GetMapping("/products_page/{cid}")
    @ApiOperation("指定三级类别下商品信息分页查询接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页显示条数", required = true)
    })
    public ResultVo pageProductDetail(@PathVariable("cid") String cid, int pageNum, int pageSize
    ) {
        return productService.pageQueryDetailProductByCategoryId(cid, pageNum, pageSize);
    }

    @GetMapping("/brand/{cid}")
    @ApiOperation("指定三级类别下商品品牌查询接⼝")
    public ResultVo brand(@PathVariable("cid") String cid) {
        return productService.queryBrandByCategoryId(cid);
    }

    @GetMapping("/products_vague_page")
    @ApiOperation("全局模糊分页查询商品信息接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "keyword", value = "查询关键字", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页显示条数", required = true)
    })
    public ResultVo vaguePageProductDetail(String keyword, int pageNum, int pageSize) {
        return productService.pageVagueQueryDetailProduct(keyword, pageNum, pageSize);
    }

    @GetMapping("/vague_brand")
    @ApiOperation("全局模糊查询商品品牌接⼝")
    @ApiImplicitParam(dataType = "String", name = "keyword", value = "查询关键字", required = true)
    public ResultVo brandVagueQuery(String keyword) {
        return productService.vagueQueryBrand(keyword);
    }
}
