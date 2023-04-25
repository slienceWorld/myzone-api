package com.favorites.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.favorites.dto.WebsiteDto;
import com.favorites.entity.Website;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【website】的数据库操作Mapper
 * @createDate 2023-04-18 16:43:28
 * @Entity com.favorites.entity.Website
 */

public interface WebsiteMapper extends BaseMapper<Website> {
    List<Website> getByPage(int pageNum, int pageSize);

    List<Website> getByTags(int pageNum, int pageSize, List<String> tags, int size);

    void saveWebsites(List<WebsiteDto> list);

    int saveWebsite(Website website);

    boolean updateBatchById(List<WebsiteDto> list);

    Website getByUrl(String url);

   List<Website> listAll();

    List<Website> listByKeywords(String keywords);
}




