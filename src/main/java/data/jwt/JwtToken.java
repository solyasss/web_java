package data.jwt;

import com.google.gson.Gson;
import data.dto.AccessToken;
import data.dto.UserAccess;

import java.util.Base64;
import java.util.Date;


public class JwtToken

{
    private final static Gson gson = new Gson();
    private JwtHeader header;
    private JwtPayload payload;
    private String signature;

    @Override
    public String toString()
    {
        return getBody() + "." + signature;

    }

    public static JwtToken fromParts(String[] parts)
    {
        JwtToken jwt = new JwtToken();
        jwt.setHeader(
                gson.fromJson(
                        new String(Base64.getDecoder().decode(parts[0])),
                        JwtHeader.class
                )
        );

        jwt.setPayload(
                gson.fromJson(
                        new String(Base64.getDecoder().decode(parts[1])),
                        JwtPayload.class
                )
        );

        jwt.setSignature(parts[2]);

        return jwt;

    }


    public static JwtToken fromAccessToken(AccessToken at)
    {
        JwtToken jwt = new JwtToken();
        jwt.setHeader( new JwtHeader() );
        JwtPayload payload = new JwtPayload();
        payload.setJti(at.getTokenId().toString());
        payload.setAud(at.getUserAccess().getRoleId());
        payload.setIat(at.getIssuedAt().toString());
        payload.setExp(at.getExpiredAt().toString());
        payload.setIss("JavaWeb222");
        payload.setSub(at.getUserAccess().getUserId().toString());
        Date dob = at.getUserAccess().getUser().getBirthdate();
        if (dob != null)
        {
            payload.setDob(dob.toString());
        }
        payload.setName(at.getUserAccess().getUser().getName());
        payload.setEmail(at.getUserAccess().getUser().getEmail());
        jwt.setPayload(payload);
        return jwt;

    }

    public String getBody() {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(gson.toJson(header).getBytes()) + "." +
                encoder.encodeToString(gson.toJson(payload).getBytes());
    }



    public JwtHeader getHeader()
    {
        return header;
    }

    public void setHeader(JwtHeader header)
    {
        this.header = header;
    }

    public JwtPayload getPayload()
    {
        return payload;
    }

    public void setPayload(JwtPayload payload)
    {
        this.payload = payload;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }
}
