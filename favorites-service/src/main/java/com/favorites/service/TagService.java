package com.favorites.service;

import com.favorites.dto.TagDto;
import com.favorites.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author DELL
* @description 针对表【tag】的数据库操作Service
* @createDate 2023-04-18 16:43:28
*/
public interface TagService extends IService<Tag> {

    boolean insertTag(TagDto tag);

    boolean updateTag(TagDto tag);

    List<Tag> listAllTags();

    List<Tag> listTagId(List<String> tag);


}
