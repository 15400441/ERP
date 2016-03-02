package cn.itcast.erp.auth.menu.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import cn.itcast.erp.auth.menu.business.ebi.MenuEbi;
import cn.itcast.erp.auth.menu.vo.MenuModel;
import cn.itcast.erp.auth.menu.vo.MenuQueryModel;
import cn.itcast.erp.util.base.BaseAction;

public class MenuAction extends BaseAction{
	public MenuModel mm = new MenuModel();
	public MenuQueryModel mqm = new MenuQueryModel();

	private MenuEbi menuEbi;
	public void setMenuEbi(MenuEbi menuEbi) {
		this.menuEbi = menuEbi;
	}

	//分页列表
	public String list(){
		//加载系统菜单与所有菜单目录（不包含菜单项）
		List<MenuModel> parentList = menuEbi.getAllOneLevel();
		put("parentList",parentList);
		setDataTotal(menuEbi.getCount(mqm));
		List<MenuModel> menuList = menuEbi.getAll(mqm,pageNum,pageCount);
		put("menuList", menuList);
		return LIST;
	}

	//跳转到添加/修改页
	public String input(){
		//加载系统菜单与所有菜单目录（不包含菜单项）
		List<MenuModel> menuList = menuEbi.getAllOneLevel();
		put("menuList",menuList);
		if(mm.getUuid() != null){
			mm = menuEbi.get(mm.getUuid());
		}
		return INPUT;
	}

	//新增/修改
	public String save(){
		if(mm.getUuid() == null){
			menuEbi.save(mm);
		}else{
			menuEbi.update(mm);
		}
		return TO_LIST;
	}

	//删除
	public String delete(){
		menuEbi.delete(mm);
		return TO_LIST;
	}
	//显示菜单
	public String showMenu() throws IOException{
		String root = getRequest().getParameter("root");
		//需要对字符集进行处理
		getResponse().setContentType("text/html;charset=utf8");
		PrintWriter pw = getResponse().getWriter();
		StringBuilder sbf = new StringBuilder();
		//基于集合中的数据，组织链接的json数组数据
		sbf.append("[");
		if("source".equals(root)){
			List<MenuModel> menuList = menuEbi.getAllByEmp(getLogin().getUuid());
			for(MenuModel temp : menuList){
				sbf.append("{\"text\":\"");
				sbf.append(temp.getName());
				sbf.append("\",\"hasChildren\":true,\"classes\":\"folder\",\"id\":\"");
				sbf.append(temp.getUuid());
				sbf.append("\"},");
			}
		}else{
			Long puuid = new Long(root);
			//加载的是什么数据？
			//获取当前登陆人可操作的对应菜单下的所有菜单项
			List<MenuModel> menuList = menuEbi.getAllByEmpAndParent(getLogin().getUuid(),puuid);
			//菜单项数据
			for(MenuModel temp : menuList){
				//修改成加载菜单项格式............
				sbf.append("{\"text\":\"<a class='hei' target='main' href='");
				sbf.append(temp.getUrl());
				sbf.append("'>");
				sbf.append(temp.getName());
				sbf.append("</a>\",\"classes\":\"file\"},");
			}
		}
		//移除多余的，
		sbf.deleteCharAt(sbf.length()-1);
		sbf.append("]");
		pw.write(sbf.toString());
		pw.flush();
		return null;
	}
	/*
	//显示菜单
	public String showMenu() throws IOException{
		//需要对字符集进行处理
		getResponse().setContentType("text/html;charset=utf8");
		PrintWriter pw = getResponse().getWriter();
		//要展示的数据必须是动态的
		//不同的人登陆看的菜单一样不一样？不相同
		List<MenuModel> menuList = menuEbi.getAllByEmp(getLogin().getUuid());
		StringBuilder sbf = new StringBuilder();
		//基于集合中的数据，组织链接的json数组数据
		sbf.append("[");
		for(MenuModel temp : menuList){
			sbf.append("{\"text\":\"");
			sbf.append(temp.getName());
			sbf.append("\",\"hasChildren\":true,\"classes\":\"folder\"},");
		}
		//移除多余的，
		sbf.deleteCharAt(sbf.length()-1);
		sbf.append("]");
		pw.write(sbf.toString());
		pw.flush();
		return null;
	}
	*/
	/*
	//显示菜单
	public String showMenu() throws IOException{
		PrintWriter pw = getResponse().getWriter();
		pw.write("[");
		//菜单目录
		pw.write("{\"text\":\"aaa11\",\"hasChildren\":true,\"classes\":\"folder\"},");
		//菜单项
		pw.write("{\"text\":\"bbb22\",\"classes\":\"file\"},");
		pw.write("{\"text\":\"ccc33\",\"hasChildren\":true,\"expanded\":false,\"classes\":\"eeff\"}");
		pw.write("]");
		pw.flush();
		return null;
	}
	*/
	/*
	//显示菜单
	public String showMenu() throws IOException{
		//测试返回json数组数据
		//调用该方法，返回的数据是json数组格式
		//return "aa";		//"aa"->result->??.jsp
		//通过result得到页面后，将页面的数据按照IO流形式读取，写入到响应对象中，发送出去
		
		//1.获取响应对象
		PrintWriter pw = getResponse().getWriter();
		pw.write("{\"aa\":\"bb\",\"cc\":334}");
		pw.flush();
		return null;
	}
	*/
	/*
	//显示菜单
	public String showMenu(){
		//按照要求干一些事情
		//要求:从请求中获取参数值，然后比对，满足条件执行操作A，否则执行操作B
		String root = getRequest().getParameter("root");
		if("source".equals(root)){
			//.....返回json数组
		}else{
			//.....返回json数组
		}
		return null;
	}
	*/
}