package com.favorites.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.favorites.dto.TagDto;
import com.favorites.entity.Tag;
import com.favorites.mapper.TagMapper;
import com.favorites.service.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author DELL
 * @description 针对表【tag】的数据库操作Service实现
 * @createDate 2023-04-18 16:43:28
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
        implements TagService {

    @Override
    public boolean insertTag(TagDto tagDto) {
        Tag tag = BeanUtil.copyProperties(tagDto, Tag.class);
        return this.save(tag);
    }

    @Override
    public boolean updateTag(TagDto tagDto) {
        Tag tag = BeanUtil.copyProperties(tagDto, Tag.class);
        this.updateById(tag);
        return false;
    }

    @Override
    public List<Tag> listAllTags() {
        return this.list();
    }

    @Override
    public List<Tag> listTagId(List<String> tag) {
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();
        lqw
                .select(Tag::getId)
                .eq(Tag::getName, tag);

        return this.list(lqw);
    }


}




