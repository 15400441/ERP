package cn.itcast.erp.auth.res.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.erp.auth.res.dao.dao.ResDao;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.res.vo.ResQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class ResImpl extends BaseImpl<ResModel> implements ResDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		ResQueryModel rqm = (ResQueryModel)qm;
		// TODO 添加自定义查询条件
	}

	public List<ResModel> getAllByEmpUuid(Long uuid) {
		//方案一：直接查询连接，问题：没有关联关系，无法实现
		/*
		String hql ="from ResModel where 员工.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
		*/
		//方案二：
		/*
		员工->角色		from EmpModel em join em.roles
		角色->资源		from RoleModel rm join rm.reses
		员工->角色->资源	from EmpModel emp join emp.roles role join role.reses 
		*/
		//String hql = " from EmpModel emp join emp.roles role join role.reses where emp.uuid = ?";
		//return this.getHibernateTemplate().find(hql,uuid);
		//select 省名  from 人 join 住户 join 街道 join 区 join 市 join 省  where 人的身份证id = ?
		//from 人 join 住户 join 街道 join 区 join 市 join 省  where 人的身份证id = ?
		//select 人,住户,街道,区,市,省  from 人 join 住户 join 街道 join 区 join 市 join 省  where 人的身份证id = ?
		
		//select 省名  from 省 join 市 join ....join 人 where  人的身份证id = ?
		//select 省,市,区,街道 ,人 住户  from 人 join 住户 join 街道 join 区 join 市 join 省  where 人的身份证id = ?
		String hql = "select distinct res from EmpModel emp join emp.roles role join role.reses res where emp.uuid = ?";
		return this.getHibernateTemplate().find(hql,uuid);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml","applicationContext-res.xml");
		ResDao dao = (ResDao) ctx.getBean("resDao");
		System.out.println(dao.getAllByEmpUuid(1L));
	}
}




