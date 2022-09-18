package com.lujun61.fmmall.service;

import com.lujun61.beans.entity.ShoppingCart;
import com.lujun61.fmmall.vo.ResultVo;

public interface ShopcartService {

    ResultVo addProductToShopcart(ShoppingCart shoppingCart);

    ResultVo queryShopcartDetailInfoByUserId(String userId);

    ResultVo updateShopcartCartnumByCartId(String cartId, String cartNum);

    ResultVo queryShopcartByCartIds(String multiCartIdStr);

    ResultVo deleteShopcartByCartId(String cartId);

    ResultVo batchDeleteShopcartByCartIds(String multiCartIdStr);

}
