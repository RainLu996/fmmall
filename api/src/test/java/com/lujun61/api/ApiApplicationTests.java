package com.lujun61.api;

import com.lujun61.ApiApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class})
class ApiApplicationTests {

    @Test
    void test01() {

        System.out.println("HelloWorld");

    }

}
