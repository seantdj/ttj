package com.sean.ttj.security;

import com.sean.ttj.security.userdetail.MyUserDetails;
import com.sean.ttj.utils.AES;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

/**
 * Created by tengdj on 2016/6/27.
 */
public class SecurityContextUtil {
    public static final String SPRING_SECURITY_COOKIE_KEY = "u_info";
    /**
     * 获取认证当前用户
     */
    public static MyUserDetails getCurrentUserDetails() {
        try{
            Object user = SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            return (user!=null && user instanceof MyUserDetails) ? (MyUserDetails) user : null;
        }catch (Exception e) {
            return null;
        }
    }

    public static MyUserDetails getCurrentUserDetails(SecurityContext context) {
        try{
            Object user = context.getAuthentication().getPrincipal();
            return (user!=null && user instanceof MyUserDetails) ? (MyUserDetails) user : null;
        }catch (Exception e) {
            return null;
        }
    }

    public static String getSecurityCookieKey(){
        return UUID.randomUUID().toString();
    }

    public static String getSecurityCookieKey(String name,String ip){
        return getSecurityCookieKey(name,ip,UUID.randomUUID().toString());
    }

    public static String getSecurityCookieKey(String name,String ip,String uuid){
        return AES.Encrypt(name + "__" +uuid+"__"+ System.currentTimeMillis() + "__" + ip);
    }
}
