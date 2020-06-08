package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController//返回json串
@RequestMapping("category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public List<Category> findCategory(@RequestParam("pid") Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return categoryService.findCategory(category);
    }

    @RequestMapping("id")
    public Object findCate(){
        return categoryService.findCate(6);
    }

    //添加
    @RequestMapping("add")
    @CrossOrigin
    public String add(@RequestBody Category category){
        String s = "SUCC";
        try {
            categoryService.add(category);
        }catch (Exception error){
            s = "FALL";
        }
        return s;
    }

    //修改
    @RequestMapping("update")
    public String update(@RequestBody Category category){
        String s = "SUCC";
        try {
            categoryService.update(category);
        }catch (Exception error){
            s = "FALL";
        }
        return s;
    }

    //删除
    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id") long id){
        String s = "SUCC";
        try {
            categoryService.deleteById(id);
        }catch (Exception error){
            s = "FALL";
        }
        return s;
    }

    /**
     * 根据分类id查询分类名称
     * @param id
     * @return
     */
    @RequestMapping("findCategoryById")
    public Category findCategoryById(@RequestParam("id") Long id){
            return categoryService.findCategoryById(id);
    }

    /**
     * 根据分类id查询分类名称
     * @param ids
     * @return
     */
    @RequestMapping("findCategoryByCids")
    public List<Category> findCategoryByCids(@RequestBody List<Long> ids){
        return categoryService.findCategoryByCids(ids);
    }


}
