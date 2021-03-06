package com.leyou.controller;

import com.leyou.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class GoodsDetailController {

    @Autowired
    GoodService goodService;

    @RequestMapping("hello")
    public String hello(Model model){
        String name = "张三";
        model.addAttribute("name",name);
        return "hello";
    }
    /**
     * 通过thyemleaf实现页面静态化
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
       /** //1:spu
        Spu spu = spuClient.findSpuBuId(spuId);
        //2：spudetail
        SpuDetail spuDetail = spuClient.findSpuDetailBySpuId(spuId);
        //3:sku
        List<Sku> skuList = skuClient.findSkuBySpuId(spuId);
        //4:查询规格参数组 及组内信息
        List<SpecGroup> groups = specGroupClient.findSpecGroupList(spu.getCid3());
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
        model.addAttribute("groups",groups);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("paramMap",paramMap);
        model.addAttribute("brand",brand);*/

        Map<String, Object> map = goodService.item(spuId);
        model.addAllAttributes(map);
        //写入静态文件
        goodService.creatHtml(spuId);
        //creatHtml(spu,spuDetail,skuList,groups,categoryList,paramMap,brand);
        return "item";
    }

    /**private void creatHtml(Spu spu, SpuDetail spuDetail, List<Sku> skuList, List<SpecGroup> groups, List<Category> categoryList, Map<Long, String> paramMap, Brand brand) {
        PrintWriter writer =null;
        try {
            //创建上下文
            Context context = new Context();
            //把数据放入上下文中
            context.setVariable("spu",spu);
            context.setVariable("spuDetail",spuDetail);
            context.setVariable("skuList",skuList);
            context.setVariable("groups",groups);
            context.setVariable("categoryList",categoryList);
            context.setVariable("paramMap",paramMap);
            context.setVariable("brand",brand);
            //写入文件
            File file = new File("D:\\ruanjan\\nginx\\nginx-1.16.1\\nginx-1.16.1\\html\\"+spu.getId()+".html");
            writer = new PrintWriter(file);
            //执行静态化
            templateEngine.process("item",context,writer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                //关闭写入流
                writer.close();
            }
        }
    }*/

}
