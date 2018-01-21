package com.mm.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class UEditorConfiguration {
    @Bean
    public ServletRegistrationBean ueditorServlet() {
        UEditorServlet uEditorServlet = new UEditorServlet();

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(uEditorServlet);
        servletRegistrationBean.addUrlMappings("/ueditor/*");

        return servletRegistrationBean;
    }

    @Slf4j
    public static class UEditorServlet extends HttpServlet {
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
            doPost(request, response);
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type" , "text/html");
            String rootPath = SystemUtils.getUserDir().toString();

            String configStr = new ActionEnter(request, rootPath, new ClassPathResource("ueditor/config.json").getURL()).exec();
            request.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();

            out.write(configStr);
        }
    }
}
