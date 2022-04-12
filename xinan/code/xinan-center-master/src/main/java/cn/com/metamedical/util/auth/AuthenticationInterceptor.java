package cn.com.metamedical.util.auth;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.metamedical.util.cache.JvmCacheTool;
import cn.com.metamedical.util.constant.CommonConstant;


public class AuthenticationInterceptor implements HandlerInterceptor {
	@Value("${yzmeta.debug:false}")
	private boolean debug;
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object) throws Exception {
		 String token = httpServletRequest.getHeader(CommonConstant.TOKEN_KEY);// 从 http头中取出 token，避免nginx的header转发带下划线的问题（需要在nginx里面配置）

		 // 如果不是映射到方法直接通过
		if (!(object instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) object;
		Method method = handlerMethod.getMethod();

		// 检查有没有需要用户权限的注解
		if (method.isAnnotationPresent(UserLoginToken.class)) {
			UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
			if (!debug && userLoginToken.required()) {
				// 执行认证
				if (token == null || !JvmCacheTool.isRightToken(token)) {
					httpServletResponse.sendError(403, "没有合法登录信息，请登录后使用。");
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) throws Exception {
	}
}