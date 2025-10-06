package services.signatures;

public interface SignatureService
{
    String getSignatureHex(String data, String secret);
    byte[] getSignature(String data, String secret);

    byte[] getSignatureBytes(String body, String secret);
}
