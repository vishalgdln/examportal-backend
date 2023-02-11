package com.exam.controller;

public class fds {

	public static void main(String[] args) {
		String str= "aaabbccdda";
		String finalResult ="";
		for(int i=0;i<str.length()-1;i++)
		{
			Integer count =0;
			for(int j=i+1;j<str.length()-2;j++)
			{
				if(str.charAt(i)==str.charAt(j))
				{
					count ++;
				}
				finalResult= finalResult+str.charAt(i)+count.toString(); 
			}
			
		}

	}

}
