package cn.itcast.erp.auth.menu.dao.dao;

import java.util.List;

import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.util.base.BaseDao;

public interface MenuDao extends BaseDao<MenuModel>{

	public List<MenuModel> getUuidOrPuuidIsSystemId();

	public List<MenuModel> getAllByEmpUuid(Long uuid);

	public List<MenuModel> getAllByEmpUuidAndPuuid(Long uuid, Long puuid);

}
