package com.exam.service;

import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.config.MySecurityConfig;
import com.exam.helper.UserFoundException;
import com.exam.model.User;
import com.exam.model.UserRole;
import com.exam.repo.RoleRepo;
import com.exam.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private MySecurityConfig bCryptPasswordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public User createUser(User user, Set<UserRole> userRoles) throws Exception {

		User userDb = this.userRepo.findByUserName(user.getUserName());
		if (userDb != null) {
			System.out.println("User is already there !!");
			throw new UserFoundException();
		} else {
			for (UserRole ur : userRoles) {
				roleRepo.save(ur.getRole());
			}
			user.setUserRole(userRoles);
			String enPassword = user.getPasswords();
			user.setPasswords(this.bCryptPasswordEncoder.passwordEncoder().encode(enPassword));
			userDb = this.userRepo.save(user);
			//Role rl = new Role();
		}
		return userDb;
	}
	
	public User getUser(String userName)
	{
		return this.userRepo.findByUserName(userName);
	}
	
	public static String getAESKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256, SecureRandom.getInstanceStrong());
		SecretKey sk = keyGen.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		return encodedKey;
	}

	public static byte[] encrypt1(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
		// get an RSA cipher object and print the provider
		final Cipher cipher = Cipher.getInstance("RSA");
		// encrypt the plain text using the public key
		cipher.init(Cipher.ENCRYPT_MODE, key);
		cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
		e.printStackTrace();
		}
		System.out.println("-------------------------------------");
		
		
			System.out.println(Arrays.toString(cipherText));
			System.out.println(bytesToHex(cipherText));
		
		return cipherText;
		}

	public static String decrypt(String hexText, String secretKey) throws Exception {
		byte[] cText = hexToBytes(hexText);
		ByteBuffer bb = ByteBuffer.wrap(cText);
		System.out.println(bb);
		
		byte[] iv = new byte[12];
		bb.get(iv);
		Arrays.toString(iv);
		byte[] cipherText = new byte[bb.remaining()];
		bb.get(cipherText);
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		System.out.println("IV   "+iv.length);
		SecretKey sk = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, sk, new GCMParameterSpec(128, iv));
		byte[] plainBytes = cipher.doFinal(cipherText);
		System.out.println(Arrays.toString(plainBytes));
		String plainText = new String(plainBytes);
		return plainText;
	}

	public static byte[] getRandomNonce(int numBytes) {
		byte[] nonce = new byte[numBytes];
		new SecureRandom().nextBytes(nonce);
		return nonce;
	}

	public static String bytesToHex(byte[] bytes) {
		StringBuilder result = new StringBuilder();
		for (byte b : bytes)
			result.append(String.format("%02x", b));
		return result.toString();
	}

	public static byte[] hexToBytes(String hexString) {
		int l = hexString.length();
		byte[] value = new byte[l / 2];
		for (int i = 0; i < l; i += 2) {
			value[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
					+ Character.digit(hexString.charAt(i + 1), 16));

		}
		return value;
	}

	public static byte[] encrypt(String text, PublicKey key) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance("RSA");
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}
	
	public static String encrypt(String text, String secretKey) throws Exception {
		byte[] pText=text.getBytes();
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		SecretKey sk = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
		byte[] iv = getRandomNonce(12);
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, sk, new GCMParameterSpec(128, iv));
		byte[] cipherText = cipher.doFinal(pText);
		byte[] cipherTextWithIv = ByteBuffer.allocate(iv.length +
		cipherText.length).put(iv).put(cipherText).array();
		return bytesToHex(cipherTextWithIv);
		}

}
