package cn.itcast.erp.auth.menu.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface MenuEbi extends BaseEbi<MenuModel> {

	public List<MenuModel> getAllOneLevel();
	/**
	 * 获取指定员工对应可操作的所有菜单信息
	 * @param uuid 员工uuid
	 * @return
	 */
	public List<MenuModel> getAllByEmp(Long uuid);
	/**
	 * 获取指定员工指定菜单组中可操作的所有菜单项
	 * @param uuid 员工uuid
	 * @param puuid 指定菜单组uuid
	 * @return
	 */
	public List<MenuModel> getAllByEmpAndParent(Long uuid, Long puuid);

}