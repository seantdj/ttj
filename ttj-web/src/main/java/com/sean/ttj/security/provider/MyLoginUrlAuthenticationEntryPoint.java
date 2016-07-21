package com.sean.ttj.security.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tengdj on 2016/6/27.
 */
public class MyLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private static final String requesetWith = "X-Requested-With";
    private String timeOutAjaxForwarUrl;

    public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl,String timeOutAjaxForwarUrl) {
        super(loginFormUrl);
        this.timeOutAjaxForwarUrl = timeOutAjaxForwarUrl;
    }

    public MyLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        if (StringUtils.isNotBlank(timeOutAjaxForwarUrl) && request.getHeader(requesetWith) != null
                && request.getHeader(requesetWith).equalsIgnoreCase("XMLHttpRequest")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(timeOutAjaxForwarUrl);
            dispatcher.forward(request, response);
        }
        super.commence(request, response, authException);
    }
}
