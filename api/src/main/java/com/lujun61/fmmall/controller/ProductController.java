package com.lujun61.fmmall.controller;

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
    public ResultVo topSixProducts(@PathVariable("pid") String pid){
        return productService.queryProductBaseInfoById(pid);
    }

    @GetMapping("/params/{pid}")
    @ApiOperation("商品参数信息查询接⼝")
    public ResultVo productParams(@PathVariable("pid") String pid){
        return productService.queryProductParamsById(pid);
    }

    @GetMapping("/comments/{pid}")
    @ApiOperation("商品评论信息查询接⼝")
    public ResultVo productDetailComments(@PathVariable("pid") String pid){
        return productService.queryDetailProductCommentsByProductId(pid);
    }

    @GetMapping("/comments_page/{pid}")
    @ApiOperation("商品评论信息分页查询接⼝")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "pageSize", value = "每页显示条数", required = true)
    })
    public ResultVo pageProductDetailComments(@PathVariable("pid") String pid, int pageNum, int pageSize
    ){
        return productService.pageQueryDetailProductCommentsByProductId(pid, pageNum, pageSize);
    }

    @GetMapping("/multicomments_num/{pid}")
    @ApiOperation("商品多类别评论数量查询接⼝")
    public ResultVo multiCommentsStatistic(@PathVariable("pid") String pid){
        return productService.getMultiClassificationCommentsCountByProductId(pid);
    }
}
