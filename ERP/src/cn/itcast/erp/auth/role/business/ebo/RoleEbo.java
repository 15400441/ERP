package cn.itcast.erp.auth.role.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.dao.dao.RoleDao;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class RoleEbo implements RoleEbi{
	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	//废弃
	public void save(RoleModel rm) {
	}

	public List<RoleModel> getAll() {
		return roleDao.getAll();
	}

	public RoleModel get(Long uuid) {
		return roleDao.get(uuid);
	}

	//废弃
	public void update(RoleModel rm) {
	}

	public void delete(RoleModel rm) {
		roleDao.delete(rm);
	}

	public List<RoleModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return roleDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return roleDao.getCount(qm);
	}

	public void save(RoleModel rm, Long[] resUuids, Long[] menuUuids) {
		//首先建立角色与菜单间的关系：多对多
		//menuUuids(array)->set->rm
		Set<MenuModel> menus = new HashSet<MenuModel>();
		for(Long uuid:menuUuids){
			MenuModel temp  = new MenuModel();
			temp.setUuid(uuid);
			menus.add(temp);
		}
		rm.setMenus(menus);
		
		//array->set->rm
		Set<ResModel> reses = new HashSet<ResModel>();
		for(Long uuid:resUuids){
			ResModel temp = new ResModel();
			temp.setUuid(uuid);
			reses.add(temp);
		}
		rm.setReses(reses);
		roleDao.save(rm);
	}

	public void update(RoleModel rm, Long[] resUuids, Long[] menuUuids) {
		Set<ResModel> reses = new HashSet<ResModel>();
		for(Long uuid:resUuids){
			ResModel temp = new ResModel();
			temp.setUuid(uuid);
			reses.add(temp);
		}
		rm.setReses(reses);
		
		Set<MenuModel> menus = new HashSet<MenuModel>();
		for(Long uuid:menuUuids){
			MenuModel temp  = new MenuModel();
			temp.setUuid(uuid);
			menus.add(temp);
		}
		rm.setMenus(menus);
		
		roleDao.update(rm);
	}

}