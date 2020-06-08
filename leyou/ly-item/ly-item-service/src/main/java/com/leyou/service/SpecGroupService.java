package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {
    @Autowired
    SpecGroupMapper specGroupMapper;
    @Autowired
    SpecParamMapper specParamMapper;

    public void saveSpecGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    /**
     * 根据分类id查询规格组列表
     * @param cateGoryId
     * @return
     */
    public List<SpecGroup> findSpecGroupList(Long cateGoryId) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cateGoryId);
        //根据分类id查询规格参数组及组内参数列表
        List<SpecGroup> groupList = new ArrayList<>();
        groupList = specGroupMapper.select(specGroup);
        groupList.forEach(group ->{
            SpecParam param = new SpecParam();
            param.setGroupId(group.getId());
            group.setParams(specParamMapper.select(param));
        });
        return specGroupMapper.select(specGroup);
    }

    public void deleteBySpecGroupId(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     * 根据规格组id查询组参数列表
     * @param gid
     * @return
     */
    public List<SpecParam> findSpecParamByGid(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return specParamMapper.select(specParam);
    }
}
