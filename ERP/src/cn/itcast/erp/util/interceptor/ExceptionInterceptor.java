package cn.itcast.erp.util.interceptor;

import cn.itcast.erp.util.exception.AppException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (AppException e) {
			//记录信息
			//跳转到一个错误页
			//ActionContext.getContext().put("errorMsg", e.getMessage());
			ActionSupport as = (ActionSupport) invocation.getAction();
			//如果在拦截器中进行国际化消息获取
			as.addActionError(as.getText(e.getMessage()));
			//将e信息发送到程序员那里或记录日志
			return "error";
		}catch (Exception e) {
			//记录日志。。。。。
			e.printStackTrace();
			return invocation.invoke();
		}
	}

}
