package com.leyou.com.leyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoodsDetailController {
    @RequestMapping("hello")
    public String hello(Model model){
        String name = "张三";
        model.addAttribute("name",name);
        return "hello";
    }
    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId") Long spuId,Model model){
        model.addAttribute("name",spuId);
        return "hello";
    }
}
