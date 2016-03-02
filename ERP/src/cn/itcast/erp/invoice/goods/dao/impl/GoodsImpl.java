package cn.itcast.erp.invoice.goods.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.erp.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.erp.invoice.goods.vo.GoodsModel;
import cn.itcast.erp.invoice.goods.vo.GoodsQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsImpl extends BaseImpl<GoodsModel> implements GoodsDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		GoodsQueryModel gqm = (GoodsQueryModel)qm;
		//gqm.unit
		if(gqm.getUnit()!=null && gqm.getUnit().trim().length()>0){
			dc.add(Restrictions.eq("unit", gqm.getUnit()));
		}
		//gqm.gtm.sm.uuid
		if( gqm.getGtm()!=null && 
			gqm.getGtm().getSm()!=null &&
			gqm.getGtm().getSm().getUuid()!=null && 
			gqm.getGtm().getSm().getUuid()!= -1){
			//could not resolve property: gtm.sm.uuid of: cn.itcast.erp.invoice.goods.vo.GoodsModel
			//Hibernate定义gtm.sm.uuid是一个完整的属性名,在hbm.xml文件中有这个名称
			/*
			//正确
			dc.createAlias("gtm", "gt");
			dc.createAlias("gt.sm", "s");
			dc.add(Restrictions.eq("s.uuid", gqm.getGtm().getSm().getUuid()));
			*/
			dc.createAlias("gtm", "gt");
			dc.add(Restrictions.eq("gt.sm", gqm.getGtm().getSm()));
		}
		/*
		am.bm.cm.dm.uuid
		am a
		a.bm b
		b.cm c
		c.dm  d
		d.uuid
		
		am a
		a.bm ab
		b.cm abc
		c.dm  abcd
		abcd.uuid
		*/
		// TODO 添加自定义查询条件
	}

	public List<GoodsModel> getAllByGtmUuid(Long uuid) {
		String hql = " from GoodsModel where gtm.uuid = ?" ;
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsModel> getAllByGtmAndUuidNotInUuids(Long uuid, Long[] uuids) {
		//模板不支持not in数组或集合(伪方案)
		//String hql = " from GoodsModel where gtm.uuid = ? and uuid not in ?" ;
		//return this.getHibernateTemplate().find(hql,uuid,uuids);
		//格式可以使用，但是不符合预编译SQL格式(可选方案)
		//String hql = " from GoodsModel where gtm.uuid = ? and uuid not in (2)" ;
		//return this.getHibernateTemplate().find(hql,uuid);
		//还原原始API，使用Query对象查询（推荐方案）
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = s.createQuery("from GoodsModel where gtm.uuid = :uuid and uuid not in :uuids");
		q.setLong("uuid", uuid);
		q.setParameterList("uuids", uuids);
		return q.list();
	}
	/*
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-goods.xml");
		GoodsDao dao = (GoodsDao) ctx.getBean("goodsDao");
		System.out.println(dao.getAllByGtmAndUuidNotInUuids(1L, new Long[]{1L,2L}));
	}
	 */
	public void useNumUpdate() {
		/*
		update
			tbl_goods g
		set
			useNum = 
				(
					select
						count(uuid)
					from
						tbl_orderdetail
					where
						goodsUuid = g.uuid
				)
		*/
		String hql = "update GoodsModel g set g.useNum = (select count(od.uuid) from OrderDetailModel od where od.gm.uuid = g.uuid )";
		this.getHibernateTemplate().bulkUpdate(hql);
	}

	public List<Object[]> getWarnList() {
		/*
		select
			g.name,
			sum(sd.num) > g.maxNum,
			sum(sd.num) < g.minNum
		from
			tbl_storedetail sd,
			tbl_goods g
		where
			sd.goodsUuid = g.uuid
		group by
			goodsUuid
		*/
		//HQL语句中无法解析二次运算操作，必须寻求一种可以执行的方案
		//String hql = "select gm.name,sum(sd.num) > gm.maxNum,sum(sd.num) < gm.minNum from StoreDetailModel sd group by gm.uuid";
		//return this.getHibernateTemplate().find(hql);
		//使用SQL完成该操作
		String sql = "select g.name, sum(sd.num) > g.maxNum, sum(sd.num) < g.minNum from tbl_storedetail sd, tbl_goods g where sd.goodsUuid = g.uuid group by goodsUuid";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery q =  s.createSQLQuery(sql);
		return q.list();
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-goods.xml");
		GoodsDao dao = (GoodsDao) ctx.getBean("goodsDao");
		List<Object[]> temp = dao.getWarnList();
		for(Object[] obj:temp){
			System.out.println(obj[0]);
			System.out.println(obj[1]);
			System.out.println(obj[2]);
		}
		
	}
}





