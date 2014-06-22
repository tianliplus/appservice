package com.tianli.result;



public class LoginResult extends BaseResult {
	/*
	 * 结果类型 0-异常 1-登录成功 2-登出成功 3-已有人
	 */
	public int actioncode = 0;
	// 只在换座的情况下有效
	public int oldseat;
}
