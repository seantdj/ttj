package com.sean.ttj.security.provider;

import com.sean.ttj.model.UserModel;
import com.sean.ttj.security.userdetail.MyUserDetails;
import com.sean.ttj.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tengdj on 2016/6/27.
 */
@Service
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Resource
    private UserService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        /*String userName = userDetails.getUsername();
        String passWorld = userDetails.getPassword();*/

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        Log logger = LogFactory.getLog(MyAuthenticationProvider.class);
        String passWord = usernamePasswordAuthenticationToken.getCredentials().toString();
        String userName = usernamePasswordAuthenticationToken.getPrincipal().toString();
        logger.info("开始认证：用户名=" + userName);
        UserModel userModel = userService.queryByNamePwd(userName, passWord);
        if (userModel != null){
            return new MyUserDetails(userName,passWord, userModel);
        }else {
            logger.info("无效的用户名或密码");
            throw new BadCredentialsException("请检查用户名或密码");
        }
    }
}
