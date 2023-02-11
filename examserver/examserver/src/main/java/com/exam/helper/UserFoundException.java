package com.exam.helper;

public class UserFoundException extends Exception {

	public UserFoundException() {
		super("User with this username is already there in DB !! try with another suer");
	}

	public UserFoundException(String str) {
		super(str);
	}
}
