package cn.itcast.erp.util.interceptor;

import cn.itcast.erp.auth.emp.vo.EmpModel;
import cn.itcast.erp.util.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

//技术：适配器模式
public class LoginInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		//需求：
		
		//如果操作的是登陆功能，直接放行
		//获取当前操作
		String actionName = invocation.getProxy().getAction().getClass().getName();
		String methodName = invocation.getProxy().getMethod();
		String allName = actionName+"."+methodName;
		
		//放行跳转登陆页page_login
		//获取本次操作
		if("page_login".equals(invocation.getProxy().getActionName())){
			return invocation.invoke();
		}
		
		//判断
		//登陆功能	cn.itcast.erp.auth.emp.web.EmpAction.login
		if("cn.itcast.erp.auth.emp.web.EmpAction.login".equals(allName)){
			return invocation.invoke();
		}

		EmpModel loginEm = (EmpModel) ActionContext.getContext().getSession().get("loginEm");
		//session中的信息为null即为未登陆
		//如果当前没有登陆，返回登陆页面
		if(loginEm == null){
			//返回登陆页面
			return "noLogin";
		}
		//如果登陆，放行
		return invocation.invoke();
	}


}
