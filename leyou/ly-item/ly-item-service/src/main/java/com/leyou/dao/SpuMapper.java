package com.leyou.dao;

import com.leyou.Bo.SpuBo;
import com.leyou.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SpuMapper extends Mapper<Spu> {

    List<SpuBo> findSpuByPage(@Param("key") String key,
                              @Param("saleable") Integer saleable);
}
