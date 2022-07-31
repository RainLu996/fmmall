package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.MultiCategory;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.CategoryMapper;
import com.lujun61.fmmall.service.CategoryService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;


    @Override
    public ResultVo queryAllCategoryByJoinSelect() {

        List<MultiCategory> multiCategories = categoryMapper.selectAllCategory();

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", multiCategories);
    }

    @Override
    public ResultVo queryAllCategoryByChildrenSelect() {
        List<MultiCategory> multiCategories = categoryMapper.childrenSelectAllCategory(0);

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", multiCategories);
    }
}
