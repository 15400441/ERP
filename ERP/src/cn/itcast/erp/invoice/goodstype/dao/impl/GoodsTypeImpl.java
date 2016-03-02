package cn.itcast.erp.invoice.goodstype.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.invoice.goodstype.dao.dao.GoodsTypeDao;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeModel;
import cn.itcast.erp.invoice.goodstype.vo.GoodsTypeQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class GoodsTypeImpl extends BaseImpl<GoodsTypeModel> implements GoodsTypeDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		GoodsTypeQueryModel gqm = (GoodsTypeQueryModel)qm;
		// TODO 添加自定义查询条件
	}

	public List<GoodsTypeModel> getAllBySmUuid(Long uuid) {
		String hql = " from GoodsTypeModel where sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsTypeModel> getAllJoinGmBySmUuid(Long uuid) {
		String hql = "select distinct gt from GoodsModel g join g.gtm gt where gt.sm.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public List<GoodsTypeModel> getAllUnionBySm(Long uuid, Long[] uuids) {
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "select distinct gt from GoodsModel g join g.gtm gt where gt.sm.uuid = :uuid and g.uuid not in :uuids";
		Query q =s.createQuery(hql);
		q.setLong("uuid", uuid);
		q.setParameterList("uuids", uuids);
		return q.list();
	}

}









