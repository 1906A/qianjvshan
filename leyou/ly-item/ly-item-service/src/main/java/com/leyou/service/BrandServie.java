package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServie {
    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> findBrand(String key,Integer page, Integer rows, String sortBy, boolean desc) {
        PageHelper.startPage(page,rows);
        List<Brand> brandList = brandMapper.findBrand(key,sortBy,desc);
        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandList);

        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());

    }

    public PageResult<Brand> findBrandSql(String key,Integer page, Integer rows, String sortBy, boolean desc) {

        //查询出来的结果
        List<Brand> brandList = brandMapper.findBrandSql(key,(page-1)*rows,rows,sortBy,desc);
        //查询总条数
        Long brandCount = brandMapper.findCount(key,sortBy,desc);

        return new PageResult<Brand>(brandCount,brandList);
    }

    public void brandCategorySave(Brand brand,List<Long> cids){
        //保存brand
        brandMapper.insert(brand);
        //保存tb_category_brand
        cids.forEach(cid ->{
            System.out.println(cid+ "-----"+brand.getId());
            brandMapper.addBrandAndCategory(cid,brand.getId());
        });

    }

    public void deleteById(Long id) {
        //删除brand
        Brand brand = new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);
        //删除关系表
        brandMapper.deleteBrandAndCategory(id);

    }

    public List<Category> findCategoryByBrandId(Long pid) {

        return brandMapper.findCategoryByBrandId(pid);
    }

    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌表
        brandMapper.updateByPrimaryKey(brand);
        //修改关系表（先删掉再添加）
        brandMapper.deleteBrandAndCategory(brand.getId());

        cids.forEach(cid ->{
            brandMapper.addBrandAndCategory(brand.getId(),cid);
            System.out.println(brand.getId()+"-------------------"+cid);
        });
    }

    public List<Brand> findBrandBycid(Long cid) {
        return brandMapper.findBrandBycid(cid);
    }
}
