package com.mm.config;

import com.mm.common.APIResult;
import com.mm.common.exception.BusinessException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.File;
import java.nio.file.Files;
import java.util.Locale;

import static com.mm.common.APIResult.withFail;
import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;
import static org.springframework.util.ResourceUtils.FILE_URL_PREFIX;

/**
 * Spring WebMvc 配置
 * @author tanwenhai@gusoftware.com
 */
@Configuration
@EnableWebMvc
@ControllerAdvice(annotations = RestController.class)
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${spring.mvc.locale:zh_CN}")
    private String locale;

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public APIResult exceptionHandler(BusinessException bs) {
        return withFail(bs.getErrorCode()).build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 添加静态资源处理 file:C:/Users/谭文海/Desktop/wangxja/upload/
        String uploadDir = FILE_URL_PREFIX + SystemUtils.getUserDir() + "/upload/";
        registry.addResourceHandler("/upload/**").addResourceLocations(uploadDir);
        registry.addResourceHandler("/static/**").addResourceLocations(CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
//        new Locale("zh", "cn");
        slr.setDefaultLocale(new Locale(locale));
        return slr;
    }

}
