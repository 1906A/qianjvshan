package com.leyou.search.controller;

import com.leyou.common.PageResult;
import com.leyou.search.item.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.repository.GoodsRepository;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("search")
public class SearchController {

    @Autowired
    GoodsRepository goodsRepository;
    @RequestMapping("page")
    public PageResult<Goods> page(@RequestBody SearchRequest searchRequest){

        System.out.println(searchRequest.getKey()+"________________"+searchRequest.getPage());
        //去es索引库做查询
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //构造条件
        builder.withQuery(QueryBuilders.matchQuery("all",searchRequest.getKey()).operator(Operator.AND));
        //分页查询
        builder.withPageable(PageRequest.of(searchRequest.getPage()-1,searchRequest.getSize()));
        //执行查询
        Page<Goods> search = goodsRepository.search(builder.build());

        return new PageResult<Goods>(search.getTotalElements(), search.getContent(), search.getTotalPages());
    }



}
