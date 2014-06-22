package com.tianli.result;

public class LoginResult extends BaseResult {

	// Action type:
	// 0-innormal 1-login success
	// 2-change success 3-logout success
	// 4-already someone
	public int actioncode = 0;
	// Only use when change seat
	public int oldseat;

	public int seatid;
}
