package com.leyou.controller;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsDetailController {
    @Autowired
    SpuClient spuClient;
    @Autowired
    SkuClient skuClient;
    @Autowired
    SpecGroupClient specGroupClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    SpecParamClient specParamClient;
    @Autowired
    BrandClient brandClient;

    @RequestMapping("hello")
    public String hello(Model model){
        String name = "张三";
        model.addAttribute("name",name);
        return "hello";
    }
    /**
     * 请求商品详情的微服务
     * 1：spu
     * 2：spudetail
     * 3：sku
     * 4：规格参数组
     * 5：规格参数详情
     * 6：三级分类
     *
     * @param spuId
     * @param model
     * @return
     */
    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId") Long spuId,Model model){
        //1:spu
        Spu spu = spuClient.findSpuBuId(spuId);

        //2：spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuId);

        //3:sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuId);

        //4:查询规格参数组 及组内信息
        List<SpecGroup> specGroupList = specGroupClient.findSpecGroupList(spu.getCid3());

        //5:三级分类  cid  + generic =0    List
        List<Category> categoryList = categoryClient.findCategoryByCids(
                Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));

        //6:规格参数详情   category   id   name
        List<SpecParam> specParamList = specParamClient.findParamByCidAndGeneric(spu.getCid3(),false);
        //规格参数的特殊属性
        Map<Long,String> paramMap = new HashMap<>();
        specParamList.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });

        //7.查询品牌
        Brand brand = brandClient.findByBrandId(spu.getBrandId());

        model.addAttribute("spu",spu);
        model.addAttribute("spuDetail",spuDetail);
        model.addAttribute("skuList",skuList);
        model.addAttribute("specGroupList",specGroupList);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("paramMap",paramMap);
        model.addAttribute("brand",brand);

        return "item";
    }
}
