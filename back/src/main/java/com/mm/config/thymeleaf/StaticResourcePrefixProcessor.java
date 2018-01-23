package com.mm.config.thymeleaf;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractSingleAttributeModifierAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionExecutionContext;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.Objects;

import static org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor.ModificationType.PREPEND;

/**
 * @author tanwenhai@gusoftware.com
 */
@Slf4j
public class StaticResourcePrefixProcessor extends AbstractSingleAttributeModifierAttrProcessor {
    private static final int PRECEDENCE = 300;
    private final String host;

    protected StaticResourcePrefixProcessor(String attributeName, String host) {
        super(attributeName);
        Objects.requireNonNull(host);
        this.host = host;
    }

    @Override
    protected String getTargetAttributeName(Arguments arguments, Element element, String attributeName) {
        return attributeName.replaceAll("srp:", "");
    }

    @Override
    protected String getTargetAttributeValue(Arguments arguments, Element element, String attributeName) {
        final String attributeValue = element.getAttributeValue(attributeName);

        final Configuration configuration = arguments.getConfiguration();
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, attributeValue);

        final StandardExpressionExecutionContext expMode = StandardExpressionExecutionContext.NORMAL;

        final Object result = expression.execute(configuration, arguments, expMode);
        return (result == null? "" : host + result.toString());
    }

    @Override
    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return PREPEND;
    }

    @Override
    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
        return false;
    }

    @Override
    protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String attributeName) {
        return false;
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
}
