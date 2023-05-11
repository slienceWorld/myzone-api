package com.favorites.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.commons.exception.DuplicateDataException;
import com.commons.util.BeanUtil;
import com.favorites.dto.WebsiteDto;
import com.favorites.entity.Tag;
import com.favorites.entity.Website;
import com.favorites.entity.WebsiteTag;
import com.favorites.mapper.TagMapper;
import com.favorites.mapper.WebsiteMapper;
import com.favorites.mapper.WebsiteTagMapper;
import com.favorites.service.TagService;
import com.favorites.service.WebsiteService;
import com.favorites.vo.WebsiteVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DELL
 * @description 针对表【website】的数据库操作Service实现
 * @createDate 2023-04-18 16:43:28
 */
@Slf4j
@Service
public class WebsiteServiceImpl extends ServiceImpl<WebsiteMapper, Website>
        implements WebsiteService {

    @Resource
    private WebsiteMapper websiteMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private TagService tagService;

    @Resource
    private WebsiteTagMapper websiteTagMapper;

    @Resource
    private PlatformTransactionManager transactionManager;


    @Override
    public List<Website> getByPage(int pageBegin, int pageSize) {
        return websiteMapper.getByPage(pageBegin, pageSize);
    }

    @Override
    public List<Website> getByTags(int pageNum, int pageSize, List<String> tags) {
        return websiteMapper.getByTags(pageNum * pageSize, pageSize, tags, tags.size());
    }

    @Override
    @Transactional
    public void saveWebsites(List<WebsiteDto> list) {
        websiteMapper.saveWebsites(list);

        List<Tag> tags = tagService.list();

        for (WebsiteDto w : list) {
            List<Tag> webTags = tags.stream().filter(tag -> w.getTitle().contains(tag.getName())).collect(Collectors.toList());
            for (Tag tag : webTags) {
                websiteTagMapper.insert(new WebsiteTag(w.getId(), tag.getId()));
            }
        }
    }

    @Override
    public boolean updateWebsitesById(List<WebsiteDto> list) {
        return websiteMapper.updateBatchById(list);

    }

    @Override
    public boolean insertSingleWebsite(WebsiteDto websiteDto) {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        Website website = new Website();
        BeanUtils.copyProperties(websiteDto, website);
        try {
            Website byUrl = websiteMapper.getByUrl(website.getUrl());
            if (ObjectUtil.isNotNull(byUrl)) {
                throw new DuplicateDataException("数据重复");
            }
            websiteMapper.saveWebsite(website);
            List<String> tags = websiteDto.getTags();
            if (CollectionUtil.isEmpty(tags)) {
                return true;
            }
            List<Long> tids = tagMapper.listIdsByName(tags);
            websiteTagMapper.insertWebsiteTags(website.getId(), tids);
        } catch (BuilderException e) {
            log.error("插入失败" + e.getMessage());
            transactionManager.rollback(transaction);
        }
        transactionManager.commit(transaction);
        return true;
    }

    @Override
    public List<Website> listAll() {
        return websiteMapper.listAll();
    }

    @Override
    public List<Website> getByKeywords(String keywords) {
        return websiteMapper.listByKeywords(keywords);
    }


    @Override
    @SneakyThrows
    public List<WebsiteVo> getByTypeAndPage(String type, int pageNum, int pageSize) {
        List<Website> websites = websiteMapper.getByTypeAndPage(type, pageNum * pageSize, pageSize);
        return BeanUtil.convert(websites, WebsiteVo.class);
    }

}




