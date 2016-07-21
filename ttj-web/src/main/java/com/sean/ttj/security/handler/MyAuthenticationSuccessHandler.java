package com.sean.ttj.security.handler;

import com.sean.ttj.security.SecurityContextUtil;
import com.sean.ttj.security.userdetail.MyUserDetails;
import com.sean.ttj.utils.CookieHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tengdj on 2016/6/27.
 */
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Log logger = LogFactory.getLog(getClass());
    private final String cookieKey = SecurityContextUtil.SPRING_SECURITY_COOKIE_KEY;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        MyUserDetails userDetails = SecurityContextUtil.getCurrentUserDetails();
        try {
            String securityValue = SecurityContextUtil.getSecurityCookieKey();
            CookieHelper.setAttribute(cookieKey, securityValue, response);
            userDetails.setUUID(securityValue);
            getRedirectStrategy().sendRedirect(request, response, getDefaultTargetUrl());
        } catch (Exception e) {
            logger.error(e);
        } finally {
            logger.warn(userDetails.getUsername() + " loginning...");
        }
        /*super.onAuthenticationSuccess(request, response, authentication);*/
    }
}
