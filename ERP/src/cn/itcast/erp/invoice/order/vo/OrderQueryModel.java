package cn.itcast.erp.invoice.order.vo;

import cn.itcast.erp.util.base.BaseQueryModel;

public class OrderQueryModel extends OrderModel implements BaseQueryModel{
	private Integer[] orderTypes;
	private Integer[] types;
	
	public Integer[] getTypes() {
		return types;
	}
	public void setTypes(Integer[] types) {
		this.types = types;
	}
	public Integer[] getOrderTypes() {
		return orderTypes;
	}
	public void setOrderTypes(Integer[] orderTypes) {
		this.orderTypes = orderTypes;
	}
	
	// TODO 添加自定义范围查询条件 
}
