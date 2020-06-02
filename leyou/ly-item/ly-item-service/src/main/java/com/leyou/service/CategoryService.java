package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 根据节点查询所有分类信息
     * @param category
     * @return
     */
    public List<Category> findCategory(Category category){
        return categoryMapper.select(category);
    }

    /**
     * 手写sql测试
     * @param id
     * @return
     */
    public Category findCate(int id){
        return categoryMapper.findCate(id);
    }


    /**
     * 添加
     * @param category
     */
    public void add(Category category) {
        categoryMapper.insertSelective(category);
    }

    public void update(Category category){
        categoryMapper.updateByPrimaryKey(category);
    }

    public void deleteById(long id) {
        Category category = new Category();
        category.setId(id);
        categoryMapper.deleteByPrimaryKey(category);
    }

    /**
     * 根据分类id查询分类名称
     * @param id
     * @return
     */
    public Category findCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
