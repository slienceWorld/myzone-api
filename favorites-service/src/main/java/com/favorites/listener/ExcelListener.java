package com.favorites.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.favorites.dto.WebsiteDto;
import com.favorites.dto.WebsiteExcelDto;
import com.favorites.service.WebsiteService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyh
 * @date 2023/4/22 17:02
 * @description
 */
@Slf4j
public class ExcelListener implements ReadListener<WebsiteExcelDto> {


    private WebsiteService websiteService;

    private List<WebsiteDto> dataList = new ArrayList<>();


    public ExcelListener(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    @Override
    public void invoke(WebsiteExcelDto websiteExcelDto, AnalysisContext analysisContext) {

        WebsiteDto websiteDto = new WebsiteDto();
        websiteDto.setUrl(websiteExcelDto.getUrl());
        websiteDto.setTitle(websiteExcelDto.getTitle());
        dataList.add(websiteDto);
        log.info("websiteDto:{}", websiteDto);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.warn("list:{}", dataList);
        websiteService.saveWebsites(dataList);
    }
}
