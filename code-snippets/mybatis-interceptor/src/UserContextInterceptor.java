package com.bkrwin.elevator.datarealm;

import com.bkrwin.elevator.infra.ErrorCode;
import com.bkrwin.ufast.dto.UserDetailDTO;
import com.bkrwin.ufast.feign.AuthClient;
import com.bkrwin.ufast.infra.infra.ActionResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wanghao
 * @Description 用户信息设置拦截器
 * @date 2019-04-22 15:51
 */
public class UserContextInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthClient authClient;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 设置用户信息到线程变量中
        String userId = request.getHeader("x-user-id");
        if (StringUtils.isBlank(userId)) {
            return true;
        }

        ActionResult<UserDetailDTO> ret = null;
        try {
            ret = authClient.getUserDetail(userId);
            if (ret == null || ret.getCode() != ErrorCode.Success.getCode()) {
                UserContextHolder.add(null);
            } else {
                UserContextHolder.add(ret.getValue());
            }
        } catch (Exception e) {
            logger.error("根据userId获取用户信息异常", e);
            UserContextHolder.add(null);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.remove();
    }
}
