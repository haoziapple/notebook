
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@SuppressWarnings({"finally","rawtypes","unchecked"})
public class POIExcel<T> {

	Class<T> clazz;
	public Workbook wb;

	public Workbook getWb() {
		return wb;
	}

	public void setWb(Workbook wb) {
		this.wb = wb;
	}

	public POIExcel() {
		super();
	}

	public POIExcel(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
	 *
	 * @param title
	 *            表格标题名
	 * @param headersName
	 *            表格属性列名数组
	 * @param headersId
	 *            表格属性列名对应的字段
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */

	public byte[] exportExcelBYTE(String title, String[] headersName,
			String[] headersId, List<T> dtoList) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] content = null;
		try {
			this.exportExcel(title, headersName, headersId, dtoList).write(os);
			content = os.toByteArray();
			if(os!=null){
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return content;
		}

	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
	 *
	 * @param filePath
	 *            文件保存完整路径 d://xx.xls
	 * @param title
	 *            表格标题名
	 * @param headersName
	 *            表格属性列名数组
	 * @param headersId
	 *            表格属性列名对应的字段
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */
	public Boolean exportExcelFile(String filePath, String title,
			String[] headersName, String[] headersId, List<T> dtoList) {
		FileOutputStream xls = null;
		Boolean istrue = false;
		try {
			if(filePath.endsWith(".xls")){
				HSSFWorkbook wb = this.exportExcel(title, headersName, headersId,dtoList);
				xls = new FileOutputStream(filePath);
				wb.write(xls);
				istrue = true;
			}else{
				XSSFWorkbook wb = this.exportXLSXExcel(title, headersName, headersId,dtoList);
				xls = new FileOutputStream(filePath);
				wb.write(xls);
				istrue = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xls.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return istrue;
		}
	}

	/**
	 * <p>Description：导出数据到excel，每个实体都有附加列</p>
	 * @param filePath 文件绝对路径
	 * @param title 文件名
	 * @param headersName 表格属性列名数组
	 * @param headersId 表格属性列名对应的字段
	 * @param map 每个元素[实体=注释内容]
	 * @return
	 * @Author yujt
	 * @Date   2016-6-13  上午10:15:52
	 */
	public Boolean exportExcelFileWithExtraCol(String filePath, String title,
			String[] headersName, String[] headersId, Map<String,T> map) {
		FileOutputStream xls = null;
		Boolean istrue = false;
		try {
			if(filePath.endsWith(".xls")){
				HSSFWorkbook wb = this.exportExcelWithExtraCol(title, headersName, headersId,map);
				xls = new FileOutputStream(filePath);
				wb.write(xls);
				istrue = true;
			}else{
				XSSFWorkbook wb = this.exportXLSXExcelWithExtraCol(title, headersName, headersId,map);
				xls = new FileOutputStream(filePath);
				wb.write(xls);
				istrue = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				xls.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return istrue;
		}
	}


	/**
	 * <p>Description：导出数据到excel，每个实体都有附加列</p>
	 * @param title excel文件名
	 * @param headersName
	 * @param headersId
	 * @param map 每个元素[实体=注释内容]
	 * @return
	 * @Author yujt
	 */
	private XSSFWorkbook exportXLSXExcelWithExtraCol(String title,
			String[] headersName, String[] headersId, Map<String,T> map) {
		// 创建excel工作簿
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建第一个sheet（页），并命名
		XSSFSheet sheet = wb.createSheet(title);
		// 设置默认高度
		sheet.setDefaultColumnWidth((short) 10);
		// 创建两种单元格格式
		XSSFCellStyle cs = wb.createCellStyle();
		XSSFCellStyle cs2 = wb.createCellStyle();
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 根据选择的字段生成表头
		for (short i = 0; i < headersName.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headersName[i]);
			cell.setCellStyle(cs);
		}
		try {
			// 设置每行每列的值
			int zdRow = 1;
			String textVal = null;
			Object val;
			String getMethodName;
			Method getMethod;
			T entity;
			String extraCol;
			Class tCls;
			int length = headersId.length;//列数
			for(Map.Entry<String,T> entry : map.entrySet()){
				entity = entry.getValue();
				extraCol = entry.getKey();

				row = sheet.createRow(zdRow);
				tCls = entity.getClass();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				// Field[] fields = l.getClass().getDeclaredFields();
				for (int i = 0; i <= length; i++) {
					if(i == length){
						XSSFCell cellTemp = row.createCell(i);
						cellTemp.setCellValue(extraCol);
						cellTemp.setCellStyle(cs2);
					}else{
						getMethodName = "get"
								+ headersId[i].substring(0, 1).toUpperCase()
								+ headersId[i].substring(1);
						getMethod = tCls.getMethod(getMethodName, new Class[] {});
						val = getMethod.invoke(entity, new Object[] {});
						if (val != null) {
							textVal = val.toString();
						} else {
							textVal = "";
						}
						XSSFCell cellTemp = row.createCell(i);
						cellTemp.setCellValue(textVal);
						cellTemp.setCellStyle(cs2);
					}
				}
				zdRow++;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			return wb;
		}
	}

	/**
	 * <p>Description：导出数据到excel，每个实体都有附加列</p>
	 * @param title excel文件名
	 * @param headersName
	 * @param headersId
	 * @param map 每个元素[实体=注释内容]
	 * @return
	 * @Author yujt
	 */
	@SuppressWarnings("deprecation")
	private HSSFWorkbook exportExcelWithExtraCol(String title,
			String[] headersName, String[] headersId, Map<String,T> map) {
		// 创建excel工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		HSSFSheet sheet = wb.createSheet(title);
		// 设置默认高度
		sheet.setDefaultColumnWidth((short) 10);
		// 创建两种单元格格式
		HSSFCellStyle cs = wb.createCellStyle();
		cs = setHeadStyle(wb, cs);
		HSSFCellStyle cs2 = wb.createCellStyle();
		cs2 = setbodyStyle(wb, cs2);
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		// 根据选择的字段生成表头
		for (short i = 0; i < headersName.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headersName[i]);
			cell.setCellStyle(cs);
		}
		try {
			// 设置每行每列的值
			int zdRow = 1;
			String textVal = null;
			Object val;
			String getMethodName;
			Method getMethod;
			T entity;
			String extraCol;
			Class tCls;
			int length = headersId.length;//列数
			for(Map.Entry<String,T> entry : map.entrySet()){
				entity = entry.getValue();
				extraCol = entry.getKey();

				row = sheet.createRow(zdRow);
				tCls = entity.getClass();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				for (int i = 0; i <= length; i++) {
					if(i == length){
						HSSFCell cellTemp = row.createCell(i);
						cellTemp.setCellValue(extraCol);
						cellTemp.setCellStyle(cs2);
					}else{
						getMethodName = "get"
								+ headersId[i].substring(0, 1).toUpperCase()
								+ headersId[i].substring(1);
						getMethod = tCls.getMethod(getMethodName, new Class[] {});
						val = getMethod.invoke(entity, new Object[] {});
						if (val != null) {
							textVal = val.toString();
						} else {
							textVal = "";
						}
						HSSFCell cellTemp = row.createCell(i);
						cellTemp.setCellValue(textVal);
						cellTemp.setCellStyle(cs2);
					}
				}
				zdRow++;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			return wb;
		}
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出
	 *
	 * @param title
	 *            表格标题名
	 * @param headersName
	 *            表格属性列名数组
	 * @param headersId
	 *            表格属性列名对应的字段
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */
	@SuppressWarnings("deprecation")
	public HSSFWorkbook exportExcel(String title, String[] headersName,
			String[] headersId, List<T> dtoList) {
		// 创建excel工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		HSSFSheet sheet = wb.createSheet(title);
		// 设置默认高度
		sheet.setDefaultColumnWidth((short) 10);
		// 创建两种单元格格式
		HSSFCellStyle cs = wb.createCellStyle();
		cs = setHeadStyle(wb, cs);
		HSSFCellStyle cs2 = wb.createCellStyle();
		cs2 = setbodyStyle(wb, cs2);
		// 创建第一行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		// 根据选择的字段生成表头
		for (short i = 0; i < headersName.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headersName[i]);
			cell.setCellStyle(cs);
		}
		try {
			// 设置每行每列的值
			int zdRow = 1;
			String textVal = null;
			Object val;
			String getMethodName;
			Method getMethod;
			for (int i = 0; i < dtoList.size(); i++) {
				// 创建行，
				row = sheet.createRow(zdRow);
				T l = dtoList.get(i);
				Class tCls = l.getClass();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				// Field[] fields = l.getClass().getDeclaredFields();

				for (int j = 0; j < headersId.length; j++) {
					getMethodName = "get"
							+ headersId[j].substring(0, 1).toUpperCase()
							+ headersId[j].substring(1);
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					val = getMethod.invoke(l, new Object[] {});
					if (val != null) {
						textVal = val.toString();
					} else {
						textVal = "";
					}
					HSSFCell cellTemp = row.createCell(j);
					cellTemp.setCellValue(textVal);
					cellTemp.setCellStyle(cs2);
				}
				zdRow++;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			return wb;
		}

	}
	public XSSFWorkbook exportXLSXExcel(String title, String[] headersName,
			String[] headersId, List<T> dtoList) {
		// 创建excel工作簿
		XSSFWorkbook wb = new XSSFWorkbook();
		// 创建第一个sheet（页），并命名
		XSSFSheet sheet = wb.createSheet(title);
		// 设置默认高度
		sheet.setDefaultColumnWidth((short) 10);
		// 创建两种单元格格式
		XSSFCellStyle cs = wb.createCellStyle();
		//cs = setHeadStyle(wb, cs);
		XSSFCellStyle cs2 = wb.createCellStyle();
		//cs2 = setbodyStyle(wb, cs2);
		// 创建第一行
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell;
		// 根据选择的字段生成表头
		for (short i = 0; i < headersName.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(headersName[i]);
			cell.setCellStyle(cs);
		}
		try {
			// 设置每行每列的值
			int zdRow = 1;
			String textVal = null;
			Object val;
			String getMethodName;
			Method getMethod;
			for (short i = 0; i < dtoList.size(); i++) {
				// 创建行，
				row = sheet.createRow(zdRow);
				T l = dtoList.get(i);
				Class tCls = l.getClass();
				// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
				// Field[] fields = l.getClass().getDeclaredFields();

				for (short j = 0; j < headersId.length; j++) {
					getMethodName = "get"
							+ headersId[j].substring(0, 1).toUpperCase()
							+ headersId[j].substring(1);
					getMethod = tCls.getMethod(getMethodName, new Class[] {});
					val = getMethod.invoke(l, new Object[] {});
					if (val != null) {
						textVal = val.toString();
					} else {
						textVal = "";
					}
					XSSFCell cellTemp = row.createCell(j);
					cellTemp.setCellValue(textVal);
					cellTemp.setCellStyle(cs2);
				}
				zdRow++;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			return wb;
		}

	}

	/**
	 *
	 * @param file 文件全文路径d:\xx\xx.xls
	 * @param option sheet选项卡索引
	 * @param dataName
	 * @return
	 */
	public List<T> importExcel(String file, int option, String[] dataName) {
		List<T> dist = new ArrayList();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = clazz.getDeclaredFields();
			Map fieldmap = new HashMap();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				String fieldname = f.getName();
				String setMethodName = "set"
						+ fieldname.substring(0, 1).toUpperCase()
						+ fieldname.substring(1);
				// 构造调用的method，
				Method setMethod = clazz.getMethod(setMethodName,
						new Class[] { f.getType() });
				fieldmap.put(fieldname, setMethod);
			}
			/**
			 * excel的解析开始
			 */
			// 将传入的File构造为FileInputStream;
			// FileInputStream in = new FileInputStream(file);
			wb = getWorkbook(file);
			// 得到第一页
			Sheet sheet = wb.getSheetAt(0);
			int[] rbs = sheet.getRowBreaks();
			Row row;
			for (int i = 0; i < rbs.length; i++) {
				row = sheet.getRow(i);
				Iterator<Cell> cellbody = row.cellIterator();
				// 得到传入类的实例
				T tObject = clazz.newInstance();
				// 遍历一行的列
				while (cellbody.hasNext()) {
					Cell cell = cellbody.next();
					// 遍历对应关系的集合
					for (int j = 0; j < dataName.length; j++) {
						if (fieldmap.containsKey(dataName)) {
							Method setMethod = (Method) fieldmap.get(dataName);
							// 得到setter方法的参数
							Type[] ts = setMethod.getGenericParameterTypes();
							// 只要一个参数
							String xclass = ts[0].toString();
							// 判断参数类型
							if (xclass.equals("class java.lang.String")) {
								setMethod.invoke(tObject,
										cell.getStringCellValue());
							} else if (xclass.equals("class java.util.Date")) {
								setMethod.invoke(tObject, DateUtil
										.StringToDate(
												cell.getStringCellValue(),
												DateStyle.YYYY_MM_DD));
							} else if (xclass.equals("class java.lang.Boolean")) {
								Boolean boolname = true;
								if (cell.getStringCellValue().equals("否")) {
									boolname = false;
								}
								setMethod.invoke(tObject, boolname);
							} else if (xclass.equals("class java.lang.Integer")) {
								setMethod.invoke(tObject,
										new Integer(cell.getStringCellValue()));
							} else if (xclass.equals("class java.lang.Long")) {
								setMethod.invoke(tObject,
										new Long(cell.getStringCellValue()));
							}
						}
					}
				}
				dist.add(tObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList();
		}
		return dist;
	}


	/**
	 * <p>Description：xls文件数据导入</p>
	 * @param file xls文件路径
	 * @param option xls文件工作簿下标
	 * @param map 每个子元素 key ：excel第几列；value ：对应的实体属性  如：[1=qzh]
	 * @return
	 * @Author yujt
	 * @Date   2016-3-7  下午4:39:38
	 */
	@SuppressWarnings("static-access")
	public List<T> importExcel(String file, int option,Map<String, String> map) {
		List<T> dist = new ArrayList<T>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = clazz.getDeclaredFields();
			Map<String,Method> fieldmap = new HashMap<String,Method>();
			Field f=null;
			String fieldname;
			String setMethodName;
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				f = filed[i];
				fieldname = f.getName();
				setMethodName = "set"
						+ fieldname.substring(0, 1).toUpperCase()
						+ fieldname.substring(1);
				if(!"setSerialVersionUID".equals(setMethodName)){
					// 构造调用的method，
					Method setMethod = clazz.getMethod(setMethodName,
							new Class[] { f.getType() });
					fieldmap.put(fieldname, setMethod);
				}
			}
			// 将传入的File构造为FileInputStream;
			wb = getWorkbook(file);
			// 得到工作簿
			Sheet sheet = wb.getSheetAt(option);
			T tObject;
			String key;
			String value;
			for(Row row : sheet){ //每行
				tObject = clazz.newInstance();
				if(row.getRowNum() > 0){
					for(Cell cell : row){ //每个单元格
						if(cell != null){
							cell.setCellType(cell.CELL_TYPE_STRING); //单元格格式控制
						}
						int index = cell.getColumnIndex();
						for(Map.Entry<String, String> entry : map.entrySet()){
							key = entry.getKey();
							value = entry.getValue(); //实体属性
							if(key.equals(String.valueOf(index))){
								if (fieldmap.containsKey(value)) {
									Method setMethod = fieldmap.get(value);
									// 得到setter方法的参数
									Type[] ts = setMethod.getGenericParameterTypes();
									// 只要一个参数
									String xclass = ts[0].toString();
									// 判断参数类型
									if (xclass.equals("class java.lang.String")) {
										setMethod.invoke(tObject,cell.getStringCellValue());
									} else if (xclass.equals("class java.util.Date")) {
										setMethod.invoke(tObject, DateUtil.StringToDate(cell.getStringCellValue(),DateStyle.YYYY_MM_DD));
									} else if (xclass.equals("class java.lang.Boolean")) {
										Boolean boolname = true;
										if (cell.getStringCellValue().equals("否")) {
											boolname = false;
										}
										setMethod.invoke(tObject, boolname);
									} else if (xclass.equals("class java.lang.Integer")) {
										setMethod.invoke(tObject,new Integer(cell.getStringCellValue()));
									} else if (xclass.equals("class java.lang.Long")) {
										setMethod.invoke(tObject,new Long(cell.getStringCellValue()));
									}
								}
							}
						}
					}
					dist.add(tObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
		wb.cloneSheet(option);
		return dist;
	}

	/**
	 * <p>Description：获得需要导入到数据库的实体的条目集合，过滤掉不符合要求（必填字段为空，字段超出最大长度）的条目</p>
	 * @param failed 被过滤的条目的集合
	 * @param filepath excel文件路径
	 * @param option excel文件数据库表下标
	 * @param rules 字符串集合，每个元素的由“第几列,字段英文名,是否必填,字段最大长度”拼接
	 * @return
	 * @Author yujt
	 */
	@SuppressWarnings("static-access")
	public List<T> importExcelOnCheck(Map<String,T> failed,String file, int option,List<String> rules) {
		List<T> dist = new ArrayList<T>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = clazz.getDeclaredFields();
			Map<String,Method> fieldmap = new HashMap<String,Method>();
			Field f=null;
			String fieldname;
			String setMethodName;
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				f = filed[i];
				fieldname = f.getName();
				setMethodName = "set"+ fieldname.substring(0, 1).toUpperCase()+ fieldname.substring(1);
				if(!"setSerialVersionUID".equals(setMethodName)){
					// 构造调用的method，
					Method setMethod = clazz.getMethod(setMethodName,new Class[] { f.getType() });
					fieldmap.put(fieldname, setMethod);
				}
			}
			// 将传入的File构造为FileInputStream;
			wb = getWorkbook(file);
			// 得到工作簿
			Sheet sheet = wb.getSheetAt(option);
			T entity;
			String cellNum;     //第几列
			String fieldEN;		//字段英文名
			String isRequired;  //是否必填
			String maxLength;   //最大长度
			Method setMethod;
			String msg4Required = "";
			String msg4Length = "";
			String msg = "";
			String cellValue = "";
			boolean flag;
			for(Row row : sheet){ //每行
				entity = clazz.newInstance();
				if(row.getRowNum() > 0){
					flag = true;
					for(Cell cell : row){ //每个单元格
						if(cell != null){
							cell.setCellType(cell.CELL_TYPE_STRING); //单元格格式控制
						}
						int index = cell.getColumnIndex();
						for (String oneRule : rules) {
							String[] allRules = oneRule.split(",");
							cellNum = allRules[0];
							fieldEN = allRules[1];
							isRequired = allRules[2];
							maxLength = allRules[3];

							if(cellNum.equals(String.valueOf(index))){
								if (fieldmap.containsKey(fieldEN)) {
									setMethod = fieldmap.get(fieldEN);
									// 得到setter方法的参数
									Type[] ts = setMethod.getGenericParameterTypes();
									// 只要一个参数
									String xclass = ts[0].toString();
									cellValue = cell.getStringCellValue();
									// 判断参数类型
									if (xclass.equals("class java.lang.String")) {
										setMethod.invoke(entity,cellValue);
									} else if (xclass.equals("class java.util.Date")) {
										setMethod.invoke(entity, DateUtil.StringToDate(cellValue,DateStyle.YYYY_MM_DD));
									} else if (xclass.equals("class java.lang.Boolean")) {
										Boolean boolname = true;
										if (cellValue.equals("否")) {boolname = false;}
										setMethod.invoke(entity, boolname);
									} else if (xclass.equals("class java.lang.Integer")) {
										setMethod.invoke(entity,new Integer(cellValue));
									} else if (xclass.equals("class java.lang.Long")) {
										setMethod.invoke(entity,new Long(cellValue));
									}
								}
								if(StringUtils.isBlank(cellValue)){
									if(isRequired.equals("0")){
										flag = false;
										msg4Required += (index+1) + ",";
									}
								}else if(cellValue.length() > Integer.parseInt(maxLength)){
									flag = false;
									System.out.println(fieldEN+"的值"+cellValue+"超出最大长度,因此这个条目无法导入到数据库");
									msg4Length += (index+1) + ",";
									continue;
								}
							}
						}
					}
					//此处做处理是因为导入的数据可能是错误的也要导入进去
//					if(flag){
						dist.add(entity);
//					}else{
//						if(StringUtils.isNotBlank(msg4Required)){
//							msg = "列"+msg4Required+"必填。";
//						}
//						msg4Required = "";
//						if(StringUtils.isNotBlank(msg4Length)){
//							msg += "列"+msg4Length+"超出长度。";
//						}
//						msg4Length = "";
//						failed.put("原第"+(row.getRowNum()+1)+"行:"+msg,entity);
//						msg = "";
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
		wb.cloneSheet(option);
		return dist;
	}


	public Workbook getWorkbook(String file) {
		if (StringUtils.isNotBlank(file)) {
			InputStream stream = null;
			try {
				stream = new FileInputStream(file);
				if(file.endsWith(".xls")){
					wb = (Workbook) new HSSFWorkbook(stream);
				}else{
					wb = (Workbook) new XSSFWorkbook(stream);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return wb;
	}

	/**
	 * 取得当前Sheet 所有列名称
	 *
	 * @param option
	 */
	public List<String> getCells(int option) {
		// List list = List.ArrayList;
		List<String> list = new ArrayList<String>();
		Sheet sheet = (Sheet) wb.getSheetAt(option);
		Cell cell = null;
		Row row = (Row) sheet.getRow(0);
		if (null != row) {
			int totalCells = row.getPhysicalNumberOfCells();
			for (int i = 0; i < totalCells; i++) {
				cell = row.getCell(i);
				list.add(cell.toString());
			}
		}
		return list;
	}

	public HSSFCellStyle setHeadStyle(HSSFWorkbook workbook, HSSFCellStyle style) {
		// style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		// style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样样式
		style.setFont(font);
		return style;
	}

	public HSSFCellStyle setbodyStyle(HSSFWorkbook workbook,
			HSSFCellStyle style2) {
		// style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成字体
		HSSFFont font2 = workbook.createFont();
		// font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font2.setFontHeightInPoints((short) 10);
		font2.setColor(IndexedColors.BLACK.getIndex());
		// 把字体应用到当前的样样式
		style2.setFont(font2);
		return style2;
	}

	   /* public static void exportExcelDisk( String xls_write_Address,ArrayList<ArrayList> ls,String[] sheetnames) throws IOException  {

	        //PropertyConfigurator.configure(class_path+"log4j.properties");//获取 log4j 配置文件
	        //Logger logger = Logger.getLogger(Config.class ); //获取log4j的实例
	        FileOutputStream output = new FileOutputStream(new File(xls_write_Address));  //读取的文件路径
	        SXSSFWorkbook wb = new SXSSFWorkbook(10000);//内存中保留 10000 条数据，以免内存溢出，其余写入 硬盘


	        for(int sn=0;sn<ls.size();sn++){
	            Sheet sheet = wb.createSheet(String.valueOf(sn));

	            wb.setSheetName(sn, sheetnames[sn]);
	            ArrayList<String[]> ls2 = ls.get(sn);
	            for(int i=0;i<ls2.size();i++){
	                Row row = sheet.createRow(i);
	                String[] s = ls2.get(i);
	                for(int cols=0;cols<s.length;cols++){
	                    Cell cell = row.createCell(cols);
	                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式
	                    sheet.setColumnWidth(cols, s[cols].length()*384); //设置单元格宽度
	                    cell.setCellValue(s[cols]);//写入内容
	                }
	               /* if(!(tips_cmd.equals("none"))&&tips_cmd!=null&&!(tips_cmd.equals(""))) {
	                    if(tips_cmd.equals("all")){
	                        //logger.debug("\n写入中："+s[0]+"\t"+s[1]+"\t"+s[2]+"\t"+s[3]+"\t"+ s[4]);
	                    }//else
	                    if(i%DataConvert.stringToInt(tips_cmd)==0){
	                        logger.debug("\n写入中："+s[0]+"\t"+s[1]+"\t"+s[2]+"\t"+s[3]+"\t"+ s[4]);
	                    }
	                }
	            }
	         }
	        wb.write(output);
	        output.close();
	    }*/



		/**
		 * <p>Description：创建一个新的excel文件</p>
		 * @param fileDir excel文件的绝对路径
		 * @param sheetName 工作簿名称
		 * @param titleRow 列名
		 * @Author yujt
		 * @Date   2016-5-12  下午1:32:52
		 */
        public void createExcel(String fileDir,String sheetName,String titleRow[]){
        	HSSFWorkbook workbook = new HSSFWorkbook();
            //添加工作簿
            workbook.createSheet(sheetName);
            FileOutputStream out = null;
            try {
                Row row = workbook.getSheet(sheetName).createRow(0);    //创建第一行
                for(int i = 0;i < titleRow.length;i++){  //第一行生成列名
                    Cell cell = row.createCell(i);
                    cell.setCellValue(titleRow[i]);
                }

                out = new FileOutputStream(fileDir);
                workbook.write(out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                	if(out != null){
                		out.close();
                	}

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}