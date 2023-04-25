package com.favorites.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.commons.config.aop.annotation.WebLog;
import com.commons.vo.Result;
import com.favorites.dto.PageDto;
import com.favorites.dto.WebsiteDto;
import com.favorites.dto.WebsiteExcelDto;
import com.favorites.entity.Website;
import com.favorites.listener.ExcelListener;
import com.favorites.service.WebsiteService;
import com.favorites.util.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yyh
 * @date 2023/4/18 15:52
 * @description
 */
@Slf4j
@RestController
public class WebsiteController {

    @Resource
    private WebsiteService websiteService;


    @Resource
    private HttpServletResponse response;

    @RequestMapping("test")
    public String test(@RequestBody WebsiteDto websiteDto) {
        Website website = BeanUtil.copyProperties(websiteDto, Website.class);
        websiteService.save(website);
        return "test";
    }

    @WebLog(description = "分页查找网址")
    @GetMapping("/getByPage")
    public Result getByPage(PageDto pageDto) {
        List<Website> websites = websiteService.getByPage(pageDto.getPageNum(), pageDto.getPageSize());
        return Result.success(websites);
    }

    @WebLog(description = "根据标签查找网址")
    @GetMapping("/getByTags")
    public Result getByTags(@Validated PageDto pageDto,
                            @RequestParam("tags") List<String> tags) {
        List<Website> websites = websiteService.getByTags(pageDto.getPageNum(), pageDto.getPageSize(), tags);

        return Result.success(websites);
    }

    @WebLog(description = "导入收藏夹文件")
    @PostMapping("/importHtml")
    public Result importHtml(MultipartFile file) {
        String content = null;
        try {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        Document doc = Jsoup.parse(content);
        Elements links = doc.select("a[href]");
        List<WebsiteDto> collect = links.stream().map(link -> {
            WebsiteDto websiteDto = new WebsiteDto();
            websiteDto.setUrl(link.attr("href"));
            websiteDto.setTitle(link.text());
            return websiteDto;
        }).collect(Collectors.toList());

        websiteService.saveWebsites(collect);
        return Result.success("添加成功");
    }


    @WebLog(description = "更新网址信息")
    @PostMapping("/updateById")
    public Result updateWebsitesById(@RequestBody List<WebsiteDto> list) {
        boolean b = websiteService.updateWebsitesById(list);
        if (!b) {
            return Result.fail("更新失败");
        }
        return Result.success("更新成功");
    }

    @WebLog(description = "添加网址")
    @PostMapping("/addWebsite")
    public Result addWebsite(@RequestBody @Validated WebsiteDto websiteDto) {
        websiteService.insertSingleWebsite(websiteDto);
        return Result.success("成功添加");

    }


    @WebLog(description = "导出收藏夹文件")
    @GetMapping("/exportExcel")
    public void exportExcel() {
        List<Website> list = websiteService.listAll();
        List<WebsiteExcelDto> excelData = list.stream().map(p -> {
                    WebsiteExcelDto dto = new WebsiteExcelDto();
                    dto.setUrl(p.getUrl());
                    dto.setTitle(p.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());

        EasyExcelUtil.writeExcel(response, "收藏夹", excelData, WebsiteExcelDto.class);
    }

    @WebLog(description = "导入收藏夹文件")
    @PostMapping("/importExcel")
    public Result importExcel(MultipartFile file) {

        try {
            EasyExcelFactory.read(file.getInputStream(), WebsiteExcelDto.class, new ExcelListener(websiteService)).sheet().doRead();
        } catch (IOException e) {
            return Result.fail("读取excel失败:" + e.getMessage());
        }
        return Result.success("导入成功");
    }


    @WebLog(description = "关键字查询")
    @GetMapping("/getByKeywords")
    public Result getByKeywords(@RequestParam("keywords") String keywords){
        List<Website> list = websiteService.getByKeywords(keywords);
        return Result.success(list);
    }

}
