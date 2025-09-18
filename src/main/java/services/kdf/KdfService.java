package services.kdf;

/**
 * * Key derivation function
 * * by RFC 2898 https://datatracker.ietf.org/doc/html/rfc2898
 */


public interface KdfService
{
    String dk (String password, String salt);
}
