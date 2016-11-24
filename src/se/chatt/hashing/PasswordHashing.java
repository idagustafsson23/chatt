package se.chatt.hashing;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import se.chatt.DAO.User;

public class PasswordHashing {

	public static User generatePasswordHash(String password, User user)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] hash = skf.generateSecret(spec).getEncoded();
		user.setIterations(iterations);
		user.setPassword(toHex(hash));
		user.setSalt(toHex(salt));
		return user;
	}

	public static boolean validatePassword(String inputPassword, User user)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = user.getIterations();
		byte[] salt = fromHex(user.getSalt());
		byte[] hash = fromHex(user.getPassword());

		PBEKeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
		byte[] testHash = skf.generateSecret(spec).getEncoded();
		int diff = hash.length ^ testHash.length;
		for (int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;

	}

	private static byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	private static String toHex(byte[] array) {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");														
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
}
