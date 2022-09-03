package com.lujun61.shopcartdel.service.impl;

import com.lujun61.shopcartdel.dao.ShopcartDelMapper;
import com.lujun61.shopcartdel.service.ShopcartDelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("shopcartDelService")
public class ShopcartDelServiceImpl implements ShopcartDelService {

    @Resource
    private ShopcartDelMapper shopcartDelMapper;

    @Override
    public int removeShopcartInfo(String cids) {
        String[] cidArray = cids.split(",");

        int ret = 1;
        for (String cid : cidArray) {
            int res = shopcartDelMapper.deleteShopcartByCid(cid);

            ret *= res;
        }

        return ret;
    }
}
