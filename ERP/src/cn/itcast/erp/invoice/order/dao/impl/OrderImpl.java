package cn.itcast.erp.invoice.order.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.order.dao.dao.OrderDao;
import cn.itcast.erp.invoice.order.vo.OrderModel;
import cn.itcast.erp.invoice.order.vo.OrderQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class OrderImpl extends BaseImpl<OrderModel> implements OrderDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		OrderQueryModel oqm = (OrderQueryModel)qm;
		//oqm.type
		if(oqm.getType()!=null && oqm.getType()!= -1){
			dc.add(Restrictions.eq("type", oqm.getType()));
		}
		//oqm.creater.name
		if(oqm.getCreater()!=null && oqm.getCreater().getName()!=null && oqm.getCreater().getName().trim().length() > 0 ){
			dc.createAlias("creater", "c1");
			dc.add(Restrictions.like("c1.name", "%"+oqm.getCreater().getName().trim()+"%"));
		}
		//oqm.completer.uuid
		if(oqm.getCompleter()!=null && oqm.getCompleter().getUuid()!=null && oqm.getCompleter().getUuid()!= -1){
			dc.add(Restrictions.eq("completer", oqm.getCompleter()));
		}
		//oqm.orderTypes
		if(oqm.getOrderTypes()!=null){
			dc.add(Restrictions.in("orderType", oqm.getOrderTypes()));
		}
		//oqm.types
		if(oqm.getTypes()!=null){
			dc.add(Restrictions.in("type", oqm.getTypes()));
		}
		// TODO 添加自定义查询条件
	}

}