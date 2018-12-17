package com.pflm.common.utils;

import java.security.Key;  

import javax.crypto.Cipher;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESedeKeySpec;  
import javax.crypto.spec.IvParameterSpec;  


import com.alibaba.fastjson.JSONObject;

 
/** 
* 3DES加密工具类 
*/  
public class DESUtil {  

	// 加解密统一使用的编码方式  
	private final static String encoding = "utf-8";  
	
	public static void main(String[] args) {
		String secretKey = "test@telefen.com~!@#$250";  
		String iv = "88888888"; 
		JSONObject param=new JSONObject();
  	    param.put("num", "10");
  	    param.put("content", "修复故障");
	    String data = param.toString();
	    
		// 加密数据
		try {
			System.out.println(encode(data, secretKey, iv));
			System.out.println(decode(encode(data, secretKey, iv), secretKey, iv));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
 
   /** 
    * 3DES加密 
    *  
    * @param plainText 普通文本 
    * @return 
    * @throws Exception  
    */  
   public static String encode(String plainText,String secretKey,String iv) throws Exception {  
       Key deskey = null;  
       DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
       SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
       deskey = keyfactory.generateSecret(spec);  
 
       Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
       IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
       cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);  
       byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));  
       String encode = Base64Util.encode(encryptData); 
       encode = encode.replace("+", "%2B").replace("/", "%2F");//为什么这样做：https://www.cnblogs.com/jiunadianshi/articles/2353968.html
       return encode;  
   }  
 
   /** 
    * 3DES解密 
    *  
    * @param encryptText 加密文本 
    * @return 
    * @throws Exception 
    */  
   public static String decode(String encryptText,String secretKey,String iv) throws Exception {  
       Key deskey = null;  
       DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());  
       SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");  
       deskey = keyfactory.generateSecret(spec);  
       Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");  
       IvParameterSpec ips = new IvParameterSpec(iv.getBytes());  
       cipher.init(Cipher.DECRYPT_MODE, deskey, ips);  
 
       encryptText = encryptText.replace("%2B","+").replace("%2F","/");//为什么这样做：https://www.cnblogs.com/jiunadianshi/articles/2353968.html
       byte[] decryptData = cipher.doFinal(Base64Util.decode(encryptText));  
 
       return new String(decryptData, encoding);  
   }  
}