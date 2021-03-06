package com.leyou.client;

import com.leyou.Bo.SpuBo;
import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("spu")
public interface SpuClientServer {
    @RequestMapping("page")
    public PageResult<SpuBo> findSpuByPage(@RequestParam("key") String key,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows,
                                           @RequestParam(required = false,value="saleable") Integer saleable);

    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId);

    @RequestMapping("findSpuBuId")
    public Spu findSpuBuId(@RequestParam("spuId") Long spuId);

    @RequestMapping("findSpuBySpuId")
    public SpuBo findSpuBySpuId(@RequestParam("spuId") Long spuId);

}
