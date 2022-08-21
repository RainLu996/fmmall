package com.lujun61.fmmall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lujun61.fmmall.vo.ResultVo;

public interface IndexImgService {

    ResultVo queryIndexImgList() throws JsonProcessingException;

}
