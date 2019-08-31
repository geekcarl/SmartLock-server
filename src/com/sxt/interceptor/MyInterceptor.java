package com.sxt.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class MyInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		
		
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,	ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		if (! uri.equals("/newinterlockwebservice/") && ! (uri.equals("/newinterlockwebservice/user.do") && request.getParameter("method").equals("login")))
		{
            Object t =  request.getSession().getAttribute("user_id");
            if (t == null)
            {
            	if (request.getHeader("RequestType") != null && request.getHeader("RequestType").equalsIgnoreCase("ajax"))
            	{
            		String resultMark = "{\"resultMark\" : -1, \"errMessage\" : \"会话超时，请重新登录确认身份\"}";
            		response.setContentType("application/json; charset=UTF-8");
            		response.getWriter().print(resultMark);
            	}
            	else
            	{
            		response.sendRedirect("/newinterlockwebservice/");            		
            	}
            }
            else
            {
            	System.out.println(t);
            }
		}
		return true;  //����ִ��action
	}

}

