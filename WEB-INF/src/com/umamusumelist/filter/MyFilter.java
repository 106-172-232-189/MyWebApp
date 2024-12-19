package com.umamusumelist.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * アクセス制御を行うフィルター
 *
 * @author Umamusumelist.com
 * @version 5.2
 */
public final class MyFilter implements Filter {

	@Override
	public void init(FilterConfig config) {

	}

	/**
	 * ".jsp"拡張子へのアクセスがあった場合、404エラーページを表示
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (httpRequest.getRequestURI().endsWith(".jsp")) {
			httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
