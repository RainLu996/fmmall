package com.lujun61.shopcartdel.controller;


import com.lujun61.shopcartdel.service.ShopcartDelService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order/stock")
public class ShopcartDelController {

    @Resource
    private ShopcartDelService shopcartDelService;

    @DeleteMapping("/delete")
    public int deleteShopcart(@RequestParam("cids") String cids) {
        return shopcartDelService.removeShopcartInfo(cids);
    }

}
