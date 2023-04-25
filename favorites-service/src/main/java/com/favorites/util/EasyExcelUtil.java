package com.favorites.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.favorites.listener.ExcelListener;
import com.favorites.mapper.WebsiteMapper;
import com.favorites.service.WebsiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yyh
 * @date 2023/4/22 16:45
 * @description
 */
@Slf4j

public class EasyExcelUtil {

    @Resource
    private WebsiteService websiteService;

    private static WebsiteService wService;


    public static <T> void readExcel(MultipartFile file, int sheetNo) throws IOException{
        ExcelReader reader = null;
        try {
            reader = EasyExcelFactory.read(file.getInputStream(), new ExcelListener(wService)).build();
            ReadSheet readSheet = EasyExcelFactory.readSheet(sheetNo).build();
            reader.read(readSheet);
        } finally {
            if (reader != null) {
                reader.finish();
            }
        }
    }


    public static <T> void writeExcel(HttpServletResponse response, String sheet, List<T> dataList, Class<T> clazz) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=website.xlsx");
        ExcelWriter writer = null;
        try {
            writer = EasyExcelFactory.write(response.getOutputStream(), clazz).excelType(ExcelTypeEnum.XLSX).build();
            WriteSheet writeSheet = EasyExcelFactory.writerSheet(sheet).build();
            writer.write(dataList, writeSheet);
        } catch (Exception e) {
            log.error("==========>>导入excel报错:{}", e.getMessage());
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }

    @PostConstruct
    public void init(){
        wService=websiteService;
    }
}
