package com.xlr.demo.utils;

import java.security.MessageDigest;

@SuppressWarnings("all")
public class MD5 {

	public static String create(String s) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			byte[] byteArray = s.getBytes("ISO-8859-1");
			byte[] md5Bytes = md5.digest(byteArray);

			StringBuffer hexValue = new StringBuffer();

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}

			return hexValue.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	public static void main(String[] args) {
		String str = "gisadmin2014";
		System.out.println(">>>>>>===="+create(str));
	}
}
