package com.leyou.search;

import com.leyou.pojo.SpecParam;
import com.leyou.search.client.SpecClient;
import com.leyou.search.client.SpuClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoodTest {
    @Autowired
    SpuClient spuClient;
    @Autowired
    SpecClient specClient;
    @Test
    public void GoodTest(){
        List<SpecParam> specParamByCidAndSearching = specClient.findSpecParamByCidAndSearching(76L);
        specParamByCidAndSearching.forEach(specid->{
            System.out.println(specid.getId());
        });
    }


}
