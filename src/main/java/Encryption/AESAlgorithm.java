package Encryption;

import com.sun.istack.internal.Nullable;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AESAlgorithm {

    private String encryptAlgorithm = "AES";


    public AESAlgorithm() {

    }

    //Encrypt some given text (to a file) using the given key (stored in a file)
    public byte[] encrypt(String string, File key, @Nullable File encryptedFile) throws Exception {

        SecretKey secretKey = KeyStoreUtils.loadKey(key);

        //AES initialization
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedText = Base64.encodeBase64(cipher.doFinal(string.getBytes("UTF-8")));

        if (encryptedFile != null) {
            if (encryptedFile.exists() && !encryptedFile.isDirectory()) {
                if (!encryptedFile.delete()) {
                    return null;
                } else {
                    writeToFile(encryptedFile, encryptedText);
                }

            }
        }
        return encryptedText;
    }

    //Decrypt some text from a file using the given key
    public String decryptFromFile(File file, File key) throws Exception {
        byte[] encryptTextBytes = Base64.decodeBase64(FileUtils.readFileToString(file).getBytes("UTF-8"));

        SecretKey secretKey = KeyStoreUtils.loadKey(key);

        //decrypt the message
        Cipher cipher = Cipher.getInstance(encryptAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptTextBytes;
        try {
            decryptTextBytes = cipher.doFinal(encryptTextBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
        return new String(decryptTextBytes);
    }

    //Write data to file
    private void writeToFile(File f, byte[] data) throws IOException {

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(data);
        fos.flush();
        fos.close();
    }
}
