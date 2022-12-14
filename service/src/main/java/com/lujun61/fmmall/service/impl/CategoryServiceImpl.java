package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.MultiCategory;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.CategoryMapper;
import com.lujun61.fmmall.service.CategoryService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryAllCategoryByJoinSelect() {

        List<MultiCategory> multiCategories = categoryMapper.selectAllCategory();

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", multiCategories);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryAllCategoryByChildrenSelect() {
        List<MultiCategory> multiCategories = categoryMapper.childrenSelectAllCategory(0);

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", multiCategories);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryFirstLevelCascadeTopSixCategoriesAndProducts() {
        List<MultiCategory> multiCategories = categoryMapper.selectFirstLevelCascadeTopSixCategoriesAndProducts();

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", multiCategories);
    }
}
