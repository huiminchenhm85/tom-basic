package com.tom.basic.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static Logger LOGGERBIZ = Logger.getLogger("BIZWARN");
	/**
	 * 判断excel文件格式（xls、xlsx）
	 * @param productFile
	 * @return
	 */
	public static Workbook excelFormat(File file){
		boolean isE2007 = false;    //判断是否是excel2007格式  
		Workbook wb  = null;
		InputStream in = null;
		try{
			in = new FileInputStream(file);  //文件内容输入流
	        if(StringUtils.endsWith(file.getName(), "xlsx")) {
	            isE2007 = true;
	        }
	        if(isE2007) {
	            wb = new XSSFWorkbook(in);  
	        }
	        else {
	            wb = new HSSFWorkbook(in);  
	        }
		}catch(Exception e){
			LOGGERBIZ.error("/ExcelUtils/excelFormat ", e);
		}finally{
			try{
				in.close();
			}catch(Exception e){
				LOGGERBIZ.error("/ExcelUtils/excelFormat ", e);
			}
		}
		return wb;
	}
	/**
	 * 设置单元格文字样式
	 * @param wb
	 * @param cell
	 * @return
	 */
	public static CellStyle fontStyle(Workbook wb, Cell cell){
		Font font = wb.createFont();  
    	font.setColor(HSSFColor.RED.index);  
    	CellStyle style = wb.createCellStyle();  
    	style.setFont(font); 
    	return style;
	}
	
	/**
	 * 设置单元格文字样式
	 * @param wb
	 * @param cell
	 * @return
	 */
	public static CellStyle setWrapTextStyle(Workbook wb, Cell cell){
		CellStyle cellStyle = wb.createCellStyle();    
		cellStyle.setWrapText(true);    
		cell.setCellStyle(cellStyle);
    	return cellStyle;
	}
	/**
	 * 复制单元格
	 * @param faultResultWb
	 * @param srcCell
	 * @param destCell
	 */
	public static void copyCell(Workbook wb, Cell srcCell, Cell destCell){
		int srcCellType = srcCell.getCellType();  
		destCell.setCellType(srcCellType);  
        if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {  
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                destCell.setCellValue(srcCell.getDateCellValue());  
            } else {  
                destCell.setCellValue(srcCell.getNumericCellValue());  
            }  
        } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {  
            destCell.setCellValue(srcCell.getRichStringCellValue());  
        } else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {  
            // nothing21  
        } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {  
            destCell.setCellValue(srcCell.getBooleanCellValue());  
        } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {  
            destCell.setCellErrorValue(srcCell.getErrorCellValue());  
        } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {  
            destCell.setCellFormula(srcCell.getCellFormula());  
        } else { // nothing29  
        } 
	}
	
	/**
	 * 获取列名
	 * @param sheet
	 * @param cell
	 * @return
	 */
	public static String getCellName(Sheet sheet, Cell cell){
		return getCellInfo(sheet.getRow(0).getCell(cell.getColumnIndex()));
	}
	
	/**
	 * 获取单元格信息，并转化为String类型
	 * @param cell
	 * @return
	 */
	public static String getCellInfo(Cell cell) {
		if(cell != null){
			int cellType = cell.getCellType();  
	        if (cellType == HSSFCell.CELL_TYPE_NUMERIC || cellType == HSSFCell.CELL_TYPE_FORMULA) {  
	            if (HSSFDateUtil.isCellDateFormatted(cell)) {  
	                return cell.getDateCellValue().toString();  
	            } else {  
	                return String.valueOf(Math.round(cell.getNumericCellValue()));  
	            }  
	        } else if (cellType == HSSFCell.CELL_TYPE_STRING) {  
	            return cell.getStringCellValue();  
	        }  else { 
	        	return "";
	        } 
		}else{
			return "";
		}
	}
	
	/**
	 * 获取单元格信息，并转化为String类型
	 * @param cell
	 * @return
	 */
	public static String getCellInfoNumScale(Cell cell) {
		if(cell != null){
			int cellType = cell.getCellType();  
	        if (cellType == HSSFCell.CELL_TYPE_NUMERIC || cellType == HSSFCell.CELL_TYPE_FORMULA) {  
	            if (HSSFDateUtil.isCellDateFormatted(cell)) {  
	                return cell.getDateCellValue().toString();  
	            } else {  
	                return String.valueOf(new BigDecimal(cell.getNumericCellValue()).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());  
	            }  
	        } else if (cellType == HSSFCell.CELL_TYPE_STRING) {  
	            return cell.getStringCellValue();  
	        }  else { 
	        	return "";
	        } 
		}else{
			return "";
		}
	}
	
	/**
	 * 判断单元格是否为空
	 * @param cell
	 * @return
	 */
	public static boolean isNull(Cell cell){
		if(cell != null &&  StringUtils.isNotBlank(getCellInfo(cell))){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 判断是否为空，是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumberAndNull(String str){
		if(StringUtils.isNotBlank(str) && NumberUtils.isDigits(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * 下载网络图片，单元格内容处理
	 * @param folderName
	 * @param imgUrlAll
	 * @return
	 * @throws Exception
	 */
//	public static String downHttpImg(String folderName, String imgUrl)throws Exception { 
//		URL url;
//		String filePath = "public/images/";
//		if(folderName == null || folderName.equals("")){
//			filePath = filePath + "others/";
//		}
//		else{
//			filePath = filePath + folderName + "/";
//		}
//		
//		//创建文件夹
//		File saveDirFile = new File(filePath);
//		if (!saveDirFile.exists()) {
//			saveDirFile.mkdirs();
//		}
//		
//		//检查目录写权限
//		File downloadDir = new File(filePath);
//		if(!downloadDir.canWrite()){
//			LOGGERBIZ.warn("/ExcelUtils/downHttpImg " + downloadDir + " 目录没有写权限。");
//			return "";
//		}
//		
//		String[] fileExtension = imgUrl.split("\\.");
//		//连接浏览器，根据url下载图片
//		url = new URL(imgUrl);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
//		String fileName = sdf.format(new Date());
//		HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
//		//设置超时间为3秒  
//		conn.setConnectTimeout(3*1000);  
//		//防止屏蔽程序抓取而返回403错误  
//		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64)"); 
//		//得到输入流  
//		InputStream inputStream = conn.getInputStream();
//		if(inputStream == null){
//			LOGGERBIZ.warn("/ExcelUtils/downHttpImg " + url + " download failure");
//			return "";
//		}
//		
//		File file = new File(filePath + fileName + "." + fileExtension[fileExtension.length - 1]);
//		FileUtils.copyInputStreamToFile(inputStream, file);
//		LOGGERBIZ.info("info:" + url + " download success"); 
//		ServiceResult<String> uploadResult = FileCenterService.uploadFile(file,BizConstants.ProductDetailImgOnQiniu,true);
//		if(uploadResult.success){
//			file.delete();
//		}        
//		return uploadResult.obj;
//	}

	/** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }
    
//    public static void exportExcelOutPutStream(Workbook workbook, Response response, String name){
//		response.setHeader("Content-Disposition", "attachment; filename=" + name);
//		BufferedOutputStream buff = null;
//		try {
//			buff = new BufferedOutputStream(response.out);
//			workbook.write(buff);
//			buff.flush();
//		} catch (Exception e) {
//			LOGGERSYS.error("ExcelUtils.exportExcelOutPutStream", e);
//		} finally{
//			try {
//				buff.close();
//			} catch (Exception e) {
//				LOGGERSYS.error("Stream close failure", e);
//			}
//		}
//		
//	}
}
