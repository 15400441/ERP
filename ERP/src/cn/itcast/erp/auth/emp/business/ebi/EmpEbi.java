package cn.itcast.erp.auth.emp.business.ebi;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.util.base.BaseEbi;

@Transactional
public interface EmpEbi extends BaseEbi<EmpModel> {
	/**
	 * 根据用户名密码登陆
	 * @param userName 用户名
	 * @param pwd 密码
	 * @param loginIp 登陆IP地址
	 * @return 登陆用户信息，如果为null,登陆失败
	 */
	public EmpModel login(String userName, String pwd, String loginIp);
	/**
	 * 修改密码
	 * @param userName 用户名
	 * @param pwd 旧密码
	 * @param newPwd 新密码
	 * @return 修改是否成功
	 */
	public boolean changePwd(String userName, String pwd, String newPwd);
	/**
	 * 添加员工
	 * @param em 员工信息数据模型
	 * @param roleUuids 员工与角色关系uuid数组
	 */
	public void save(EmpModel em, Long[] roleUuids);
	public void update(EmpModel em, Long[] roleUuids);
	/**
	 * 获取指定部门所有员工信息
	 * @param uuid
	 * @return
	 */
	public List<EmpModel> getAllByDep(Long uuid);
}