package cn.itcast.erp.auth.menu.business.ebo;

import java.util.List;

import cn.itcast.erp.auth.menu.business.ebi.MenuEbi;
import cn.itcast.erp.auth.menu.dao.dao.MenuDao;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.util.base.BaseQueryModel;

public class MenuEbo implements MenuEbi{
	private MenuDao menuDao;
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	public void save(MenuModel mm) {
		menuDao.save(mm);
	}

	public List<MenuModel> getAll() {
		return menuDao.getAll();
	}

	public MenuModel get(Long uuid) {
		return menuDao.get(uuid);
	}

	public void update(MenuModel mm) {
		//快照
		MenuModel temp = menuDao.get(mm.getUuid());
		temp.setName(mm.getName());
		temp.setUrl(mm.getUrl());
	}

	public void delete(MenuModel mm) {
		//此处设置了级联删除cascade=delete，理论上应该是先删除子，后删除父
		//但是删除的子是哪些？执行该操作时，是通过mm.getChildren()得到数据后，删除里面的数据
		//mm此时是否包含children,包含，但是里面的数据是空的
		//注意：执行级联删除操作，必须保障主数据通过快照或查询加载到所有的关联数据
		mm = menuDao.get(mm.getUuid());
		menuDao.delete(mm);
	}

	public List<MenuModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return menuDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return menuDao.getCount(qm);
	}

	public List<MenuModel> getAllOneLevel() {
		return menuDao.getUuidOrPuuidIsSystemId();
	}

	public List<MenuModel> getAllByEmp(Long uuid) {
		return menuDao.getAllByEmpUuid(uuid);
	}

	public List<MenuModel> getAllByEmpAndParent(Long uuid, Long puuid) {
		return menuDao.getAllByEmpUuidAndPuuid(uuid,puuid);
	}

}