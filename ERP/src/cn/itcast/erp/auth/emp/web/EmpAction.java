package cn.itcast.erp.auth.emp.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.dep.business.ebi.DepEbi;
import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.emp.business.ebi.EmpEbi;
import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.emp.vo.EmpQueryModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.auth.role.business.ebi.RoleEbi;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.base.BaseAction;
import cn.itcast.erp.util.exception.AppException;

public class EmpAction extends BaseAction{
	public EmpModel em = new EmpModel();
	public EmpQueryModel eqm = new EmpQueryModel();

	private EmpEbi empEbi;
	private DepEbi depEbi;
	private RoleEbi roleEbi;
	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}
	public void setRoleEbi(RoleEbi roleEbi) {
		this.roleEbi = roleEbi;
	}
	public void setDepEbi(DepEbi depEbi) {
		this.depEbi = depEbi;
	}
	public void setEmpEbi(EmpEbi empEbi) {
		this.empEbi = empEbi;
	}

	//分页列表
	public String list(){
		//查询所有部门信息
		List<DepModel> depList = depEbi.getAll();
		put("depList",depList);
		setDataTotal(empEbi.getCount(eqm));
		List<EmpModel> empList = empEbi.getAll(eqm,pageNum,pageCount);
		put("empList", empList);
		return LIST;
	}

	//跳转到添加/修改页
	public String input(){
		//查询所有角色信息
		List<RoleModel> roleList = roleEbi.getAll();
		put("roleList",roleList);
		//查询所有部门信息
		List<DepModel> depList = depEbi.getAll();
		//放入指定范围
		put("depList",depList);
		if(em.getUuid() != null){
			em = empEbi.get(em.getUuid());
			//需要将关系数据进行回显
			//必须将关系数据转换成可以回显的格式
			//页面需要的数据格式：roleUuids数据
			//现有的关系数据em.roles
			//数据在Set中，需要显示的数据Long[]
			//set->array
			roleUuids = new Long[em.getRoles().size()];
			int i = 0;
			for(RoleModel temp : em.getRoles()){
				//temp.uuid->array
				roleUuids[i++] = temp.getUuid();
			}
		}
		return INPUT;
	}

	//如果要收集多个name相同的值，则使用两种形式可以接受数据
	//集合，数组
	//public List<String> aa;
	//public Set<String> aa;
	//public String[] aa;
	//选取依据：1.使用方便，2.节约内存
	//作用：描述的是员工对角色的关系数据对应的关联外键，该数据要进数据库中的表字段
	//该数据将转化为多对多关系，最终在模型中描述成Set<模型类Model>
	public Long[] roleUuids;
	//获取关系数据字段uuid数组的作用？绑定员工与角色之间的关系
	//新增/修改
	public String save(){
		if(em.getUuid() == null){
			empEbi.save(em,roleUuids);
		}else{
			empEbi.update(em,roleUuids);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		empEbi.delete(em);
		return TO_LIST;
	}

	//登陆
	public String login(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String loginIp = request.getHeader("x-forwarded-for"); 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("Proxy-Client-IP"); 
		} 
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getHeader("WL-Proxy-Client-IP"); 
		}
		if(loginIp == null || loginIp.length() == 0 || "unknown".equalsIgnoreCase(loginIp)) { 
			loginIp = request.getRemoteAddr(); 
		}
		EmpModel loginEm = empEbi.login(em.getUserName(),em.getPwd(),loginIp);
		if(loginEm == null){
			//如果登陆失败，提示用户，重新输入并登陆
			//添加错误信息
			this.addActionError("对不起，用户名密码错误！");
			return "loginFail";
		}else{
			//如果登陆成功，跳转到主页
			
			//加载当前登陆人可以操作的所有资源信息
			List<ResModel> resList = resEbi.getAllResByEmp(loginEm.getUuid());
			//将集合中的可操作资源信息对应的字符串进行拼接，放入session
			StringBuilder sbf = new StringBuilder();
			for(ResModel temp : resList){
				sbf.append(temp.getUrl());
				sbf.append(",");
			}
			loginEm.setAllRes(sbf.toString());
			
			//登陆数据放入session
			putSession(EmpModel.LOGIN_INFO, loginEm);
			return "loginSuccess";
		}
	}
	
	//注销系统
	public String logout(){
		//销毁session中的登陆数据
		putSession(EmpModel.LOGIN_INFO,null);
		//返回登陆页面
		return "noLogin";
	}
	
	//跳转到修改密码页
	public String toChangePwd(){
		return "toChangePwd";
	}
	
	//定义新密码
	public String newPwd;
	
	public String changePwd(){
		//旧密码 em.pwd
		//新密码 newPwd
		boolean flag = empEbi.changePwd(getLogin().getUserName(),em.getPwd(),newPwd);
		if(flag){
			//清理登陆信息，重新登陆
			putSession(EmpModel.LOGIN_INFO,null);
			return "noLogin";
		}else{
			throw new AppException("对不起，用户名密码错误");
		}
	}
	
}



