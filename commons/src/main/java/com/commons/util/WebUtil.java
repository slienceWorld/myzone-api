package com.commons.util;

import com.commons.vo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yyh
 * @date 2023/4/5 16:34
 * @description
 */
public class WebUtil {

    public static String objectToJson(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }

    public static void returnResult(HttpServletResponse response, Result result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(WebUtil.objectToJson(result));
    }
}
