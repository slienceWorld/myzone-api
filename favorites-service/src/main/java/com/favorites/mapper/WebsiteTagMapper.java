package com.favorites.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.favorites.entity.WebsiteTag;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【website_tag】的数据库操作Mapper
 * @createDate 2023-04-18 16:43:28
 * @Entity com.favorites.entity.WebsiteTag
 */
public interface WebsiteTagMapper extends BaseMapper<WebsiteTag> {
    int insertWebsiteTags(Long wid, List<Long> tids);
}




