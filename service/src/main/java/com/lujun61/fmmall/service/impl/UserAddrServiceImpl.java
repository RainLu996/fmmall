package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.UserAddr;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.UserAddrMapper;
import com.lujun61.fmmall.service.UserAddrService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userAddrService")
public class UserAddrServiceImpl implements UserAddrService {

    @Resource
    UserAddrMapper userAddrMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listAddrsByUid(String userId) {

        List<UserAddr> userAddrs = userAddrMapper.selectAddrsByUid(userId);

        if (userAddrs.size() > 0) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", userAddrs);
        } else {
            return new ResultVo(Constants.USER_ADDR_EMPTY, "未查询到地址信息", null);
        }

    }
}
