package cn.itcast.erp.util.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cn.itcast.erp.invoice.storeoper.vo.StoreOperModel;


public class GeneratorUtil {
	private Class clazz;
	private String b;
	private String l;
	private String s;
	private String pkg;
	private String rootDir;	
	
	public static void main(String[] args) throws IOException {
		//EmpModel,RoleModel,ResModel,MenuModel
		//SupplierModel,GoodsTypeModel,GoodsModel
		//OrderModel,OrderDetailModel
		//StoreModel,StoreDetailModel,StoreOperModel
		new GeneratorUtil(StoreOperModel.class);
		System.out.println("struts.xml未配置");
		System.out.println("hbm.xml未配置关系");
		System.out.println("QueryModel未添加范围字段");
		System.out.println("DaoImpl未添加查询条件的实现");
	}
	
	
	
	public GeneratorUtil(Class clazz) throws IOException{
		this.clazz = clazz;
		//-1.数据初始化
		dataInit();
		//0.生成所有要使用的目录
		generatorDirectory();
		//1.QueryModel
		generatorQueryModel();
		//2.hbm.xml
		generatorHbmXml();
		//3.Dao
		generatorDao();
		//4.Impl
		generatorImpl();
		//5.Ebi
		generatorEbi();
		//6.Ebo
		generatorEbo();
		//7.Action
		generatorAction();
		//8.applicationContext.xml
		generatorApplicationContextXml();
		//9.struts.xml
		//modifyStrutsXml();
	}
	
	//9.struts.xml
	private void modifyStrutsXml() throws IOException {
		//需求：读取原始struts.xml文件中的内容，在特定位置添加内容
		//添加的内容：
//		<!-- Dep -->
//    	<action name="dep_*" class="depAction" method="{1}">
//    	</action>
		//添加的位置：
//	    </package>的上方
		
		//操作:读文件，并且写文件，而且读写对应的文件是同一个
		//InputStream OutputStream
		//先读取文件A的内容，写入文件B，删除文件A，文件B更名为文件A
		//方案一：
		//1.读取文件A的一行，判断是否是特殊行，如果不是直接写
		//2.如果是先添加追加的内容，然后写
		//方案二：
		//1.先将整个A文件全部读取出来，放置在一个特殊的位置
		//2.在整个特殊位置中查找你要添加的位置，然后将内容追加进去
		//3.将整体内容写入文件
		
		//方案一实现：
		File f = new File("resources/struts.xml");
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		File f2 = new File("resources/struts2.xml");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f2));
		
		String msg = null;
		//读文件
		do{
			msg = br.readLine();
			if(msg != null){
				//如果读取到特殊行，加入特殊内容
				if(msg.contains("</package>")){
					bw.write("    	<!-- "+b+" -->");
					bw.newLine();
					bw.write("    	<action name=\""+s+"_*\" class=\""+s+"Action\" method=\"{1}\">");
					bw.newLine();
					bw.write("    	</action>");
					bw.newLine();
					bw.newLine();
				}
				//写入第二个文件
				bw.write(msg);
				bw.newLine();
			}
		}while(msg != null);
		
		br.close();
		bw.flush();
		bw.close();
		
		f.delete();
		f2.renameTo(f);
	}
	
	//8.applicationContext.xml
	private void generatorApplicationContextXml() throws IOException {
		//1.创建文件
		File f = new File("resources/applicationContext-"+s+".xml");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\r\n"+
"<beans xmlns=\"http://www.springframework.org/schema/beans\""+"\r\n"+
"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+"\r\n"+
"	xsi:schemaLocation=\""+"\r\n"+
"		http://www.springframework.org/schema/beans"+"\r\n"+
"		http://www.springframework.org/schema/beans/spring-beans.xsd"+"\r\n"+
"		\">"+"\r\n"+
"	<!-- Action -->"+"\r\n"+
"	<bean id=\""+s+"Action\" class=\""+pkg+".web."+b+"Action\" scope=\"prototype\">"+"\r\n"+
"		<property name=\""+s+"Ebi\" ref=\""+s+"Ebi\"/>"+"\r\n"+
"	</bean>"+"\r\n"+
"	<!-- Ebi  -->"+"\r\n"+
"	<bean id=\""+s+"Ebi\" class=\""+pkg+".business.ebo."+b+"Ebo\">"+"\r\n"+
"		<property name=\""+s+"Dao\" ref=\""+s+"Dao\"/>"+"\r\n"+
"	</bean>"+"\r\n"+
"	<!-- Dao -->"+"\r\n"+
"	<bean id=\""+s+"Dao\" class=\""+pkg+".dao.impl."+b+"Impl\">"+"\r\n"+
"		<property name=\"sessionFactory\" ref=\"sessionFactory\"/>"+"\r\n"+
"	</bean>"+"\r\n"+
"</beans>");
	
		//4.关闭流
		bw.flush();
		bw.close();			
	}

	//7.Action
	private void generatorAction() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/web/"+b+"Action.java");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("package "+pkg+".web;"+"\r\n"+
""+"\r\n"+
"import java.util.List;"+"\r\n"+
""+"\r\n"+
"import "+pkg+".business.ebi."+b+"Ebi;"+"\r\n"+
"import "+pkg+".vo."+b+"Model;"+"\r\n"+
"import "+pkg+".vo."+b+"QueryModel;"+"\r\n"+
"import cn.itcast.erp.util.base.BaseAction;"+"\r\n"+
""+"\r\n"+
"public class "+b+"Action extends BaseAction{"+"\r\n"+
"	public "+b+"Model "+l+"m = new "+b+"Model();"+"\r\n"+
"	public "+b+"QueryModel "+l+"qm = new "+b+"QueryModel();"+"\r\n"+
""+"\r\n"+
"	private "+b+"Ebi "+s+"Ebi;"+"\r\n"+
"	public void set"+b+"Ebi("+b+"Ebi "+s+"Ebi) {"+"\r\n"+
"		this."+s+"Ebi = "+s+"Ebi;"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	//分页列表"+"\r\n"+
"	public String list(){"+"\r\n"+
"		setDataTotal("+s+"Ebi.getCount("+l+"qm));"+"\r\n"+
"		List<"+b+"Model> "+s+"List = "+s+"Ebi.getAll("+l+"qm,pageNum,pageCount);"+"\r\n"+
"		put(\""+s+"List\", "+s+"List);"+"\r\n"+
"		return LIST;"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	//跳转到添加/修改页"+"\r\n"+
"	public String input(){"+"\r\n"+
"		if("+l+"m.getUuid() != null){"+"\r\n"+
"			"+l+"m = "+s+"Ebi.get("+l+"m.getUuid());"+"\r\n"+
"		}"+"\r\n"+
"		return INPUT;"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	//新增/修改"+"\r\n"+
"	public String save(){"+"\r\n"+
"		if("+l+"m.getUuid() == null){"+"\r\n"+
"			"+s+"Ebi.save("+l+"m);"+"\r\n"+
"		}else{"+"\r\n"+
"			"+s+"Ebi.update("+l+"m);"+"\r\n"+
"		}"+"\r\n"+
"		return TO_LIST;"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	//删除"+"\r\n"+
"	public String delete(){"+"\r\n"+
"		"+s+"Ebi.delete("+l+"m);"+"\r\n"+
"		return TO_LIST;"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"}");
		
		//4.关闭流
		bw.flush();
		bw.close();			
	}

	//6.Ebo
	private void generatorEbo() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/business/ebo/"+b+"Ebo.java");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("package "+pkg+".business.ebo;"+"\r\n"+
""+"\r\n"+
"import java.util.List;"+"\r\n"+
""+"\r\n"+
"import "+pkg+".business.ebi."+b+"Ebi;"+"\r\n"+
"import "+pkg+".dao.dao."+b+"Dao;"+"\r\n"+
"import "+pkg+".vo."+b+"Model;"+"\r\n"+
"import cn.itcast.erp.util.base.BaseQueryModel;"+"\r\n"+
""+"\r\n"+
"public class "+b+"Ebo implements "+b+"Ebi{"+"\r\n"+
"	private "+b+"Dao "+s+"Dao;"+"\r\n"+
"	public void set"+b+"Dao("+b+"Dao "+s+"Dao) {"+"\r\n"+
"		this."+s+"Dao = "+s+"Dao;"+"\r\n"+
"	}"+"\r\n"+
"	"+"\r\n"+
"	public void save("+b+"Model "+l+"m) {"+"\r\n"+
"		"+s+"Dao.save("+l+"m);"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	public List<"+b+"Model> getAll() {"+"\r\n"+
"		return "+s+"Dao.getAll();"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	public "+b+"Model get(Long uuid) {"+"\r\n"+
"		return "+s+"Dao.get(uuid);"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	public void update("+b+"Model "+l+"m) {"+"\r\n"+
"		"+s+"Dao.update("+l+"m);"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	public void delete("+b+"Model "+l+"m) {"+"\r\n"+
"		"+s+"Dao.delete("+l+"m);"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	public List<"+b+"Model> getAll(BaseQueryModel qm, Integer pageNum,Integer pageCount) {"+"\r\n"+
"		return "+s+"Dao.getAll(qm,pageNum,pageCount);"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"	public Integer getCount(BaseQueryModel qm) {"+"\r\n"+
"		return "+s+"Dao.getCount(qm);"+"\r\n"+
"	}"+"\r\n"+
""+"\r\n"+
"}");
		
		//4.关闭流
		bw.flush();
		bw.close();	
	}

	//5.Ebi
	private void generatorEbi() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/business/ebi/"+b+"Ebi.java");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("package "+pkg+".business.ebi;"+"\r\n"+
"\r\n"+	
"import org.springframework.transaction.annotation.Transactional;"+"\r\n"+
"\r\n"+	
"import "+pkg+".vo."+b+"Model;"+"\r\n"+
"import cn.itcast.erp.util.base.BaseEbi;"+"\r\n"+
"\r\n"+	
"@Transactional"+"\r\n"+
"public interface "+b+"Ebi extends BaseEbi<"+b+"Model> {"+"\r\n"+
"\r\n"+		
"}");
		
		//4.关闭流
		bw.flush();
		bw.close();
	}

	//4.Impl
	private void generatorImpl() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/dao/impl/"+b+"Impl.java");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("package "+pkg+".dao.impl;"+"\r\n"+
"\r\n"+	
"import org.hibernate.criterion.DetachedCriteria;"+"\r\n"+
"import org.hibernate.criterion.Restrictions;"+"\r\n"+
"\r\n"+	
"import "+pkg+".dao.dao."+b+"Dao;"+"\r\n"+
"import "+pkg+".vo."+b+"Model;"+"\r\n"+
"import "+pkg+".vo."+b+"QueryModel;"+"\r\n"+
"import cn.itcast.erp.util.base.BaseImpl;"+"\r\n"+
"import cn.itcast.erp.util.base.BaseQueryModel;"+"\r\n"+
"\r\n"+	
"public class "+b+"Impl extends BaseImpl<"+b+"Model> implements "+b+"Dao{"+"\r\n"+
"\r\n"+	
"	public void doQbc(DetachedCriteria dc,BaseQueryModel qm){"+"\r\n"+
"		"+b+"QueryModel "+l+"qm = ("+b+"QueryModel)qm;"+"\r\n"+
"		// TODO 添加自定义查询条件"+"\r\n"+
"	}"+"\r\n"+
"\r\n"+		
"}");
		
		//4.关闭流
		bw.flush();
		bw.close();				
	}

	//3.Dao
	private void generatorDao() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/dao/dao/"+b+"Dao.java");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("package "+pkg+".dao.dao;"+"\r\n"+
"\r\n"+			
"import "+pkg+".vo."+b+"Model;"+"\r\n"+
"import cn.itcast.erp.util.base.BaseDao;"+"\r\n"+
"\r\n"+
"public interface "+b+"Dao extends BaseDao<"+b+"Model>{"+"\r\n"+
"\r\n"+
"}");
		bw.newLine();
		
		
		//4.关闭流
		bw.flush();
		bw.close();		
	}

	//2.hbm.xml
	private void generatorHbmXml() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/vo/"+b+"Model.hbm.xml");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		bw.newLine();
		
		bw.write("<!DOCTYPE hibernate-mapping PUBLIC");
		bw.newLine();
		
		bw.write("        '-//Hibernate/Hibernate Mapping DTD 3.0//EN'");
		bw.newLine();
		
		bw.write("        'http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd'>");
		bw.newLine();
		
		bw.write("<hibernate-mapping>");
		bw.newLine();
		
		bw.write("    <class name=\""+pkg+".vo."+b+"Model\" table=\"tbl_"+s+"\">");
		bw.newLine();
		
		bw.write("        <id name=\"uuid\">");
		bw.newLine();
		
		bw.write("            <generator class=\"native\" />");
		bw.newLine();
		
		bw.write("        </id>");
		bw.newLine();
		
		//获取原始Model类中的属性，生成在这个位置
		Field[] fds = clazz.getDeclaredFields();
		for(Field fd : fds){
			//如果修饰符是private的，生成
			if(fd.getModifiers() == Modifier.PRIVATE){
				//uuid不生成
				if(!fd.getName().equals("uuid")){
					//关联关系不生成->String,Long,Double,Integer生成
					if( fd.getType().equals(String.class) ||
						fd.getType().equals(Long.class)   ||
						fd.getType().equals(Double.class) ||
						fd.getType().equals(Integer.class) 
						){
						//特殊现象
						if(!fd.getName().contains("View")){
							bw.write("        <property name=\""+fd.getName()+"\"/>");
							bw.newLine();
						}
					}
				}
			}
		}
		
		bw.write("    </class>");
		bw.newLine();
		
		bw.write("</hibernate-mapping>");
		bw.newLine();
		
		//4.关闭流
		bw.flush();
		bw.close();		
	}


	//1.QueryModel
	private void generatorQueryModel() throws IOException {
		//1.创建文件
		File f = new File("src/"+rootDir+"/vo/"+b+"QueryModel.java");
		
		if(f.exists()){
			return;
		}
		
		f.createNewFile();
		//2.绑定IO流
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//3.写内容
		bw.write("package "+pkg+".vo;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("import cn.itcast.erp.util.base.BaseQueryModel;");
		bw.newLine();
		
		bw.newLine();
		
		bw.write("public class "+b+"QueryModel extends "+b+"Model implements BaseQueryModel{");
		bw.newLine();
		
		bw.write("	// TODO 添加自定义范围查询条件 ");
		bw.newLine();
		
		bw.write("}");
		bw.newLine();
		
		//4.关闭流
		bw.flush();
		bw.close();
	}
	
	//0.生成所有要使用的目录
	private void generatorDirectory() {
		File f = new File("src/"+rootDir+"/business/ebi");
		f.mkdirs();
		
		f = new File("src/"+rootDir+"/business/ebo");
		f.mkdirs();
		
		f = new File("src/"+rootDir+"/dao/dao");
		f.mkdirs();
		
		f = new File("src/"+rootDir+"/dao/impl");
		f.mkdirs();
		
		f = new File("src/"+rootDir+"/web");
		f.mkdirs();
	}
	
	//-1.数据初始化
	private void dataInit() {
		//基于Class初始化其他方法需要使用的数据
		String className = clazz.getSimpleName();					
		b = className.substring(0, className.length()-5);			//Dep
		l = b.substring(0,1).toLowerCase();							//d
		s = l+ b.substring(1);										//dep
		String allPkg = clazz.getPackage().getName();
		pkg = allPkg.substring(0,allPkg.length()-3);				//cn.itcast.erp.invoice.auth.dep
		rootDir = pkg.replace(".", "/");							//cn/itcast/erp/invoice/auth/dep
	}
	
	/*
	public static void main(String[] args) throws IOException {
		File f = new File("src/User.java");
		f.createNewFile();
		
		FileWriter fw = new FileWriter(f);
		fw.write("class User{}");
		fw.flush();
		fw.close();
	}
	*/
}
