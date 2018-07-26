package com.kevin.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportUtil {
    
    public static Logger logger = LoggerFactory.getLogger(ExportUtil.class);
    
    private static final String DEFAULT_FILE_NAME = "导出数据表格";

    /**
     * 导出集合中的数据到excel文件，导出列在params中列出
     * @param dataList
     * @param params
     */
    public static <T> BookResult exportData(List<T> dataList,String[] params){
        
        return exportData(dataList, params, DEFAULT_FILE_NAME);
    }
    
    /**
     * 导出普通格式的excel文件，并规定文件名称(一个sheet，且不规定sheet的title)
     * @param dataList
     * @param params
     * @param fileName
     */
    public static <T> BookResult exportData(List<T> dataList,String[] params,String fileName){
        
        //没有数据列，直接返回
        if(params == null || params.length == 0)
            
            return new BookResult(null, "无导出列");
        //没有数据，直接返回
        if(dataList == null || dataList.size() == 0)
            
            return new BookResult(null, "无导数据");
        //新建工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();
        
        HSSFSheet sheet = workBook.createSheet();
        
        //列数等于字段数
        int columnNum = params.length;
        //行数
        int rowNum = dataList.size();
        //循环行数
        for (int i = 0; i < rowNum; i++) {
            
            HSSFRow row = sheet.createRow(i);
            //获取数据
            T data = dataList.get(i);
            //获取class对象
            Class<? extends Object> claz = dataList.get(0).getClass();
            //循环列数
            for (int j = 0; j < columnNum ; j++) {
                
                HSSFCell cell = row.createCell(j);
                try {
                    
                    Field f = claz.getDeclaredField(params[j]);
                    //反射方法获取字段的值
                    if(f != null){
                        
                        Object value = f.get(data);
                        String valStr = null;
                        if(value instanceof Date){
                            
                            valStr = DateUtil.format2String((Date)value, "yyyy-MM-dd HH:mm:ss");
                        }else{
                            
                            valStr = value.toString();
                        }
                        
                        cell.setCellValue(valStr);
                    }
                } catch (NoSuchFieldException e) {
                    
                    String errorInfo = claz.getName() + "没有字段：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                } catch (IllegalArgumentException e) {
                    
                    String errorInfo = claz.getName() + "字段获取错误：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                } catch (IllegalAccessException e) {
                    
                    String errorInfo = claz.getName() + "字段获取错误：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                }
            }
        }
        return new BookResult(workBook);
    }
    
    public static <T> BookResult exportDataInObj(List<T> dataList,Object[] params,String[] paramNames){
        
        
      //没有数据列，直接返回
        if(params == null || params.length == 0)
            
            return new BookResult(null, "无导出列");
        //没有数据，直接返回
        if(dataList == null || dataList.size() == 0)
            
            return new BookResult(null, "无导数据");
        //新建工作簿
        HSSFWorkbook workBook = new HSSFWorkbook();
        
        HSSFSheet sheet = workBook.createSheet();
        
        //列数等于字段数
        int columnNum = params.length;
        //行数
        int rowNum = dataList.size();
        
        //插入行名
        HSSFRow nameRow = sheet.createRow(0);
        for (int i = 0; i < paramNames.length; i++) {
            
            HSSFCell cell = nameRow.createCell(i);
            cell.setCellValue(paramNames[i]);
            
        }
        
        //循环行数
        for (int i = 0; i < rowNum; i++) {
            
            HSSFRow row = sheet.createRow(i+1);
            //获取数据
            T data = dataList.get(i);
            //获取class对象
            Class<? extends Object> claz = dataList.get(0).getClass();
            //循环列数
            for (int j = 0; j < columnNum ; j++) {
                
                HSSFCell cell = row.createCell(j);
                try {
                    
                    //判断为string
                    if(params[j] instanceof String){
                        
                        Method method = claz.getMethod(getMethodName((String)params[j]));
                        //反射方法获取字段的值
                        if(method != null){
                            
                            Object value = method.invoke(data);
                            String valStr = null;
                            if(value instanceof Date){
                                
                                valStr = DateUtil.format2String((Date)value, "yyyy-MM-dd HH:mm:ss");
                            }else{
                                
                                if(value instanceof Number){
                                    
                                    cell.setCellValue(new Double(value.toString()));
                                }else{
                                    
                                    if(value != null)
                                        
                                        valStr = value.toString();
                                    else
                                        valStr = "空";
                                }
                            }
                            
                            if(valStr != null)
                                cell.setCellValue(valStr);
                        }
                    }else if(params[j] instanceof KeyValue){
                        
                        Method method = claz.getMethod(getMethodName(((KeyValue)params[j]).getKey()));
                        //获取需要导出的字段
                        Object val = ((KeyValue)params[j]).getValue();
                        //定义获取的值
                        Object newData = method.invoke(data);
                        while(val instanceof KeyValue){
                            newData = method.invoke(newData);
                            Class<? extends Object> clazChild = newData.getClass();
                            method = clazChild.getMethod(getMethodName(((KeyValue)val).getKey()));
                            val = ((KeyValue)val).getValue();
                        }
                        
                        //获取最终value
                        {
                            
                            Class<? extends Object> clazChild = newData.getClass();
                            method = clazChild.getMethod(getMethodName((String)val));
                            val = method.invoke(newData);
                        }
                        String valStr = null;
                        if(val instanceof Date){
                            
                            valStr = DateUtil.format2String((Date)val, "yyyy-MM-dd HH:mm:ss");
                        }else{
                            
                            if(val instanceof Number){
                                
                                cell.setCellValue(new Double(val.toString()));
                            }else{
                                
                                if(val != null)
                                    
                                    valStr = val.toString();
                                else
                                    valStr = "空";
                            }
                        }
                        
                        if(valStr != null)
                            cell.setCellValue(valStr);
                        
                    }else{
                        
                        continue;
                    }
                } catch (IllegalArgumentException e) {
                    
                    String errorInfo = claz.getName() + "字段获取错误：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                } catch (IllegalAccessException e) {
                    
                    String errorInfo = claz.getName() + "字段获取错误：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                } catch (NoSuchMethodException e) {
                    
                    String errorInfo = claz.getName() + "没有方法：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                }  catch (InvocationTargetException e) {
                    
                    String errorInfo = claz.getName() + "中执行方法失败：" + params[j];
                    logger.error(errorInfo);
                    e.printStackTrace();
                    return new BookResult(workBook, errorInfo);
                }
            }
        }
        return new BookResult(workBook);
    }
    
    /**
     * 导出excel文件
     * @param response 本次请求的response
     * @param workbook 编写完成的workBook工作簿
     * @param fileName 导出的文件名，如果文件名为“test.xls”，则fileName="test"
     */
    public static void exportExcel(HttpServletResponse response,HSSFWorkbook workbook,String fileName){
        
        try {
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename="+new String( (fileName + ".xls").getBytes(),  
                    "iso-8859-1")); 
            if(response != null){
                
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            }
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
    
    /**
     * 根据字符串名，获取自动生成的get方法名
     * @param str 字段名称
     * @return  字段的get方法名
     */
    public static String getMethodName(String str){
        
        if(str != null && str.length() > 0){
            
            //如果不是大写字母
            if(str.charAt(0)>90){
                
                str = str.substring(0, 1).toUpperCase() + str.substring(1);
            }else{
                
                str = str.substring(0, 1).toLowerCase() + str.substring(1);
            }
        }
        return "get" + str;
    }
    
    public static class BookResult{
       
        private HSSFWorkbook workBook;
        
        private String errorInfo;
        
        private boolean success = true;
        
        
        
        /**
         * 导出信息
         * @param workBook 工作簿对象
         * @param errorInfo 错误信息
         * @param success 导出结果
         */
        public BookResult(HSSFWorkbook workBook, String errorInfo, boolean success) {
            super();
            this.workBook = workBook;
            this.errorInfo = errorInfo;
            this.success = success;
        }
        

        /**
         * 导出成功
         * @param workBook 工作簿对象
         */
        public BookResult(HSSFWorkbook workBook) {
            super();
            this.workBook = workBook;
        }
        


        
        /**
         * 导出失败
         * @param workBook 工作簿对象
         * @param errorInfo 错误信息
         */
        public BookResult(HSSFWorkbook workBook, String errorInfo) {
            super();
            this.workBook = workBook;
            this.errorInfo = errorInfo;
            this.success = false;
        }


        public HSSFWorkbook getWorkBook() {
            return workBook;
        }

        public void setWorkBook(HSSFWorkbook workBook) {
            this.workBook = workBook;
        }

        public String getErrorInfo() {
            return errorInfo;
        }

        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
        
        
    }
    
    public static class KeyValue{
        
        private String key;
        
        private Object value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public KeyValue(String key, Object value) {
            super();
            this.key = key;
            this.value = value;
        }
        
    }
}
