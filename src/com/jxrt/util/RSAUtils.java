package com.jxrt.util;


import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


/**
 * RSA非对称加密辅助类
 *
 * @author spirit
 * @date 2017年12月5日 下午4:34:11
 */

public class RSAUtils {


    /**
     * 指定加密算法为DESede
     */

    private static String ALGORITHM = "RSA/ECB/PKCS1Padding";//MD5withRSA///RSA/ECB/PKCS1Padding

    /**
     * 指定key的大小(64的整数倍,最小512位)
     */

    private static int KEYSIZE = 1024;

    /* RSA最大加密明文大小 */

    private static final int MAX_ENCRYPT_BLOCK = 117;
    /* RSA最大解密密文大小 */


    private static final int MAX_DECRYPT_BLOCK = 128;

    /* 公钥模量 */

    public static String publicModulus = null;

    /* 公钥指数 */

    public static String publicExponent = null;

    /* 私钥模量 */

    public static String privateModulus = null;

    /* 私钥指数 */

    public static String privateExponent = null;

    private static KeyFactory keyFactory = null;


    static {

        try {

            keyFactory = KeyFactory.getInstance("RSA");

        } catch (NoSuchAlgorithmException ex) {

            System.out.println(ex.getMessage());

        }

    }


    public RSAUtils() {

        try {

            generateKeyPairString(KEYSIZE);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public RSAUtils(int keySize) {

        try {

            generateKeyPairString(keySize);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    /**
     * 生成密钥对字符串
     */

    private void generateKeyPairString(int keySize) throws Exception {

        /** RSA算法要求有一个可信任的随机数源 */

        SecureRandom sr = new SecureRandom();

        /** 为RSA算法创建一个KeyPairGenerator对象 */

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */

        kpg.initialize(keySize, sr);

        /** 生成密匙对 */

        KeyPair kp = kpg.generateKeyPair();

        /** 得到公钥 */

        Key publicKey = kp.getPublic();

        /** 得到私钥 */

        Key privateKey = kp.getPrivate();

        /** 用字符串将生成的密钥写入文件 */


        String algorithm = publicKey.getAlgorithm(); // 获取算法

        KeyFactory keyFact = KeyFactory.getInstance(algorithm);

        BigInteger prime = null;

        BigInteger exponent = null;


        RSAPublicKeySpec keySpec = (RSAPublicKeySpec) keyFact.getKeySpec(publicKey, RSAPublicKeySpec.class);

        prime = keySpec.getModulus();

        exponent = keySpec.getPublicExponent();

        RSAUtils.publicModulus = HexUtil.bytes2Hex(prime.toByteArray());

        RSAUtils.publicExponent = HexUtil.bytes2Hex(exponent.toByteArray());


        RSAPrivateCrtKeySpec privateKeySpec = (RSAPrivateCrtKeySpec) keyFact.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);

        BigInteger privateModulus = privateKeySpec.getModulus();

        BigInteger privateExponent = privateKeySpec.getPrivateExponent();

        RSAUtils.privateModulus = HexUtil.bytes2Hex(privateModulus.toByteArray());

        RSAUtils.privateExponent = HexUtil.bytes2Hex(privateExponent.toByteArray());

    }


    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     *
     * @param hexModulus        系数。
     * @param hexPublicExponent 专用指数。
     * @return RSA专用公钥对象。
     */

    public static RSAPublicKey getRSAPublicKey(String hexModulus, String hexPublicExponent) {

        if (isBlank(hexModulus) || isBlank(hexPublicExponent)) {

            System.out.println("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");

            return null;

        }

        byte[] modulus = null;

        byte[] publicExponent = null;

        try {

            modulus = HexUtil.hex2Bytes(hexModulus);

            publicExponent = HexUtil.hex2Bytes(hexPublicExponent);

        } catch (Exception ex) {

            System.out.println("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");

            ex.printStackTrace();

        }

        if (modulus != null && publicExponent != null) {

            return generateRSAPublicKey(modulus, publicExponent);

        }

        return null;

    }


    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     *
     * @param modulus        系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */

    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {

        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),

                new BigInteger(publicExponent));

        try {

            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);

        } catch (InvalidKeySpecException ex) {

            System.out.println("RSAPublicKeySpec is unavailable.");

            ex.printStackTrace();

        } catch (NullPointerException ex) {

            System.out.println("RSAUtil#KEY_FACTORY is null, can not generate KeyFactory instance.");

            ex.printStackTrace();

        }

        return null;

    }


    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
     *
     * @param hexModulus         系数。
     * @param hexPrivateExponent 专用指数。
     * @return RSA专用私钥对象。
     */

    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {

        if (isBlank(hexModulus) || isBlank(hexPrivateExponent)) {

            System.out.println("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");

            return null;

        }

        byte[] modulus = null;

        byte[] privateExponent = null;

        try {

            modulus = HexUtil.hex2Bytes(hexModulus);

            privateExponent = HexUtil.hex2Bytes(hexPrivateExponent);

        } catch (Exception ex) {

            System.out.println("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");

            ex.printStackTrace();

        }

        if (modulus != null && privateExponent != null) {

            return generateRSAPrivateKey(modulus, privateExponent);

        }

        return null;

    }


    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     *
     * @param modulus         系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */

    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {

        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),

                new BigInteger(privateExponent));

        try {

            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);

        } catch (InvalidKeySpecException ex) {

            System.out.println("RSAPrivateKeySpec is unavailable.");

            ex.printStackTrace();

        } catch (NullPointerException ex) {

            System.out.println("RSAUtil#KEY_FACTORY is null, can not generate KeyFactory instance.");

            ex.printStackTrace();

        }

        return null;

    }


    /**
     * 使用给定的公钥加密给定的字符串。
     *
     * @param key       给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */

    public static String encryptString(Key key, String plaintext) {

        if (key == null || plaintext == null) {

            return null;

        }

        byte[] data = plaintext.getBytes();

        try {

            byte[] en_data = encrypt(key, data);

            return new String(Base64.encodeBase64String(en_data));

//			return new String(HexUtil.bytes2Hex(en_data));

        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return null;

    }


    /**
     * 使用指定的公钥加密数据。
     *
     * @param key  给定的公钥。
     * @param data 要加密的数据。
     * @return 加密后的数据。
     */


    public static byte[] encrypt(Key key, byte[] data) throws Exception {

        Cipher ci = Cipher.getInstance(ALGORITHM);

        ci.init(Cipher.ENCRYPT_MODE, key);

        int inputLen = data.length;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offSet = 0;

        byte[] cache;

        int i = 0;

        // 对数据分段加密

        while (inputLen - offSet > 0) {

            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {

                cache = ci.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);

            } else {

                cache = ci.doFinal(data, offSet, inputLen - offSet);

            }

            out.write(cache, 0, cache.length);

            i++;

            offSet = i * MAX_ENCRYPT_BLOCK;

        }

        byte[] encryptedData = out.toByteArray();

        out.close();

        return encryptedData;

    }


    /**
     * 使用给定的公钥解密给定的字符串。
     *
     * @param key         给定的公钥
     * @param encrypttext 密文
     * @return 原文字符串。
     */

    public static String decryptString(Key key, String encrypttext) {

        if (key == null || isBlank(encrypttext)) {

            return null;

        }

        try {

            byte[] en_data = Base64.decodeBase64(encrypttext);


            byte[] data = decrypt(key, en_data);

            return new String(data);

        } catch (Exception ex) {

            ex.printStackTrace();

            System.out.println(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));

        }

        return null;

    }


    /**
     * 使用指定的公钥解密数据。
     *
     * @param key  指定的公钥
     * @param data 要解密的数据
     * @return 原数据
     * @throws Exception
     */

    public static byte[] decrypt(Key key, byte[] data) throws Exception {

        Cipher ci = Cipher.getInstance(ALGORITHM);

        ci.init(Cipher.DECRYPT_MODE, key);

//		return ci.doFinal(data);

        int inputLen = data.length;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int offSet = 0;

        byte[] cache;

        int i = 0;

        // 对数据分段解密

        while (inputLen - offSet > 0) {

            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {

                cache = ci.doFinal(data, offSet, MAX_DECRYPT_BLOCK);

            } else {

                cache = ci.doFinal(data, offSet, inputLen - offSet);

            }

            out.write(cache, 0, cache.length);

            i++;

            offSet = i * MAX_DECRYPT_BLOCK;

        }

        byte[] decryptedData = out.toByteArray();

        out.close();

        return decryptedData;

    }

    public static String switchToPublicKey(String publicModulus, String publicExponent) {
        return new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtils.getRSAPublicKey(publicModulus, publicExponent).getEncoded()));
    }


    /**
     * 判断非空字符串
     *
     * @param cs 待判断的CharSequence序列
     * @return 是否非空
     */

    private static boolean isBlank(final CharSequence cs) {

        int strLen;

        if (cs == null || (strLen = cs.length()) == 0) {

            return true;

        }

        for (int i = 0; i < strLen; i++) {

            if (Character.isWhitespace(cs.charAt(i)) == false) {

                return false;

            }

        }

        return true;

    }
    
    public static String encryptByPublicKey(String publicKey, String data) throws NoSuchAlgorithmException, InvalidKeySpecException  
    {  
    	PublicKey publicK = null;
		try {
			publicK = getPublicKey(publicKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(publicK);
        String pass = RSAUtils.encryptString(publicK, data);
        System.out.println("----------" + pass);
        return pass;
    }
    
    
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;  
        keyBytes = Base64.decodeBase64(key.getBytes()); 
        System.out.println(key);
        System.out.println(key.length());
        System.out.println(key.getBytes().length);
        System.out.println(keyBytes.length);
//        keyBytes = key.getBytes();
        
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes); 
        System.out.println(keySpec);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec); 
        return publicKey;
    }
    public static void main(String[] args){
//
////        new RSAUtil();
////
////        System.out.println("RSAUtil.publicModulus==" + RSAUtil.publicModulus);
////
////        System.out.println("RSAUtil.publicExponent==" + RSAUtil.publicExponent);
////
////        System.out.println("RSAUtil.privateModulus==" + RSAUtil.privateModulus);
////
////        System.out.println("RSAUtil.privateExponent==" + RSAUtil.privateExponent);
////
////        String pubKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.getRSAPublicKey(RSAUtil.publicModulus, RSAUtil.publicExponent).getEncoded()));
////        //
////        System.out.println(pubKey);
////        //公钥加密，私钥解密
////        String encript = RSAUtil.encryptString(RSAUtil.getRSAPublicKey("008be011c288f0ab9f42e03470c38365904c4fe1a945098b870090cf5f0735c7f64dd330af999fa312394ccbd18751c1efa58940bc158e75d10ff45c62a6f12266bed068bd78cb270802ba95c00a646b1e6ca954300ba3eed4e5aa5cc637ee09e6040533b4ac2e303ceb0c5231e8fabf05ae12061032ec7e01f3f5919b76c819e7", "010001"), "123");
////
////        System.out.println("加密后数据：" + encript);
//
//        String newSource = RSAUtil.decryptString(RSAUtil.getRSAPrivateKey("009841b4cfcf5297f32fa93f0e6843c064a352b94bb38f829d99a3853ed8e111f5885a746e474895815e1765d2c6181c1407011a0c03bce0fb78d33c342e3ec51b637e45d4482f8817be07b31224671bca8d8a49f7061cbf2476ca0b696820bc35aa0999d1004dbdd24813ed156b1054d210d0b2811335a37a0eb2b7e7729c2c13", "00802a374902ed34a96a025ee902cd9d6359b425f45a67407ce7baa54c3d0660b6bd9bdf0c8186d10b002853f733f751936e3df82e0aeb70bb5e7ef9dbb6ddf1ce856dac58d5b286461a02d468b9f05082ec8ab013861ec7d92236aebd56fa79549afff745385a1a4315acff6d162c5e33581aee2c865424e8a37c4f0f35351059"), "IuYw6mbp1TctzVix8yHlDMpGI1FxHi4+34nxJ+Tuof+McJn6Sd6M0bO+Q1J6PtMw0Zpycv/v6XUbRyLjMVeGVo/tMrkKxSTZdJC3ifElA2zrl86vlsnzbRj7AFtvgsXSAQlEQakBHTzAV/dVVy2mPagGImUP6kLozc5NJdamXQY=");
//
//        System.out.println("解密后数据:" + newSource);
    	try {
			RSAUtils.encryptByPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpSCu45Se7ShYzF0ykdfQgqpNcm1gQqAuomcSsjIrDLy/kLbdMlZFez47xvUQ/rMNlGqxlEXQiPp95xcF/hzvoCG26R4Czf3sfTFJohJC47GU+Hn0FHeqqhlElWwXffAnLSsTI7Z0nnpa6QSE83DywxPcVfAhoIQh/qOvbCtgoRwIDAQAB", "a1111111");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
