package com.leyou.controller;

import com.leyou.Bo.SpuBo;
import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {
    @Autowired
    SpuService spuService;

    /**
     * 商品列表查询分页
     * @param key
     * @param page
     * @param rows
     * @param saleable
     * @return
     */
    @RequestMapping("page")
    public PageResult<SpuBo> findSpuByPage(@RequestParam("key") String key,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows,
                                           @RequestParam(required = false,value="saleable") Integer saleable){
        return spuService.findSpuByPage(key, page, rows, saleable);
    }

    /**
     * 保存商品信息
     * @param spuBo
     */
    @RequestMapping("saveOrUpdateGoods")
    public  void saveSpuDetail(@RequestBody SpuBo spuBo){
        if(spuBo.getId()!=null){
            spuService.updateSpuDetail(spuBo);
        }else {
            spuService.saveSpuDetail(spuBo);
        }

    }

    /**
     * 查询商品详情
     */
    @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId") Long spuId){
        return spuService.findSpuDetailBySpuId(spuId);
    }

    /**
     * 根据spuId删除spu详情
     * @param spuId
     */
    @RequestMapping("deleteById/{spuId}")
    public void deleteSpuBySpuId(@PathVariable("spuId") Long spuId){
        spuService.deleteSpuBySpuId(spuId);
    }

    /**
     * 上下架
     * @param spuId
     */
    @RequestMapping("upOrDown")
    public void upOrDown(@RequestParam("spuId") Long spuId,
                         @RequestParam("saleable") int saleable){
        spuService.upOrDown(spuId,saleable);
    }

    /**
     * 根据spuid查询spu
     * @param spuId
     * @return
     */
    @RequestMapping("findSpuBuId")
    public Spu findSpuBuId(@RequestParam("spuId") Long spuId){
        return spuService.findSpuBuId(spuId);
    }

    @RequestMapping("findSpuBySpuId")
    public SpuBo findSpuBySpuId(@RequestParam("spuId") Long spuId){
        return spuService.findSpuBySpuId(spuId);
    }

}
