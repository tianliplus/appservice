package com.tianli.result;




public class LoginResult extends BaseResult {
	/*
	 * Action type: 0-innormal 1-login success 2-logout success 3-already
	 * someone
	 */
	public int actioncode = 0;
	// Only use when change seat
	public int oldseat;

	public int seatid;
}
