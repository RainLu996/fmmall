package com.lujun61.ordersubmit.service.impl;

import com.lujun61.beans.entity.DetailShoppingCart;
import com.lujun61.beans.entity.Orders;
import com.lujun61.beans.entity.ProductSku;
import com.lujun61.fmmall.utils.UUIDUtils;
import com.lujun61.ordersubmit.service.OrderSubmitService;
import com.lujun61.ordersubmit.service.feign.OrderAddClient;
import com.lujun61.ordersubmit.service.feign.OrderItemAddClient;
import com.lujun61.ordersubmit.service.feign.ShopCartDelClient;
import com.lujun61.ordersubmit.service.feign.StockUpdateClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("orderSubmitService")
public class OrderSubmitServiceImpl implements OrderSubmitService {

    @Resource
    private OrderAddClient orderAddClient;

    @Resource
    private OrderItemAddClient orderItemAddClient;

    @Resource
    private StockUpdateClient stockUpdateClient;

    @Resource
    private ShopCartDelClient shopCartDelClient;


    @Override
    public Map<String, String> orderSubmit(Orders orders, String cids) {

        // 未申请支付链接map，只有用户支付界面回馈给用户的一些商品基本信息
        HashMap<String, String> res = null;

        // 用于拼接订单中所有商品的ProductName
        StringBuilder untitle = new StringBuilder();

        // 1、校验库存-保存订单
        /* 订单编号需要放到此处生成！！！！ */
        String orderId = UUIDUtils.getUUID();
        orders.setOrderId(orderId);
        List<DetailShoppingCart> sc = orderAddClient.orderAdd(orders, cids);

        if (sc != null) {

            // 2、保存订单快照信息
            int isOrderItem = orderItemAddClient.addOrderItem(sc, orderId);

            // 3、修改库存
            if (isOrderItem > 0) {
                List<ProductSku> productSkuList = new LinkedList<>();
                for (DetailShoppingCart item : sc) {
                    String skuId = item.getSkuId();
                    Integer newStock = Integer.parseInt(item.getStock()) - Integer.parseInt(item.getCartNum());

                    ProductSku productSku = new ProductSku();
                    productSku.setSkuId(skuId);
                    productSku.setStock(newStock);

                    productSkuList.add(productSku);
                }
                int isStockUpdate = stockUpdateClient.updateStock(productSkuList);

                // 4、删除购物车信息
                if (isStockUpdate > 0) {
                    int isShopCartDel = shopCartDelClient.deleteShopcart(cids);
                    if (isShopCartDel > 0) {
                        // 封装返回给用户的订单基本信息
                        res.put("orderId", orderId);

                        for (int i = 0; i < sc.size(); i++) {
                            untitle.append(i == sc.size() - 1 ? sc.get(i).getProductName() : sc.get(i).getProductName() + ",");
                        }
                        res.put("productNames", untitle.toString());
                    }
                }
            }
        }
        return res;
    }
}
