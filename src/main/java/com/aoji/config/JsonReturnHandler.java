package com.aoji.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author yangsaixing
 * @description 处理JSON返回值
 * @date Created in 上午10:51 2017/11/17
 */
@Component
public class JsonReturnHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return  methodParameter.getMethodAnnotation(JSON.class)!=null;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        modelAndViewContainer.setRequestHandled(true);
        //获取注解并处理返回数据
        HttpServletResponse response=nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annotations = methodParameter.getMethodAnnotations();
        CustomJsonSerializer customJsonSerializer=new CustomJsonSerializer();
        for(Annotation annotation:annotations){
            if(annotation instanceof JSON){
                JSON json=(JSON)annotation;
                customJsonSerializer.filter(json.type(),json.include(),json.filter());
            }
        }
        //设置响应体类型并返回
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(customJsonSerializer.toJson(o));
    }
}
