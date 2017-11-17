package com.aoji;

import com.aoji.config.JsonReturnHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * @author yangsaixing
 * @description  项目启动类
 * @date Created in 上午10:47 2017/11/10
 */
@SpringBootApplication
public class VisaApplication extends RequestMappingHandlerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(VisaApplication.class);
    }

    @Override
    public void setReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.setReturnValueHandlers(returnValueHandlers);
        returnValueHandlers.add(new JsonReturnHandler());
    }
}
