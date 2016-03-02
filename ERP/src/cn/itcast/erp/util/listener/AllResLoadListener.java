package cn.itcast.erp.util.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.erp.auth.res.business.ebi.ResEbi;
import cn.itcast.erp.auth.res.vo.ResModel;

public class AllResLoadListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent event) {
		//在监听器中获取ServletContext对象
		ServletContext sc = event.getServletContext();
		//从spring环境中获取
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc );
		ResEbi resEbi = (ResEbi) ctx.getBean("resEbi");
		//加载全资源数据
		List<ResModel> resAll  = resEbi.getAll();
		//将集合中的数据组织好放入到指定范围
		StringBuilder sbfAll = new StringBuilder();
		for(ResModel temp :resAll){
			sbfAll.append(temp.getUrl());
			sbfAll.append(",");
		}
		//放入ServletContext范围
		sc.setAttribute("resAllStr", sbfAll.toString());
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
}
