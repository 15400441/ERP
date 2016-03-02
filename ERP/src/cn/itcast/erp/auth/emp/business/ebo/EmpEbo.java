package cn.itcast.erp.auth.emp.business.ebo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.dao.dao.EmpDao;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.base.BaseQueryModel;
import cn.itcast.erp.util.format.MD5Utils;

public class EmpEbo implements EmpEbi{
	private EmpDao empDao;
	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
	}
	
	//废弃
	public void save(EmpModel em) {
	}

	public List<EmpModel> getAll() {
		return empDao.getAll();
	}

	public EmpModel get(Long uuid) {
		return empDao.get(uuid);
	}
	//废弃
	public void update(EmpModel em) {
		/*
		name
		email
		tele
		address
		dm.uuid
		
		empDao.update(em);
		*/
		//修改时，页面传递过来很多数据，但是有些需要修改，有些不能参与修改
		//此时调用update操作，将所有字段都修改
		/*
		//先去查询数据，然后将得到的数据中不能修改的数据覆盖当前数据，此时可以修改的数据未被覆盖，完成修改
		//update操作完成，物理更新，硬更新
		EmpModel temp = empDao.get(em.getUuid());	//具有原始数据
		em.setPwd(temp.getPwd());
		//清除temp对象
		empDao.update(em);
		*/
		
		//先去查询数据，得到数据后，将可以修改的数据设置到查询的结果中，更新
		//快照完成，逻辑更新，软更新
		//1.查询
		EmpModel temp = empDao.get(em.getUuid());
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setTele(em.getTele());
		temp.setAddress(em.getAddress());
		temp.setDm(em.getDm());
		//temp.getDm().setUuid(em.getDm().getUuid());
	}

	public void delete(EmpModel em) {
		empDao.delete(em);
	}

	public List<EmpModel> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {
		return empDao.getAll(qm,pageNum,pageCount);
	}

	public Integer getCount(BaseQueryModel qm) {
		return empDao.getCount(qm);
	}

	public EmpModel login(String userName, String pwd , String loginIp) {
		//对密码进行md5加密
		pwd = MD5Utils.md5(pwd);
		//查询得到的结果如果不为null，将进入快照与一级缓存
		EmpModel loginEm =  empDao.getByUserNameAndPwd(userName,pwd);
		//判断用户是否登陆成功
		if(loginEm != null){
			//记录登陆信息
			loginEm.setLastLoginTime(System.currentTimeMillis());
			//使用原始登陆此处+1
			loginEm.setLoginTimes(loginEm.getLoginTimes() + 1);
			//登陆IP
			loginEm.setLastLoginIp(loginIp);
		}
		return loginEm;
	}

	public boolean changePwd(String userName, String pwd, String newPwd) {
		//方案一：使用pwd与登陆信息中的密码比对，如果相同，使用快照更新密码
		//A与B同时使用同一个账号登陆，A操作完毕，密码已经修改，B还可以修改
		//方案二：使用用户名与旧密码查询数据，如果查询到了，使用快照更新新密码
		//A与B同时执行修改密码业务，A查询过来，还未进行快照更新的时间段内，B查询过来，A的修改无效
		//方案三：执行 update pwd = 新密码 where userName = 用户名  and pwd = 旧密码（ 正确）
		//A与B同时执行，A执行传递操作数据过去后，修改完成，B无法进行修改
		//密码先加密
		pwd = MD5Utils.md5(pwd);
		newPwd = MD5Utils.md5(newPwd);
		return empDao.updatePwdByUserNameAndPwd(userName,pwd,newPwd);
	}

	public void save(EmpModel em, Long[] roleUuids) {
		//设置登陆次数
		em.setLoginTimes(0);
		//设置最后登陆时间
		em.setLastLoginTime(System.currentTimeMillis());
		//最后登陆Ip
		em.setLastLoginIp("-");
		//收集的密码必须加密
		em.setPwd(MD5Utils.md5(em.getPwd()));
		
		//roleUuids->关系数据
		//array(data)->set
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long uuid:roleUuids){
			RoleModel temp = new RoleModel();
			temp.setUuid(uuid);
			roles.add(temp);
		}
		em.setRoles(roles);
		
		empDao.save(em);
	}

	public void update(EmpModel em, Long[] roleUuids) {
		EmpModel temp = empDao.get(em.getUuid());
		temp.setName(em.getName());
		temp.setEmail(em.getEmail());
		temp.setTele(em.getTele());
		temp.setAddress(em.getAddress());
		temp.setDm(em.getDm());
		
		//array->set
		Set<RoleModel> roles = new HashSet<RoleModel>();
		for(Long uuid:roleUuids){
			RoleModel temp2 = new RoleModel();
			temp2.setUuid(uuid);
			roles.add(temp2);
		}
		temp.setRoles(roles);
		
		//修改关系时执行的SQL语句是什么语句？
		//将原始的3个关系，去掉了1个，加上2个
		//update,delete
		/*
		1,2,3  - > 1 ,2 
		2,3    - > 2 ,3 ,4
		???? - > ?????
		先删除原始的所有关系，然后建立全新的关系
		*/
	}

	public List<EmpModel> getAllByDep(Long uuid) {
		return empDao.getAllByDepUuid(uuid);
	}
}

