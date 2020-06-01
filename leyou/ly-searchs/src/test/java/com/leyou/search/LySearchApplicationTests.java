package com.leyou.search;

import com.leyou.Bo.SpuBo;
import com.leyou.common.PageResult;
import com.leyou.search.client.SpuClient;
import com.leyou.search.item.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.servcie.GoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LySearchApplicationTests {
    @Autowired
    SpuClient spuClient;
    @Autowired
    GoodService goodService;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    public void contextLoads() {

        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        PageResult<SpuBo> pageResult = spuClient.findSpuByPage("", 1, 200, 2);
        pageResult.getItems().forEach(spuBo -> {
            System.out.println(spuBo.getId());
            try {
                Goods goods = goodService.convert(spuBo);
                goodsRepository.save(goods);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
