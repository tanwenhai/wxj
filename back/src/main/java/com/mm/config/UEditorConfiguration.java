package com.mm.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class UEditorConfiguration {
    @Autowired
    Environment env;

    @Bean
    public ServletRegistrationBean ueditorServlet() {
        UEditorServlet uEditorServlet = new UEditorServlet();
        String activeProfile = env.getActiveProfiles()[0];

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(uEditorServlet);
        servletRegistrationBean.addUrlMappings("/ueditor/*");
        servletRegistrationBean.addInitParameter("config", "config-" + activeProfile + ".json");

        return servletRegistrationBean;
    }

    @Slf4j
    public static class UEditorServlet extends HttpServlet {
        @Override
        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
            doPost(request, response);
        }

        @Override
        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String fileName = getServletConfig().getInitParameter("config");
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type" , "text/html");
            String rootPath = SystemUtils.getUserDir().toString();

            String configStr = new ActionEnter(request, rootPath, new ClassPathResource("ueditor/" + fileName).getURL()).exec();
            request.setCharacterEncoding("utf-8");
            PrintWriter out = response.getWriter();

            out.write(configStr);
        }
    }
}
