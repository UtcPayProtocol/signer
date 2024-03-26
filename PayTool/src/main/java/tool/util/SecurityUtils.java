package tool.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;

public abstract class SecurityUtils{
	private static final String CIPHER_AES_NAME = "AES";
	private static final String CIPHER_AES_MODE = "AES/CBC/PKCS5Padding";
	private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);
	/**
	 * 使用AES算法做数据解密处理。
	 * 
	 * @param key
	 *            解密Key（16字节）
	 * @param iv
	 *            解密初始化向量（16字节）
	 * @param encData
	 *            拟解密的数据（UTF8）
	 * @return 原始数据
	 */
	public static byte[] aesDecryption(byte[] key, byte[] iv, byte[] encData) {
		try {
			Cipher cipher;
			cipher = Cipher.getInstance(CIPHER_AES_MODE);
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, CIPHER_AES_NAME), new IvParameterSpec(iv));
			byte[] orgData = cipher.doFinal(encData);
			return orgData;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}

	/**
	 * 使用AES算法做数据加密处理。
	 * 
	 * @param key
	 *            加密Key
	 * @param iv
	 *            加密初始向量
	 * @param orgData
	 *            拟加密数据
	 * @return 加密数据
	 */
	public static byte[] aesEncryption(byte[] key, byte[] iv, byte[] orgData) {
		try {
			Cipher cipher;
			cipher = Cipher.getInstance(CIPHER_AES_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, CIPHER_AES_NAME), new IvParameterSpec(iv));
			byte[] encData = cipher.doFinal(orgData);
			return encData;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		}
	}
	public static String decodeAESBySalt(String password, String salt) throws Exception {
		return new String(SecurityUtils.aesDecryption(
				salt.substring(0, 16).getBytes(StandardCharsets.UTF_8), 
				salt.substring(16, 32).getBytes(StandardCharsets.UTF_8), 
				Hex.decodeHex(password.toCharArray())),StandardCharsets.UTF_8);
	}
	public static String encodeAESBySalt(String password, String salt) {
		return new String(Hex.encodeHex(SecurityUtils.aesEncryption(
				salt.substring(0, 16).getBytes(StandardCharsets.UTF_8), 
				salt.substring(16, 32).getBytes(StandardCharsets.UTF_8), 
				password.getBytes(StandardCharsets.UTF_8))));
	}
	/**
	 * @Title: decode
	 * @Description: 对应前台解密
	 * @param paramString
	 * @return
	 */
	public static String decode(String paramString) {
		if (paramString == null) {
			return "";
		}
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramString.length(); ++i) {
			char c = paramString.charAt(i);
			String str;
			switch (c) {
			case '~':
				str = paramString.substring(i + 1, i + 3);
				localStringBuffer.append((char) Integer.parseInt(str, 16));
				i += 2;
				break;
			case '^':
				str = paramString.substring(i + 1, i + 5);
				localStringBuffer.append((char) Integer.parseInt(str, 16));
				i += 4;
				break;
			default:
				localStringBuffer.append(c);
			}
		}
		return localStringBuffer.toString();
	}

	/**
	 * @Title: encode
	 * @Description: 对应前台加密
	 * @param paramString
	 * @return
	 */
	public static String encode(String paramString) {
		if (paramString == null) {
			return "";
		}
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0; i < paramString.length(); i++) {
			int j = paramString.charAt(i);
			String str;

			str = Integer.toString(j, 16);
			for (int k = str.length(); k < 4; k++)
				str = "0" + str;
			localStringBuffer.append("^" + str);

		}
		return localStringBuffer.toString();
	}
	/**
	 * 对称加密秘钥解密
	 * @param encodeKey
	 * @param rsaKey
	 * @return
	 */
	public static String getDecodeKeyByRsaKey(String encodeKey, String rsaKey) {
		return encodeKey;
	}
	/**
	 * 对称加密秘钥加密
	 * @param decodeKey
	 * @param rsaKey
	 * @return
	 */
	public static String getEncodeKeyByRsaKey(String decodeKey, String rsaKey) {
		return decodeKey;
	}
	/**
	 * 对参数进行解密
	 * @param <T>
	 * @param encodeParam
	 * @param decodeKey
	 * @return 
	 * @throws Exception
	 */
	public static <T> T getDecodeParamByDecodeKey(String encodeParam, String decodeKey,Class<T> t) throws Exception{
		String decodeParam=new String(Base64.getDecoder().decode(encodeParam),StandardCharsets.UTF_8);
		T param =ObjectJsonHelper.deserialize(decodeParam,t);
		return param;
	}
	/**
	 * 对参数进行加密
	 * @param decodeParam
	 * @param encodeKey
	 * @return
	 * @throws Exception
	 */
	public static String getEncodeParamByEecodeKey(Object decodeParam, String encodeKey) throws Exception{
		String serializeResult = ObjectJsonHelper.serialize(decodeParam);
		return new String(Base64.getEncoder().encode(serializeResult.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
	}
//	public static void main(String[] args) throws Exception {
//		int i=0;
//		while(i<10) {
//			i++;
//			String id=RandomStringUtils.randomAlphanumeric(16);
//			String key=RandomStringUtils.randomAlphanumeric(16);
//			String salt=id+key;
//			System.out.println(salt);
//			String encodeAESBySalt = SecurityUtils.encodeAESBySalt(RandomStringUtils.randomNumeric(11), salt);
//			System.out.println(encodeAESBySalt.length());
//			System.out.println(encodeAESBySalt);
//			System.out.println(SecurityUtils.decodeAESBySalt(encodeAESBySalt, salt));
//			System.out.println();
//		}
//	}


	private static String encodingCharset = "UTF-8";
	
	
	public static String hmacSign(String aValue, String aKey) {
		return hmacSign(aValue, aKey , "MD5");
	}
	
	/**
	 * 生成签名消息
	 * @param aValue  要签名的字符串
	 * @param aKey  签名密钥
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey,String jiami) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(jiami);//"MD5"
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}
	
	/***
	 * 对应python里面的hmac.new(API_SECRET, msg=message, digestmod=hashlib.sha256).hexdigest().upper() 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String hamacSha256(String key , String value){
		String result = null;
		  byte[] keyBytes = key.getBytes();
		  SecretKeySpec localMac = new SecretKeySpec(keyBytes, "HmacSHA256");
		  try {
		    Mac mac = Mac.getInstance("HmacSHA256");
		    mac.init(localMac);
		    byte[] arrayOfByte = mac.doFinal(value.getBytes());
		    BigInteger localBigInteger = new BigInteger(1,
		        arrayOfByte);
		    result = String.format("%0" + (arrayOfByte.length << 1) + "x",
		        new Object[] { localBigInteger });
		    
		  } catch (InvalidKeyException e) {
		    e.printStackTrace();
		  } catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		  } catch (IllegalStateException e) {
		    e.printStackTrace();
		  }
		  
		  return result;
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}

	/**
	 * SHA加密
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue , String algorithm) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}
	
	/***
	 * sha-1散列加密
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
			return digest(aValue, "SHA");
	}
	/***
	 * sha-256散列加密
	 * @param aValue
	 * @return
	 */
	public static String digestSha256(String aValue) {
		return digest(aValue, "SHA-256");
	}
}