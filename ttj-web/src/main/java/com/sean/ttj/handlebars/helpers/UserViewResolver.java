package com.sean.ttj.handlebars.helpers;


import org.springframework.core.Ordered;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Created by tengdj on 2016/6/23.
 */
public class UserViewResolver extends WebApplicationObjectSupport implements ViewResolver, Ordered {
    private int order = Ordered.HIGHEST_PRECEDENCE;
    private final ViewResolver delegate;

    protected UserViewResolver(ViewResolver delegate) {
        this.delegate = delegate;
    }

    public int getOrder() {
        return order;
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return delegate.resolveViewName(viewName, locale);
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
