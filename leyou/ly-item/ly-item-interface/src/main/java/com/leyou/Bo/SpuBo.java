package com.leyou.Bo;

import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;

import java.util.List;

public class SpuBo extends Spu {
    //cid1,cid2,cid3组合
    private String cname;
    //品牌名称
    private String bname;
    private SpuDetail spuDetail;
    private List<Sku> skus;

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

//    public SpuBo(String cname, String bname) {
//        this.cname = cname;
//        this.bname = bname;
//    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
