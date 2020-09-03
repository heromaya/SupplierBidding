package com.laodyu.commons.utils;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @ClassName
 * @Description TODO
 * @Author Joe
 * @Date 2020/5/8
 * @Version 1.0
 **/
public class POIUtils {

    /***
     *
     * @param list 需要生成Excel表格的 list集合
     * @return
     */
    public static ResponseEntity<byte []>toExcel(List<?> list){
        //1.创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档
        workbook.createInformationProperties();

        //3. 获取并配置文档信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("");
        //文档管理员
        docInfo.setManager("laodyu");
        //设置公司信息
        docInfo.setCompany("laodyu");

        //4.获取文档的摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //设置标题
        summInfo.setTitle("信息表");
        //文档作者
        summInfo.setAuthor("laodyu");
        //文档备注
        summInfo.setComments("本文档由laodyu提供");

        //5.创建样式
         //创建标题行的样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        HSSFSheet sheet = workbook.createSheet();
        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        //第一行第一列
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headerStyle);
        HSSFCell cell1 = r0.createCell(1);
        HSSFCell cell2 = r0.createCell(2);
        HSSFCell cell3 = r0.createCell(3);
        HSSFCell cell4 = r0.createCell(4);
        HSSFCell cell5 = r0.createCell(5);
        HSSFCell cell6 = r0.createCell(6);
        HSSFCell cell7 = r0.createCell(7);
        HSSFCell cell8 = r0.createCell(8);
        HSSFCell cell9 = r0.createCell(9);
        return null;
    }
}
