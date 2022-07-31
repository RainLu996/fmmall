package com.lujun61.fmmall.service;

import com.lujun61.fmmall.vo.ResultVo;

public interface CategoryService {

    ResultVo queryAllCategoryByJoinSelect();


    ResultVo queryAllCategoryByChildrenSelect();

}
