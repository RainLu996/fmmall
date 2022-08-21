package com.lujun61.fmmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lujun61.beans.entity.IndexImg;
import com.lujun61.fmmall.constant.Constants;
import com.lujun61.fmmall.dao.IndexImgMapper;
import com.lujun61.fmmall.service.IndexImgService;
import com.lujun61.fmmall.vo.ResultVo;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("indexImgService")
public class IndexImgServiceImpl implements IndexImgService {

    @Resource
    IndexImgMapper indexImgMapper;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo queryIndexImgList() throws JsonProcessingException {

        // 第一次查询Redis
        String indexImgsJson1 = stringRedisTemplate.boundValueOps("indexImgs").get();

        List<IndexImg> indexImgs = null;

        // 若Redis中存在数据
        if (indexImgsJson1 != null) {

            // 封装、返回数据
            JavaType javaType1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
            indexImgs = objectMapper.readValue(indexImgsJson1, javaType1);

        } else {

            // 当产生缓存击穿时，通过加锁产生串行操作，以控制第一个查询才能查询数据库。【双重检测锁】
            synchronized (this) {

                // 第二次查询Redis
                String indexImgsJson2 = stringRedisTemplate.boundValueOps("indexImgs").get();

                // Redis中没有数据，此时已经加锁，可以限制只有第一个请求才能查询数据库。并将数据保存至Redis，并返回给Controller
                if (indexImgsJson2 == null) {

                    // 如果这里查询的数据在数据库中并不存在，那么无论返回还是存入Redis中的数据都是null。
                    // 这仍然会导致大量并发请求去请求数据库。产生缓存穿透。
                    indexImgs = indexImgMapper.selectIndexImgList();
                    if (indexImgs == null) {

                        //当从数据库查询出的数据为null时，保存⼀个⾮空数据到redis，并设置过期时间。过期时间不可以太长。
                        stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString("[]"));
                        stringRedisTemplate.boundValueOps("indexImgs").expire(10, TimeUnit.SECONDS);

                    } else {

                        stringRedisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs));
                        stringRedisTemplate.boundValueOps("indexImgs").expire(1, TimeUnit.DAYS);  // 1天到期

                    }

                // 如果Redis中有了数据（由高并发的第一次请求所存入），就取出Redis中的数据，并返回
                } else {
                    JavaType javaType2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                    indexImgs = objectMapper.readValue(indexImgsJson2, javaType2);
                }
            }
        }

        //返回数据
        if(indexImgs != null){
            return new ResultVo(Constants.RETURN_OBJECT_CODE_SUCCESS, "success", indexImgs);
        }else{
            return new ResultVo(Constants.RETURN_OBJECT_CODE_FAIL, "fail", null);
        }

    }
}
