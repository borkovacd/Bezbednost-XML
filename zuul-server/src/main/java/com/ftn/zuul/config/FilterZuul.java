package com.ftn.zuul.config;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class FilterZuul extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		  RequestContext ctx = RequestContext.getCurrentContext();
		  HttpServletRequest request = ctx.getRequest();
		  String header = request.getHeader("token");
		  
		  System.out.println("token stigao je:  " + header);
		  ctx.addZuulRequestHeader("token", header);
		  
		  return null;
		
		
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "da";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
