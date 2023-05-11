package com.favorites.mapper;

import com.favorites.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author DELL
* @description 针对表【tag】的数据库操作Mapper
* @createDate 2023-04-18 16:43:28
* @Entity com.favorites.entity.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    List<Long>  listIdsByName(List<String> tags);

    int saveTags(List<String> tags);

}




