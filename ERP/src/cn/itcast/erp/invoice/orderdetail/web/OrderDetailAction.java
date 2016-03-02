package cn.itcast.erp.invoice.orderdetail.web;

import java.util.List;

import cn.itcast.erp.invoice.orderdetail.business.ebi.OrderDetailEbi;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailQueryModel;
import cn.itcast.erp.util.base.BaseAction;

public class OrderDetailAction extends BaseAction{
	public OrderDetailModel om = new OrderDetailModel();
	public OrderDetailQueryModel oqm = new OrderDetailQueryModel();

	private OrderDetailEbi orderDetailEbi;
	public void setOrderDetailEbi(OrderDetailEbi orderDetailEbi) {
		this.orderDetailEbi = orderDetailEbi;
	}

	//分页列表
	public String list(){
		setDataTotal(orderDetailEbi.getCount(oqm));
		List<OrderDetailModel> orderDetailList = orderDetailEbi.getAll(oqm,pageNum,pageCount);
		put("orderDetailList", orderDetailList);
		return LIST;
	}

	//跳转到添加/修改页
	public String input(){
		if(om.getUuid() != null){
			om = orderDetailEbi.get(om.getUuid());
		}
		return INPUT;
	}

	//新增/修改
	public String save(){
		if(om.getUuid() == null){
			orderDetailEbi.save(om);
		}else{
			orderDetailEbi.update(om);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		orderDetailEbi.delete(om);
		return TO_LIST;
	}

	//----AJAX-----------------------
	public OrderDetailModel getOm() {
		return om;
	}
	public String ajaxGetSurplusByUuid(){
		//数据剩余数量在Model中
		om = orderDetailEbi.get(om.getUuid());
		return "ajaxGetSurplusByUuid";
	}

	
}