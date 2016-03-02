package cn.itcast.erp.auth.emp.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.itcast.erp.auth.dep.vo.DepModel;
import cn.itcast.erp.auth.role.vo.RoleModel;
import cn.itcast.erp.util.format.FormatUtil;

public class EmpModel {
	//类别视图值辅助定义:核心思想：缓存————数据结构
	//类别视图值替代方案：数据字典
	//1.定义所有的类别真实值常量
	public static final Integer EMP_GENDER_OF_MAN = 1;
	public static final Integer EMP_GENDER_OF_WOMAN = 0;
	//2.定义所有的类别视图值常量
	public static final String EMP_GENDER_OF_MAN_VIEW = "男";
	public static final String EMP_GENDER_OF_WOMAN_VIEW = "女";
	//3.定义类别视图值Map集合
	public static final Map<Integer, String> genderMap = new HashMap<Integer,String>();
	//4.初始化类别Map集合
	static{
		genderMap.put(EMP_GENDER_OF_MAN, EMP_GENDER_OF_MAN_VIEW);
		genderMap.put(EMP_GENDER_OF_WOMAN, EMP_GENDER_OF_WOMAN_VIEW);
	}
	
	public static final String LOGIN_INFO = "loginEm";
	
	private Long uuid;
	
	private String userName;
	private String pwd;
	private String name;
	private String email;
	private String tele;
	private String address;
	private String lastLoginIp;
	private Integer loginTimes;
	
	private Integer gender;
	private Long birthday;
	private Long lastLoginTime;
	
	//视图值：用于显示的值
	//该值与一个真实值相对应，但是由于真实值不利于显示，因此设计对应的视图值
	//规范：
	//视图值全是String类型
	//定义名称：原始真实值变量名+View
	//视图值仅提供get方法
	//视图值没有初始化值，并且不与数据库中的字段直接对应，但是伴随着真实值得改变而改变
	private String birthdayView;
	private String lastLoginTimeView;
	private String genderView;
	
	//辅助字段
	private String allRes;
	
	private DepModel dm;
	//对角色多对多
	private Set<RoleModel> roles;
	
	public Set<RoleModel> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}

	public String getGenderView() {
		return genderView;
	}
	
	public String getAllRes() {
		return allRes;
	}

	public void setAllRes(String allRes) {
		this.allRes = allRes;
	}

	public String getLastLoginTimeView() {
		return lastLoginTimeView;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
		this.lastLoginTimeView = FormatUtil.formatDate(lastLoginTime);
	}

	public String getBirthdayView() {
		return birthdayView;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
		this.genderView = genderMap.get(gender);
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
		this.birthdayView = FormatUtil.formatDate(birthday);
	}
	public DepModel getDm() {
		return dm;
	}
	public void setDm(DepModel dm) {
		this.dm = dm;
	}
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
