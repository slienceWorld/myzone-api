package com.favorites.controller;

import com.commons.config.aop.annotation.WebLog;
import com.commons.vo.Result;
import com.favorites.dto.TagDto;
import com.favorites.entity.Tag;
import com.favorites.service.TagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yyh
 * @date 2023/4/20 16:17
 * @description
 */
@RestController
public class TagController {
    @Resource
    private TagService tagService;

    @WebLog(description = "添加标签")
    @PostMapping("addTag")
    public Result addTag(TagDto tag) {
        boolean b=tagService.insertTag(tag);
        if (!b){
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }


    @WebLog(description = "修改标签")
    @PostMapping("updateTag")
    public Result modifyTag(TagDto tag) {
        boolean b=tagService.updateTag(tag);
        if (!b){
            return Result.fail("添加失败");
        }
        return Result.success("添加成功");
    }

    @WebLog(description = "获取所有标签")
    @PostMapping("getAllTags")
    public Result getAllTags() {
        List<Tag> tagList = tagService.listAllTags();
        return Result.success(tagList);
    }

}
