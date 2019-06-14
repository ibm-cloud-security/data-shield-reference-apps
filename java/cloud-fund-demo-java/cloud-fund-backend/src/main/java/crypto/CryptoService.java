package crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import utils.Constants;

public class CryptoService implements ICrypto {
	
	private String SALT = System.getenv(Constants.SALT_VALUE_ENV);

	@Override
	public SecretKeySpec generateKey(String secret) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	SecretKeyFactory factory = SecretKeyFactory.getInstance(Constants.FACTORY_KEY_ALGORITHM);
    	KeySpec spec = new PBEKeySpec(secret.toCharArray(), SALT.getBytes(), 65536, 256);
    	SecretKey tmp = factory.generateSecret(spec);
    	SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), Constants.KEY_SPEC_ALGORITHM);
    	return secretKey;
	}

	@Override
	public String encrypt(String string, SecretKeySpec secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(Constants.CIPHER_TRANSFORMATION_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(string.getBytes("UTF-8")));
	}

	@Override
	public String decrypt(String string, SecretKeySpec secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(Constants.CIPHER_TRANSFORMATION_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(string)));
	}
	

}
