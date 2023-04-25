package com.video.controller;

import cn.hutool.core.util.StrUtil;
import com.commons.config.aop.annotation.WebLog;
import com.commons.vo.Result;
import com.video.config.VideoHttpRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yyh
 * @date 2023/4/25 22:11
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/media")
public class MediaController {

    String url = "F:\\workspace\\myzone-api\\media-service\\src\\main\\resources\\static\\111.mp4";

    @Autowired
    private VideoHttpRequestHandler videoHttpRequestHandler;


    @WebLog(description = "测试")
    @RequestMapping("/test")
    public Result test() {
        return Result.success("test");
    }


    @GetMapping("/video")
    public void getVideoResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Path path = Paths.get(url);

        if (Files.exists(path)) {
            String mimeType = Files.probeContentType(path);
            if (!StrUtil.isEmpty(mimeType)) {
                response.setContentType(mimeType);
            }
            request.setAttribute(VideoHttpRequestHandler.ATTR_FILE, path);
            videoHttpRequestHandler.handleRequest(request, response);
        } else {
            log.error("文件不存在");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }




}
