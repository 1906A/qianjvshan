package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.Bo.SpuBo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {
    @Autowired
    SpuMapper spuMapper;
    @Autowired
    SpuDetailMapper spuDetailMapper;
    @Autowired
    SkuMapper skuMapper;
    @Autowired
    StockMapper stockMapper;
    @Autowired
    AmqpTemplate amqpTemplate;

    public PageResult<SpuBo> findSpuByPage(String key, Integer page, Integer rows, Integer saleable) {
        PageHelper.startPage(page,rows);
        PageInfo<SpuBo> list = new PageInfo<SpuBo>(spuMapper.findSpuByPage(key, saleable));
        return new PageResult<SpuBo>(list.getTotal(),list.getList());
    }

    /**
     * 保存商品信息
     * @param spuBo
     */
    public void saveSpuDetail(SpuBo spuBo) {

        Date nowDate = new Date();
        /**
         * 1.保存spu
         * 2.保存spu_detail
         * 3.保存cku
         * 4.保存stock
         */
        Spu spu = new Spu();
        spu.setTitle(spuBo.getTitle());
        spu.setSubTitle(spuBo.getSubTitle());
        spu.setBrandId(spuBo.getBrandId());
        spu.setCid1(spuBo.getCid1());
        spu.setCid2(spuBo.getCid2());
        spu.setCid3(spuBo.getCid3());
        spu.setSaleable(false);
        spu.setValid(true);
        spu.setCreateTime(nowDate);
        spu.setLastUpdateTime(nowDate);
        spuMapper.insert(spu);

        //保存spu_detail表
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);

        //保存sku
        List<Sku> skus = spuBo.getSkus();
        skus.forEach(sku->{
            sku.setSpuId(spu.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);

            //库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
        //发送mq消息
        this.sendMsg("insert",spu.getId());
    }
    public void sendMsg(String type,Long spuId){
        //发送mq消息
        amqpTemplate.convertAndSend("item.exchanges","item."+type,spuId);
    }

    /**
     * 根据spuid查询商品集列表
     * @param spuId
     * @return
     */
    public SpuDetail findSpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey(spuId);
    }

    /**
     * 修改商品信息
     * @param spuBo
     */
    public void updateSpuDetail(SpuBo spuBo) {
        /**
         * 1.保存spu
         * 2.保存spu_detail
         * 3.保存sku
         * 4.保存stock
         */
        Date nowDate = new Date();
        spuBo.setCreateTime(null);
        spuBo.setLastUpdateTime(nowDate);
        spuBo.setSaleable(null);
        spuBo.setValid(null);
        spuMapper.updateByPrimaryKeySelective(spuBo);
        //修改spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        //修改sku
//        List<Sku> skus = spuBo.getSkus();
//        skus.forEach(s ->{
//            //删除sku表
//            s.setEnable(false);
//            skuMapper.updateByPrimaryKey(s);
//            //库存
//            stockMapper.deleteByPrimaryKey(s.getId());
//        } );
        List<Sku> skuList = skuMapper.findSkuBySpuId(spuBo.getId());
        skuList.forEach(s ->{
            skuMapper.deleteByPrimaryKey(s.getId());
            stockMapper.deleteByPrimaryKey(s.getId());
        });
        //保存sku
        List<Sku> skus1 = spuBo.getSkus();
        skus1.forEach(sku->{
            sku.setSpuId(spuBo.getId());
            sku.setEnable(true);
            sku.setCreateTime(nowDate);
            sku.setLastUpdateTime(nowDate);
            skuMapper.insert(sku);

            //库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
        //发送mq消息
        this.sendMsg("update",spuBo.getId());
    }

    public void deleteSpuBySpuId(Long spuId) {
        //删除sku
        List<Sku> skuList = skuMapper.findSkuBySpuId(spuId);
        skuList.forEach(s ->{
            //删除sku表
            s.setEnable(false);
            skuMapper.updateByPrimaryKeySelective(s);
            //库存
            stockMapper.deleteByPrimaryKey(s.getId());
        });
        //删除detail
        spuDetailMapper.deleteByPrimaryKey(spuId);
        //删除spu
        spuMapper.deleteByPrimaryKey(spuId);
        //删除商品发送mq消息
        //this.sendMsg("delete",spuId);
    }

    /**
     * 上下架
     * @param spuId
     */
    public void upOrDown(Long spuId,int saleable) {
        Spu spu = new Spu();
        spu.setId(spuId);
        spu.setSaleable(saleable ==1 ? true :false);
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 根据spuid查询spu
     * @param spuId
     * @return
     */
    public Spu findSpuBuId(Long spuId) {
        return spuMapper.selectByPrimaryKey(spuId);
    }

    public SpuBo findSpuBySpuId(Long spuId) {
        return spuMapper.findSpuBySpuId(spuId);
    }
}
