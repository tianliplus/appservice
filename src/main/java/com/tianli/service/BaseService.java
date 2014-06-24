package com.tianli.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseService {
	HttpServletRequest req;
	HttpServletResponse res;

	public BaseService(HttpServletRequest request, HttpServletResponse response) {
		this.req = request;
		this.res = response;
	}
}
