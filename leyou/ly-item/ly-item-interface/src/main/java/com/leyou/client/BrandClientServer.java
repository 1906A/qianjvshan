package com.leyou.client;

import com.leyou.pojo.Brand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


public interface BrandClientServer {

    @RequestMapping("/brand/findByBrandId")
    public Brand findByBrandId(@RequestParam("id") Long id);

}
