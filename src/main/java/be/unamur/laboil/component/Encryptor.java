package be.unamur.laboil.component;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Joachim Lebrun on 12-08-18
 */
@Component
public class Encryptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

    private static final String CIPHER_ALGORITHM = "AES";
    private static final String KEY_ALGORITHM = "AES";
    private static final String PASS_HASH_ALGORITHM = "SHA-1";

    private String defaultPass = "ruLfxMiWMkY3i5vK";

    public String encrypt(String data) {
        try {
            Cipher cipher = buildCipher(defaultPass, Cipher.ENCRYPT_MODE);
            byte[] dataToSend = data.getBytes(UTF_8);
            byte[] encryptedData = cipher.doFinal(dataToSend);
            return Base64.encodeBase64URLSafeString(encryptedData);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Issue on encrypt");
        }
    }

    public String decrypt(String encryptedValue) {
        try {
            Cipher cipher = buildCipher(defaultPass, Cipher.DECRYPT_MODE);
            byte[] encryptedData = Base64.decodeBase64(encryptedValue);
            byte[] data = cipher.doFinal(encryptedData);
            return new String(data, UTF_8);

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Issue on decrypt");
        }
    }

    private Cipher buildCipher(String password, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        Key key = buildKey(password);
        cipher.init(mode, key);
        return cipher;
    }

    private Key buildKey(String password) throws NoSuchAlgorithmException {
        MessageDigest digester = MessageDigest.getInstance(PASS_HASH_ALGORITHM);
        digester.update(String.valueOf(password).getBytes(UTF_8));
        byte[] key = Arrays.copyOf(digester.digest(), 16);
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

}
