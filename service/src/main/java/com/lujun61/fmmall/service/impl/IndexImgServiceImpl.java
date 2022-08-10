package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.IndexImg;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.IndexImgMapper;
import com.lujun61.fmmall.service.IndexImgService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("indexImgService")
public class IndexImgServiceImpl implements IndexImgService {

    @Resource
    IndexImgMapper indexImgMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryIndexImgList() {
        List<IndexImg> indexImgs = indexImgMapper.selectIndexImgList();

        if (indexImgs.size() == 0) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "fail", null);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", indexImgs);
        }
    }
}
