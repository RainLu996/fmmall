package com.lujun61.fmmall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.service.CategoryService;
import com.lujun61.fmmall.service.IndexImgService;
import com.lujun61.fmmall.service.ProductService;
import com.lujun61.fmmall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/index")
@CrossOrigin
@Api(value = "提供首页数据显示所需的接口", tags = "首页管理")
public class IndexController {

    @Resource
    IndexImgService indexImgService;

    @Resource
    CategoryService categoryService;

    @Resource
    ProductService productService;

    @GetMapping("/indeximg")
    @ApiOperation("首页轮播图接口")
    public ResultVo indexImgList(@RequestHeader("token") String token) {

        try {
            return indexImgService.queryIndexImgList();
        } catch (JsonProcessingException e) {
           return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "fail", null);
        }

    }


    @GetMapping("/categories")
    @ApiOperation("商品分类查询接口")
    public ResultVo listCatetory() {
        return categoryService.queryAllCategoryByChildrenSelect();
        //return categoryService.queryAllCategoryByJoinSelect();
    }

    @GetMapping("/recommend_new")
    @ApiOperation("今日推荐商品查询接口")
    public ResultVo listRecommendProducts() {
        return productService.queryRecommendProducts();
    }

    @GetMapping("/recommend_classification")
    @ApiOperation("分类推荐商品查询接口")
    public ResultVo topSixProducts() {
        return categoryService.queryFirstLevelCascadeTopSixCategoriesAndProducts();
    }

}
