package services.signatures;

import com.google.inject.Singleton;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Singleton
public class H256SignatureService implements SignatureService
{

    @Override
    public String getSignatureHex(String data, String secret) {
        return bytesToHex(getSignatureBytes(data, secret));
    }

    @Override
    public byte[] getSignature(String data, String secret) {
        return new byte[0];
    }

    public byte[] getSignatureBytes(String data, String secret) {
        String algorithm = "HmacSHA256";
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret.getBytes(), algorithm));
            return mac.doFinal(data.getBytes());
        }
        catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }







    private final char[] hex = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    private String bytesToHex(byte[] input)
    {
        char[] chars = new char[64];
        for (int i = 0; i < 32; i += 1) {
            int b = (input[i] >> 4) & 0x0F;
            chars[2 * i] = hex[b];
            b = (input[i]) & 0x0F;
            chars[2 * i + 1] = hex[b];
        }
        return new String(chars);
    }

}
