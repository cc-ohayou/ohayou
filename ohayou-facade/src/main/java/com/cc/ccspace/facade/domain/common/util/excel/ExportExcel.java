

package com.cc.ccspace.facade.domain.common.util.excel;


import com.cc.ccspace.facade.domain.common.constants.CommonConstants;
import com.cc.ccspace.facade.domain.common.util.push.HttpMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @description ff
 * @author CF create on 2018/2/7 15:10
 */
public class ExportExcel<T> {

    /** Excel头部 */
    private List<Map<String, String>> header = null;

    /** Excel数据 */
    private List<T> datas = null;

    /** Sheet名称 */
    private String sheetName = CommonConstants.NOTHING;

    /** 标题 */
    private String title = CommonConstants.NOTHING;

    /** 文件后缀 */
    public static final String EXCEL_FILE_TYPE = ".xls";

    /** 时间格式化 */
    private static SimpleDateFormat sdf = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss");

    /**
     * Description: 导出Excel<br>
     * 
     * @param path
     * @param fileName
     * @return
     */
    public String exportExcel(String path, String fileName) {
        // 参数异常判断
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(fileName)) {
            return CommonConstants.NOTHING;
        }
        // 参数异常判断
        if (null == header || null == datas || header.isEmpty()) {
            return CommonConstants.NOTHING;
        }
        // 构造Excel数据
        List<Map<String, String>> dataLst = excelDatas();
        // 创建工作薄
        HSSFWorkbook workBook = makeWorkBook(dataLst);
        // 生成Excel文件
        return wirteExcel(path, fileName, workBook);
    }

    /**
     * CSV文件生成方法
     * @param head
     * @param dataList
     * @param outPutPath
     * @param filename
     * @return
     */
    public static File createCSVFile(List<Object> head, List<Object> dataList,String outPutPath, String filename) {
        File csvFile = null;
        BufferedWriter csvWtriter = null;
        
        try {
            csvFile = new File(outPutPath + File.separator + filename + ".csv");
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();

            // GB2312使正确读取分隔符","
            csvWtriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"), 1024);

            // 写入文件内容
                writeRow(dataList, csvWtriter);
            
            csvWtriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvWtriter.close();
                Runtime.getRuntime().gc();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }
    

    /**
     * 写一行数据方法
     * @param row
     * @param csvWriter
     * @throws IOException
     */
    private static void writeRow(List<Object> row, BufferedWriter csvWriter) throws IOException {
        // 写入文件头部
        for (Object data : row) {
            StringBuffer buf = new StringBuffer();
            String rowStr =  buf.append("\"").append(data).append("\t\",").toString();
            csvWriter.write(rowStr);
        }
        csvWriter.newLine();
    }
    /**
     * Description: 生成Excel文件<br>
     * 
     * @param path
     * @param fileName
     * @param
     * @return String
     */
    private String wirteExcel(String path, String fileName,
                              HSSFWorkbook workBook) {
        // 取得完整路径
        String fullPath = excelFullPath(path, fileName);
        // 文件输出流
        FileOutputStream fout = null;
        try {
            // 实例化输出流
            fout = new FileOutputStream(path + fileName + EXCEL_FILE_TYPE);
            // 写文件
            workBook.write(fout);
        } catch (Exception e) {
            return CommonConstants.NOTHING;
        } finally {
            // 关闭流
            IOUtils.closeQuietly(fout);
        }
        // 返回路径
        return fullPath;
    }

    /**
     * Description: 取得Excel完整输出路径<br>
     * 
     * @param path
     * @param fileName
     * @return String
     */
    public static String excelFullPath(String path, String fileName) {
        // 完整路径
        String fullPath = CommonConstants.NOTHING;
        // 路径复习
        File file = new File(path);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        // 判断是否已/结尾
        if (path.endsWith(File.separator)) {
            fullPath = path + fileName;
        } else {
            fullPath = path + File.separator + fileName;
        }
        // 判断是否包含后缀
        if (!fileName.toLowerCase().endsWith(EXCEL_FILE_TYPE)) {
            fullPath = fullPath + EXCEL_FILE_TYPE;
        }
        return fullPath;
    }

    /**
     * Description: 创建工作薄<br>
     * 
     * @param dataLst
     * @return HSSFWorkbook
     */
    private HSSFWorkbook makeWorkBook(List<Map<String, String>> dataLst) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook workBook = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = null;
        // 是否有Sheet名称
        if (!StringUtils.isEmpty(sheetName)) {
            // 设定Sheet名称
            sheet = workBook.createSheet(sheetName);
        } else {
            // 使用默认名称
            sheet = workBook.createSheet();
        }
        // 行游标定义
        int rowIndex = CommonConstants.NUM_ZERO;
        // 创建Excel标题
        rowIndex = createTitle(workBook, sheet, rowIndex);
        // 创建Excel头部
        rowIndex = createHead(workBook, sheet, rowIndex);
        // 创建Excel数据
        createData(workBook, sheet, rowIndex, dataLst);
        // 返回工作薄
        return workBook;
    }

    /**
     * Description: 创建Excel数据<br>
     * 
     * @param workBook
     * @param sheet
     * @param rowIndex
     * @param dataLst
     */
    private void createData(HSSFWorkbook workBook, HSSFSheet sheet,
                            int rowIndex, List<Map<String, String>> dataLst) {
        // 参数异常判断
        if (null == dataLst || dataLst.isEmpty()) {
            return;
        }
        // 数据长度
        int size = dataLst.size();
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = makeBodyStyle(workBook);
        // 循环处理
        for (int i = 0; i < size; i++ ) {
            // 当前数据
            Map<String, String> data = dataLst.get(i);
            // 第三步，在sheet中添加第rowIndex行
            HSSFRow row = sheet.createRow(i + rowIndex);
            // 设定行数据
            createDataRow(data, row, style);
        }
    }

    /**
     * Description: 设定行数据<br>
     * 
     * @param data
     * @param row
     * @param style
     */
    private void createDataRow(Map<String, String> data, HSSFRow row,
                               HSSFCellStyle style) {
        // 头部列数
        int size = header.size();
        // 循环处理
        for (int i = 0; i < size; i++ ) {
            // 当前列
            Map<String, String> head = header.get(i);
            // 创建当前单元格
            HSSFCell cell = row.createCell(i);
            // 取得filed
            String field = head.get(CommonConstants.EXCEL_FIELD);
            // 设定单元格值为filed值
            cell.setCellValue(data.get(field));
            // 设定样式
            cell.setCellStyle(style);
        }
    }

    /**
     * Description: 创建Excel头部<br>
     * 
     * @param workBook
     * @param sheet
     * @param rowIndex
     * @return
     */
    private int createHead(HSSFWorkbook workBook, HSSFSheet sheet,
                           int rowIndex) {
        // 第三步，在sheet中添加表头第rowIndex行
        HSSFRow row = sheet.createRow(rowIndex);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = makeHeadStyle(workBook);
        // 头部列数
        int size = header.size();
        // 循环处理
        for (int i = 0; i < size; i++ ) {
            // 当前列
            Map<String, String> head = header.get(i);
            // 设定列宽
            sheet.setColumnWidth(i, 20 * 256);
            // 创建当前单元格
            HSSFCell cell = row.createCell(i);
            // 设定单元格值为当前列名称
            cell.setCellValue(head.get(CommonConstants.EXCEL_FIELD_NAME));
            // 设定样式
            cell.setCellStyle(style);
        }
        return rowIndex + CommonConstants.NUM_ONE;
    }

    /**
     * Description: 创建Title<br>
     * 
     * @param workBook
     * @param sheet
     * @param rowIndex
     * @return int
     */
    private int createTitle(HSSFWorkbook workBook, HSSFSheet sheet,
                            int rowIndex) {
        // 如果未设定Title
        if (StringUtils.isEmpty(title)) {
            return rowIndex;
        }
        // 列数
        int cols = header.size();
        // 第三步，在sheet中添加表头第rowIndex行
        HSSFRow row = sheet.createRow(rowIndex);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = makeTitleStyle(workBook);
        // 取得第一个单元格
        HSSFCell cell = row.getCell(CommonConstants.NUM_ZERO);
        if (null == cell) {
            // 创建第一个单元格
            cell = row.createCell(CommonConstants.NUM_ZERO);
        }
        // 设定Title
        cell.setCellValue(title);
        // 设定Title样式
        cell.setCellStyle(style);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex,
                CommonConstants.NUM_ZERO, cols - CommonConstants.NUM_ONE));
        return rowIndex + CommonConstants.NUM_ONE;
    }

    /**
     * Description: 设定Title样式<br>
     * 
     * @param workBook
     * @return HSSFCellStyle
     */
    private HSSFCellStyle makeTitleStyle(HSSFWorkbook workBook) {
        HSSFCellStyle style = makeBodyStyle(workBook);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        HSSFFont font = workBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);// 字体
        font.setFontHeightInPoints((short)20);// 字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        font.setColor(HSSFColor.BLACK.index);// 颜色
        style.setFont(font);
        return style;
    }

    /**
     * Description: 设定Head样式<br>
     * 
     * @param workBook
     * @return HSSFCellStyle
     */
    public HSSFCellStyle makeHeadStyle(HSSFWorkbook workBook) {
        HSSFCellStyle style = makeBodyStyle(workBook);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        HSSFFont font = workBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);// 字体
        font.setFontHeightInPoints((short)11);// 字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        font.setColor(HSSFColor.BLACK.index);// 颜色
        style.setFont(font);
        return style;
    }
    public CellStyle makeHeadStyle(SXSSFWorkbook workBook) {
        CellStyle style = makeBodyStyle(workBook);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//设置边框样式
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        Font font = workBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);// 字体
        font.setFontHeightInPoints((short)11);// 字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        font.setColor(HSSFColor.BLACK.index);// 颜色
        style.setFillBackgroundColor(HSSFColor.GREEN.index);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        return style;
    }

    /**
     * Description: 设定样式<br>
     * 
     * @param workBook
     * @return
     */
    public HSSFCellStyle makeBodyStyle(HSSFWorkbook workBook) {
        HSSFCellStyle style = workBook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);

        HSSFFont font = workBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);// 字体
        font.setFontHeightInPoints((short)9);// 字号
        font.setColor(HSSFColor.BLACK.index);// 颜色
        style.setFont(font);
        return style;
    }
    public CellStyle makeBodyStyle(SXSSFWorkbook workBook) {
        CellStyle style = workBook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        Font font = workBook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);// 字体
        font.setFontHeightInPoints((short)9);// 字号
        font.setColor(HSSFColor.BLACK.index);// 颜色
        style.setFont(font);
        style.setWrapText(true);
        return style;
    }

    /**
     * Description: 构造Excel数据<br>
     * 
     * @return List<Map<String, String>>
     */
    public List<Map<String, String>> excelDatas() {
        // 定义Excel返回数组
        List<Map<String, String>> dataLst = new ArrayList<Map<String, String>>();
        // 定义数据集合
        Map<String, String> map = null;
        // 数据记录数
        int size = datas.size();
        // 循环处理
        for (int i = 0; i < size; i++ ) {
            // 获取当前数据行
            T data = datas.get(i);
            // 实例化数据集合
            map = new HashMap<String, String>();
            // 将data赋值给map
            copyDataToMap(data, map);
            // 写入数据集合
            dataLst.add(map);
        }
        // 返回数据
        return dataLst;
    }

    /**
     * Description: 将data赋值给map<br>
     * 
     * @param data
     * @param map
     */
    private void copyDataToMap(T data, Map<String, String> map) {
        // 头部长度
        int size = header.size();
        // 循环处理
        for (int i = 0; i < size; i++ ) {
            // 当前字段
            Map<String, String> head = header.get(i);
            // 取得Field
            String field = head.get(CommonConstants.EXCEL_FIELD);
            // 判断异常
            if (!StringUtils.isEmpty(field)) {
                // 递归反射取值
                String val = invokeFieldValue(data, field, CommonConstants.NUM_ZERO);
                // 写入Map
                map.put(field, val);
            }
        }
    }

    /**
     * Description: 递归反射取值<br>
     * 
     * @param owner
     * @param field
     * @param index
     * @return String
     */
    private String invokeFieldValue(Object owner, String field, int index) {
        // 按点分割字符串
        String[] fields = field.split("\\.");
        // 分割个数
        int size = fields.length;
        // 异常判断
        if (size == CommonConstants.NUM_ZERO) {
            return CommonConstants.NOTHING;
        }
        // 如果递归到最后一层
        if (index == size) {
            // 返回值
            return objectValue(owner);
        } else {
            // 取得当前节点
            String curr = fields[index];
            // 反射获取值
            Object obj = invokeMethod(owner, curr, null);
            // 层数增加
            index++ ;
            // 递归调用
            return invokeFieldValue(obj, field, index);
        }
    }

    /**
     * Description: 取得对象的值<br>
     * 
     * @param obj
     * @return String
     */
    private String objectValue(Object obj) {
        // 异常判断
        if (null == obj) {
            return CommonConstants.NOTHING;
        }
        // 如果是日期类型
        if (obj instanceof Date) {
            // 转换为日期类型
            Date date = (Date)obj;
            // 返回转换格式后的日期
            return sdf.format(date);
        }
        if(obj instanceof String){
        	String str=(String)obj;
        	/*if(str.length()==11&&RegUtil.isPhoneValid(str)){
        		return StrUtils.hidePhone(str);
        	}*/
        }
        
        return obj.toString();
    }

    /**
     * Description: 反射获取值<br>
     * 
     * @param owner
     * @param fieldname
     * @param args
     * @return Object
     */
    private Object invokeMethod(Object owner, String fieldname, Object[] args) {
        Class<? extends Object> ownerClass = owner.getClass();
        Object object = null;
        Method method = null;
        try {
            method = ownerClass.getMethod(toGetter(fieldname));
            object = method.invoke(owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 
     * Description: 获取Get方法名称<br>
     * 
     * @param fieldname
     * @return String
     */
    private String toGetter(String fieldname) {
        // If the second char is upper, make 'get' + field name as getter name. For example,
        // eBlog-> geteBlog
        if (fieldname.length() > 2) {
            String second = fieldname.substring(1, 2);
            if (second.equals(second.toUpperCase())) {
                return new StringBuffer(HttpMethod.METHOD_GET).append(
                    fieldname).toString();
            }
        }
        // Common situation
        fieldname = new StringBuffer(HttpMethod.METHOD_GET).append(
            fieldname.substring(0, 1).toUpperCase()).append(
                fieldname.substring(1)).toString();
        return fieldname;
    }

    /**
     * @param header The header to set.
     */
    public void setHeader(List<Map<String, String>> header) {
        this.header = header;
    }

    /**
     * @param datas The datas to set.
     */
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    /**
     * @param sheetName The sheetName to set.
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}