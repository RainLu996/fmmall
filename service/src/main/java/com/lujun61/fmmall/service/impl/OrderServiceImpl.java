package com.lujun61.fmmall.service.impl;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;
import com.lujun61.fmmall.dao.ShoppingCartMapper;
import com.lujun61.fmmall.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    ShoppingCartMapper shoppingCartMapper;

    @Override
    public void addOrder(String cids, Orders order) {

        // 1、检验库存：根据cids查询用户选中的购物车商品详细信息
        String[] cidArry = cids.split(",");
        List<DetailShoppingCart> detailShoppingCarts = shoppingCartMapper.selectShopcartByCartIds(Arrays.asList(cidArry));

        boolean isCheck = false;
        for (DetailShoppingCart sc : detailShoppingCarts) {
            if (Integer.parseInt(sc.getStock()) < Integer.parseInt(sc.getCartNum())) {
                isCheck = true;
            }
        }

        // 只有库存足够，才继续进行业务处理
        if (!isCheck) {

        }
    }
}
