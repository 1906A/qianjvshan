package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandServie brandServie;

    //品牌管理分页查询
    @RequestMapping("page")
    public Object findBrandBypage(@RequestParam("key") String key,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows,
                                  @RequestParam("sortBy") String sortBy,
                                  @RequestParam("desc") boolean desc){
        //PageResult<Brand> brandList = brandServie.findBrand(key,page,rows,sortBy,desc);
        PageResult<Brand> brandList = brandServie.findBrandSql(key,page,rows,sortBy,desc);
        return brandList;
    }

    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand,
                               @RequestParam("cids") List<Long> cids){

        //判断主键id是否有值
        if(brand.getId()==null){
            brandServie.brandCategorySave(brand,cids);
        }else {
            //修改
            brandServie.updateBrand(brand,cids);
        }

        System.out.println(brand.getId());
    }

    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id){
        brandServie.deleteById(id);
    }

    @RequestMapping("bid/{id}")
    public List<Category> findCategoryByBrandId(@PathVariable("id") Long pid){

        return brandServie.findCategoryByBrandId(pid);
    }

    /**
     * 根据分类id查询对应的品牌
     * @param cid
     * @return
     */
    @RequestMapping("cid/{cid}")
    public List<Brand> findBrandByCid(@PathVariable("cid")Long cid){

        return brandServie.findBrandBycid(cid);
    }

}
