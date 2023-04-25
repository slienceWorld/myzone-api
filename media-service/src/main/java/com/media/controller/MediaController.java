package com.media.controller;

import cn.hutool.core.util.StrUtil;
import com.commons.config.aop.annotation.WebLog;
import com.commons.vo.Result;
import com.media.config.VideoHttpRequestHandler;
import com.media.domain.Media;
import com.media.service.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.io.File.separator;

/**
 * @author yyh
 * @date 2023/4/25 22:11
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/media")
public class MediaController {


    @Resource
    private VideoHttpRequestHandler videoHttpRequestHandler;

    @Resource
    private MediaService mediaService;

    public static final String URL = "F:\\workspace\\myzone-api\\media";


    @WebLog(description = "测试")
    @RequestMapping("/test")
    public Result test() {
        return Result.success("test");
    }


    @GetMapping("/videoPlayer")
    public void getVideoResource(HttpServletRequest request, HttpServletResponse response) throws Exception {


        Path path = Paths.get(URL + separator + "111.mp4");

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


    @GetMapping("/musicPlayer")
    public void getMusicResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("audio/mpeg");
        response.setHeader("Content-Disposition", "inline; filename=music.mp3");

        File file = new File(URL + separator + "music.mp3");
        try (InputStream is = new FileInputStream(file)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        }
    }

    @PostMapping("/upload")
    public Result uploadMusic(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {

            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

            List<String> supportedExtensions = Arrays.asList("mp3", "wav", "flac");
            if (!supportedExtensions.contains(fileExtension)) {
                return Result.fail("上传文件格式错误");
            }

            String newFileName = UUID.randomUUID() + fileExtension;

            Path filePath = Paths.get(URL).toAbsolutePath().resolve(newFileName);
            log.info("path:{}", filePath);

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            return Result.success("文件上传成功");
        } else {
            return Result.fail("上传文件不存在");
        }
    }

    @PostMapping("/uploadVideo")
    public Result uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileName=originalFileName.substring(0,originalFileName.lastIndexOf('.'));
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

            List<String> supportedExtensions = Arrays.asList("mp4", "avi", "mkv");
            if (!supportedExtensions.contains(fileExtension)) {
                return Result.fail("文件格式有误");
            }

            String newFileName = UUID.randomUUID() + "." + fileExtension;
            Path filePath = Paths.get(URL).toAbsolutePath().resolve(newFileName);

            try (InputStream is = file.getInputStream()) {
                Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            Media media = new Media();
            media.setFileName(fileName);
            media.setType(1);
            media.setUrl(filePath.toString());
            mediaService.save(media);
            return Result.success("视频文件上传成功");
        } else {
            return Result.fail("上传文件不存在");
        }
    }

}


