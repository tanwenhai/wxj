package com.mm.config;

import com.mm.config.thymeleaf.StaticResourcePrefixDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanwenhai@gusoftware.com
 */
@Configuration
public class ThymeleafConfiguration {
    @Bean
    public StaticResourcePrefixDialect staticResourcePrefixDialect() {
        return new StaticResourcePrefixDialect();
    }
}
