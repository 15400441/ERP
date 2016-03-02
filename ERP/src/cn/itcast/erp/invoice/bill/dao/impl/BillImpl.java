package cn.itcast.erp.invoice.bill.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.itcast.erp.invoice.bill.dao.dao.BillDao;
import cn.itcast.erp.invoice.bill.vo.BillQueryModel;
import cn.itcast.erp.invoice.orderdetail.vo.OrderDetailModel;
import cn.itcast.erp.invoice.supplier.vo.SupplierModel;

public class BillImpl extends HibernateDaoSupport implements BillDao{

	public List<Object[]> getBuyBill(BillQueryModel bqm) {
		/*
		select
			g.uuid,
			g.name,
			sum(od.num)
		from
			tbl_orderdetail od,
			tbl_goods g
		where
			g.uuid = goodsUuid
		group by
			g.uuid
		*/
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDetailModel.class);
		//求和，分组
		//select count(*) ......
		//dc.setProjection(Projections.rowCount());
		//dc.setProjection(Projections.sum("num"));
		//dc.setProjection(Projections.groupProperty("gm"));
		ProjectionList plist = Projections.projectionList();
		plist.add(Projections.groupProperty("gm"));		//具有分组效果，并且在投影中添加了查询内容
		plist.add(Projections.sum("num"));
		dc.setProjection(plist);
		//设定条件
		//bqm.type
		dc.createAlias("om", "o");
		if(bqm.getType()!=null && bqm.getType()!= -1){
			//订单的类别
			dc.add(Restrictions.eq("o.type", bqm.getType()));
		}
		//bqm.supplierUuid
		if(bqm.getSupplierUuid()!=null && bqm.getSupplierUuid()!= -1){
			//odm->gm->gtm->sm->uuid
			//odm->om->sm->uuid
			SupplierModel sm = new SupplierModel();
			sm.setUuid(bqm.getSupplierUuid());
			dc.add(Restrictions.eq("o.sm", sm));
		}
		return this.getHibernateTemplate().findByCriteria(dc);
	}
	
	public List<OrderDetailModel> getBillDetail(BillQueryModel bqm) {
		DetachedCriteria dc = DetachedCriteria.forClass(OrderDetailModel.class);
		dc.createAlias("om", "o");
		if(bqm.getType()!=null && bqm.getType()!= -1){
			dc.add(Restrictions.eq("o.type", bqm.getType()));
		}
//		if(bqm.getSupplierUuid()!=null && bqm.getSupplierUuid()!= -1){
//			SupplierModel sm = new SupplierModel();
//			sm.setUuid(bqm.getSupplierUuid());
//			dc.add(Restrictions.eq("o.sm", sm));
//		}
		//bqm.goodsUuid
		if(bqm.getGoodsUuid()!=null && bqm.getGoodsUuid()!= -1){
			dc.add(Restrictions.eq("gm.uuid", bqm.getGoodsUuid()));
		}
		return this.getHibernateTemplate().findByCriteria(dc);
	}
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-bill.xml");
		BillDao dao = (BillDao) ctx.getBean("billDao");
		List<Object[]> temp = dao.getBuyBill(null);
		for(Object[] objs:temp){
			System.out.println(objs[0].getClass());
			System.out.println(objs[1]);
		}
	}
}