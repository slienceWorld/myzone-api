package com.favorites.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.favorites.dto.WebsiteTagDto;
import com.favorites.entity.WebsiteTag;
import com.favorites.service.WebsiteTagService;
import com.favorites.mapper.WebsiteTagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author DELL
* @description 针对表【website_tag】的数据库操作Service实现
* @createDate 2023-04-18 16:43:28
*/
@Service
public class WebsiteTagServiceImpl extends ServiceImpl<WebsiteTagMapper, WebsiteTag>
    implements WebsiteTagService{
    @Resource
    private WebsiteTagMapper websiteTagMapper;

//    @Override
//    public void insertWebsiteTags(WebsiteTagDto websiteTagDto) {
//        websiteTagMapper.insertWebsiteTags();
//        return;
//    }
}




