package com.pflm.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/** 
 * Md5加密
 */
public class Md5 {
	public static String encode(String password) {
		String algorithm = "MD5";
		MessageDigest instance = null;
		try {
			instance = MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] digest = instance.digest(password.getBytes());
		char[] encodeHex = Hex.encodeHex(digest);
		return new String(encodeHex);
	}
	
	public static void main(String[] args) {
		System.out.println(Md5.encode("admin"));
	}
	
}