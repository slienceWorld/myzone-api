package com.favorites.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.favorites.dto.WebsiteDto;
import com.favorites.entity.Website;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【website】的数据库操作Service
 * @createDate 2023-04-18 16:43:28
 */
public interface WebsiteService extends IService<Website> {

    List<Website> getByPage(int pageNum, int pageSize);

    List<Website> getByTags(int pageNum, int pageSize, List<String> tags);

    void saveWebsites(List<WebsiteDto> list);

    boolean updateWebsitesById(List<WebsiteDto> list);

    boolean insertSingleWebsite(WebsiteDto websiteDto);

    List<Website> listAll();

    List<Website> getByKeywords(String keywords);
}
