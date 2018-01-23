package com.mm.config.thymeleaf;

import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author tanwenhai@gusoftware.com
 */
public class StaticResourcePrefixDialect extends AbstractDialect {
    private static final String PREFIX = "srp";

    @Value("${static.resource.host:''}")
    private String host;

    @Override
    public Set<IProcessor> getProcessors() {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<>();
        processors.add(new StaticResourcePrefixProcessor("href", host));
        processors.add(new StaticResourcePrefixProcessor("src", host));
        return processors;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
