package com.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.media.domain.Media;
import com.media.service.MediaService;
import com.media.mapper.MediaMapper;
import org.springframework.stereotype.Service;

/**
* @author DELL
* @description 针对表【media】的数据库操作Service实现
* @createDate 2023-04-27 17:14:48
*/
@Service
public class MediaServiceImpl extends ServiceImpl<MediaMapper, Media>
    implements MediaService{

}




