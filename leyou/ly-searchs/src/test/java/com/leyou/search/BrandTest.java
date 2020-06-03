package com.leyou.search;

import com.leyou.pojo.Brand;
import com.leyou.search.client.BrandClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BrandTest {
    @Autowired
    BrandClient brandClient;
    @Test
    public void BrandTest(){
        Brand brandById = brandClient.findByBrandId(2032L);
        System.out.println(brandById);
    }
}
