package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.ShoppingCart;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.ShoppingCartMapper;
import com.lujun61.fmmall.service.ShopcartService;
import com.lujun61.fmmall.utils.UUIDUtils;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("shopcartService")
public class ShopcartServiceImpl implements ShopcartService {

    @Resource
    ShoppingCartMapper shoppingCartMapper;

    @Transactional
    public ResultVo addProductToShopcart(ShoppingCart shoppingCart) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        shoppingCart.setCartId(UUIDUtils.getUUID());
        shoppingCart.setCartTime(format.format(new Date()));

        int res = shoppingCartMapper.insert(shoppingCart);

        if (res > 0) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", null);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "添加购物车失败", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryShopcartDetailInfoByUserId(String userId) {
        List<DetailShoppingCart> detailShoppingCarts = shoppingCartMapper.selectShoppingCartByUserId(userId);

        if (detailShoppingCarts.size() > 0) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", detailShoppingCarts);
        } else {
            return new ResultVo(Constants.SHOPPINGCART_EMPTY, "啊呀~购物车空空如也", null);
        }
    }

    @Transactional
    public ResultVo updateShopcartCartnumByCartId(String cartId, String cartNum) {
        int res = shoppingCartMapper.updateCartnumByCartid(cartId, cartNum);

        if (res > 0) {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", null);
        } else {
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "fail", null);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryShopcartByCartIds(String multiCartIdStr) {

        String[] cartIds = multiCartIdStr.split(",");

        List<DetailShoppingCart> detailShoppingCarts = shoppingCartMapper.selectShopcartByCartIds(Arrays.asList(cartIds));

        return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", detailShoppingCarts);

    }
}
