package com.lujun61.orderadd.service.impl;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;
import com.lujun61.orderadd.dao.OrderAddMapper;
import com.lujun61.orderadd.service.OrderAddService;
import com.lujun61.orderadd.service.feign.StockQueryClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("orderAddService")
public class OrderAddServiceImpl implements OrderAddService {

    @Resource
    OrderAddMapper orderAddMapper;

    @Resource
    StockQueryClient stockQueryClient;

    @Override
    public List<DetailShoppingCart> insertOrder(Orders order, String cids) {

        boolean isCheck = false;
        StringBuilder untitle = new StringBuilder();  // 订单表中的untitle字段信息

        // 1、检验库存
        List<DetailShoppingCart> sc = stockQueryClient.queryStock(cids);
        for (int i = 0; i < sc.size(); i++) {
            // 任意一个购物车商品的库存不足，即视为无法继续提交订单
            if (Integer.parseInt(sc.get(i).getStock()) < Integer.parseInt(sc.get(i).getCartNum())) {
                isCheck = true;
                break;
            }
            untitle.append(i == sc.size() - 1 ? sc.get(i).getProductName() : sc.get(i).getProductName() + ",");
        }

        // 只有库存足够，才继续进行业务处理
        if (!isCheck) {
            // 2、进一步封装订单信息并保存订单
            /* 此处order-add服务只负责将order数据保存至数据库，而不负责生成 唯一订单id。这个工作应该由服务消费者完成 */
            //  order.setOrderId(UUIDUtils.getUUID());
            order.setUntitled(untitle.toString());
            order.setCreateTime(new Date());
            order.setStatus("1");  // 初始订单状态为“未付款”

            int res = orderAddMapper.insertOrder(order);
            if (res > 0) {
                // 由于业务需求中需要order-add服务返回“订单详细信息的集合”，所以此处查询出来就应该返回。避免多个服务再去查询
                return sc;
            }

        }

        // 不论库存充足还是订单添加失败，都返回null。
        return null;

    }
}
