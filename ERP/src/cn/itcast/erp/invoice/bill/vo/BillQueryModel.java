package cn.itcast.erp.invoice.bill.vo;

import cn.itcast.erp.util.base.BaseQueryModel;

public class BillQueryModel implements BaseQueryModel{
	private Integer type;
	private Long supplierUuid;
	private Long goodsUuid;
	
	public Long getGoodsUuid() {
		return goodsUuid;
	}
	public void setGoodsUuid(Long goodsUuid) {
		this.goodsUuid = goodsUuid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getSupplierUuid() {
		return supplierUuid;
	}
	public void setSupplierUuid(Long supplierUuid) {
		this.supplierUuid = supplierUuid;
	}
	
	// TODO 添加自定义范围查询条件 
}
