package cn.itcast.erp.util.interceptor;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;
import cn.itcast.erp.util.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthInterceptor extends AbstractInterceptor{
	private ResEbi resEbi;
	public void setResEbi(ResEbi resEbi) {
		this.resEbi = resEbi;
	}
	
	//优化方案二：对个人资源加载进行优化
	//问题：目前个人信息加载量过大，每次执行业务操作都要加载个人信息对应的可操作资源
	//解决方案：减少个人信息加载量的次数
	//减少加载次数为一次
	//时间：登陆时加载
	//放置位置：session范围
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		String resAll = ServletActionContext.getServletContext().getAttribute("resAllStr").toString();
		if(!resAll.toString().contains(allName)){
			return invocation.invoke();
		}
		
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_INFO);
		if(loginEm.getAllRes().contains(allName)){
			return invocation.invoke();
		}
		throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
	}
	
	
	//优化方案一：优化全资源加载
	//原始设计中，每次操作获取系统全资源，消耗很长的时间
	//分析：全资源对于整个系统来说，没有太大的变化性，所有人可以使用同一组数据（二级缓存）
	//数据一定要加载，什么时间加载？加载什么数据？
	//数据：全资源数据
	//加载分析：该数据仅仅需要加载一次，放置在一个指定的位置，后期使用从固定位置获取
	//时间：服务器启动时，(服务器启动时运行代码的机制：监听器)
	//位置：最大范围共享applicationContext(ServletContext)
	public String intercept2(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		//从SevletContext范围中获取所有的资源，直接使用
		String resAll = ServletActionContext.getServletContext().getAttribute("resAllStr").toString();
		if(!resAll.toString().contains(allName)){
			return invocation.invoke();
		}
		
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_INFO);
		List<ResModel> resList = resEbi.getAllResByEmp(loginEm.getUuid());
		StringBuilder sbf = new StringBuilder();
		for(ResModel temp : resList){
			sbf.append(temp.getUrl());
			sbf.append(",");
		}
		if(sbf.toString().contains(allName)){
			return invocation.invoke();
		}
		throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
	}
	
	
	public String intercept1(ActionInvocation invocation) throws Exception {
		//需求：
		//1.获取本次操作
		//获取本次操作内容：页面发起的是一个action调用emp_list与Action类中的方法
		//操作的内容是某个类中的某个方法
		String actionName = invocation.getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		//先判断当前操作是否需要拦截，如果不需要直接放行
		//1.1获取所有的资源
		List<ResModel> resAll  = resEbi.getAll();
		StringBuilder sbfAll = new StringBuilder();
		for(ResModel temp :resAll){
			sbfAll.append(temp.getUrl());
			sbfAll.append(",");
		}
		//1.2判断本次操作是否需要拦截
		if(!sbfAll.toString().contains(allName)){
			return invocation.invoke();
		}
		
		//2.获取当前登陆人可以允许的所有操作
		//由员工->角色->资源   可操作的资源的集合
		//方式一：登陆人信息session中可以获取到
		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get(EmpModel.LOGIN_INFO);
		
		//loginEm.getRoles()		//得到角色的集合
		//roleList->resList		//得到资源的集合
		//本方案需要登陆时，重新加载所有延迟数据，不推荐使用
		//System.out.println(loginEm.getRoles());
		//方式二：根据登陆人信息再次查询
		List<ResModel> resList = resEbi.getAllResByEmp(loginEm.getUuid());
		
		//3.判断是否可以进行操作
		//判断allName是否出现在  集合resList中所包含的对象中的url属性中
		
		//3.1.可以进行，放行
		/*
		for(资源对象){
			if(url中的值与allName相同){
				return invocation.invoke();
			}
		}
		*/
		//目标：allName这个字符串，是否出现在所有的url对应的字符串中
		//url字符串全部链接在一起，形成一个大的字符串，判断allName在不在大字符串中出现过
		//3.1.1将所有的url连接在一起
		StringBuilder sbf = new StringBuilder();
		for(ResModel temp : resList){
			sbf.append(temp.getUrl());
			sbf.append(",");
		}
		//if(大字符串序列中出现过allName){
		if(sbf.toString().contains(allName)){
			return invocation.invoke();
		}
		
		//3.2.不能进行，拦截
		//对原始操作的调用
		throw new AppException("对不起，您没有访问权限，请不要进行非法操作！");
	}

}
