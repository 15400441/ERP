package cn.itcast.erp.util.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.auth.emp.vo.EmpModel;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport{
	//结果集规范字符串抽取
	protected static final String LIST ="list";
	protected static final String TO_LIST ="toList";
	protected static final String INPUT ="input";
	
	//分页变量抽取
	public Integer pageNum = 1;
	public Integer pageCount = 10;
	public Integer maxPageNum;
	public Integer dataTotal;
	
	
	public String getActionName(){
		//动态获取对应的名称   dep
		//getClass()	==>   DepAction   ==>  dep
		String className = getClass().getSimpleName();		//DepAction
		String sub = className.substring(0, className.length()-6);		//Dep
		//sub.toLowerCase()		DepAction->dep   GoodsTypeAction->goodstype
		String first = sub.substring(0, 1);		//D
		first = first.toLowerCase();			//d
		return first + sub.substring(1);
	}

	protected void setDataTotal(Integer dataTotal){
		this.dataTotal = dataTotal;
		maxPageNum = (dataTotal + pageCount -1) / pageCount;
	}
	
	
	protected void put(String name,Object obj){
		ActionContext.getContext().put(name,obj);
	}
	
	protected Object get(String name){
		return ActionContext.getContext().get(name);
	}
	
	protected void putSession(String name,Object obj){
		ActionContext.getContext().getSession().put(name,obj);
	}
	
	protected Object getSession(String name){
		return ActionContext.getContext().getSession().get(name);
	}
	
	//获取当前登陆人信息
	protected EmpModel getLogin(){
		return (EmpModel) getSession(EmpModel.LOGIN_INFO);
	}
	
	//获取reqeust与response对象
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	
}
