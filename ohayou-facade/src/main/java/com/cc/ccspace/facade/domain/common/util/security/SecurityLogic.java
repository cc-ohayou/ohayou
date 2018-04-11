package com.cc.ccspace.facade.domain.common.util.security;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityLogic {
	static Logger logger = LoggerFactory.getLogger(SecurityLogic.class);
	public static final String TOKEN_AES_KEY = "N13iJFLBjrlBA5aw";
	// 加密
    private static String encryptAES(String sSrc, String sKey) throws Exception {
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
 
        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }
 
    // 解密
    public static String decryptAES(String sSrc, String sKey) throws Exception {
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
	
	/**
	 * 将 Token 反解
	 * @param token
	 * @return
	 */
	/*public static TokenParam decryptToken(String token) {
		TokenParam tokenParam = new TokenParam();
		try {
			String res = decryptAES(token, Config.TOKEN_AES_KEY);
			String[] resParam = res.split("\\|");
			tokenParam.setUserId(ConvertUtil.toLong(resParam[0]));
			tokenParam.setToken(resParam[1]);
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}

		return tokenParam;
	}*/
	
	/**
	 * 加密 token
	 * @param userId
	 * @param token
	 * @return
	 */
	public static String encryptToken(Long userId, String token) {
		String toEncrypt = userId + "|" + token;
		try {
			return encryptAES(toEncrypt, TOKEN_AES_KEY);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	

	
	public static void main(String[] args) throws Exception{
/*		System.out.println(JSON.toJSONString(
				decryptToken("/cGMShsAOW+XHj//9VaNJEZjRnpeUgpHHW1u3E9po2rO1zy" +
						"SfR3mZALlEemJRro/0oxwV5qyyrcNphjVfuWfQQ==")));*/
	System.out.print(encryptToken(821286622543659532L,"93096040ac734c8d893d279f83aa0137"));

	}
}
