package com.sean.ttj.handlebars.helpers;

/**
 * Created by tengdj on 2016/6/23.
 */
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;

import java.util.List;
import java.util.Map;

public class MyHandlebarsViewResolver extends HandlebarsViewResolver {

    public void setHelpList(final List<Object> helpers) {
        super.setHelpers(helpers);
    }

    public void setHelpMap(final Map<String, Helper<?>> helpers) {
        super.setHelpers(helpers);
    }


}
