package com.tom.basic.service;

public class BizException extends Exception{
	
	private static final long serialVersionUID = -7180394697896457815L;
	
	public String errorMsg;
	
	public BizException(){}
	
	public BizException(String errorMsg){
		this.errorMsg = errorMsg;
	}

}
