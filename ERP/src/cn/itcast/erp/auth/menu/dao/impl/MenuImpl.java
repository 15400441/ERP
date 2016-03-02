package cn.itcast.erp.auth.menu.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.auth.menu.dao.dao.MenuDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.menu.vo.MenuQueryModel;
import cn.itcast.erp.util.base.BaseImpl;
import cn.itcast.erp.util.base.BaseQueryModel;

public class MenuImpl extends BaseImpl<MenuModel> implements MenuDao{

	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){
		MenuQueryModel mqm = (MenuQueryModel)qm;
		//过滤掉系统菜单:某个东西不等于某个值
		//过掉系统菜单：uuid != 1 ;  puuid != 0
		dc.add(Restrictions.not(Restrictions.eq("uuid", MenuModel.MENU_SYSTEM_UUID)));
		
		//name
		if(mqm.getName()!=null && mqm.getName().trim().length()>0){
			dc.add(Restrictions.like("name", "%"+mqm.getName().trim()+"%"));
		}
		//parent.uuid
		if(mqm.getParent()!=null && mqm.getParent().getUuid()!=null && mqm.getParent().getUuid()!= -1){
			//dc.add(Restrictions.eq("parent.uuid", mqm.getParent().getUuid()));
			dc.add(Restrictions.eq("parent", mqm.getParent()));
		}
	}

	public List<MenuModel> getUuidOrPuuidIsSystemId() {
		String hql = " from MenuModel where uuid = ? or puuid = ?";
		return this.getHibernateTemplate().find(hql,MenuModel.MENU_SYSTEM_UUID,MenuModel.MENU_SYSTEM_UUID);
	}

	public List<MenuModel> getAllByEmpUuid(Long uuid) {
		//员工->角色->菜单
		//菜单->角色->员工
	  //String hql = "select distinct menu from MenuModel menu join menu.roles role join role.emps emp where emp.uuid = ? and menu.parent.uuid = ? order by menu.uuid";
		String hql = "select distinct menu from EmpModel e join e.roles role join role.menus menu where e.uuid = ? and menu.parent.uuid = ? order by menu.uuid";
		return this.getHibernateTemplate().find(hql,uuid,MenuModel.MENU_SYSTEM_UUID);
	}

	public List<MenuModel> getAllByEmpUuidAndPuuid(Long uuid, Long puuid) {
		String hql =" select distinct menu from EmpModel e join e.roles role join role.menus menu where e.uuid = ? and menu.parent.uuid = ? order by menu.uuid";
		return this.getHibernateTemplate().find(hql,uuid,puuid);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}