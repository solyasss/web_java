package servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.DataAccessor;
import data.dto.AccessToken;
import data.dto.User;
import data.dto.UserAccess;
import data.jwt.JwtToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.signatures.SignatureService;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Singleton
public class UserServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final SignatureService signatureService;
    private final DataAccessor dataAccessor;

    @Inject
    public UserServlet(DataAccessor dataAccessor, SignatureService signatureService) {
        this.dataAccessor = dataAccessor;
        this.signatureService = signatureService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authHeader = req.getHeader("Authorization");  // Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
        if (authHeader == null || "".equals(authHeader)) { // Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Missing 'Authorization' header"));
            return;
        }

        String authScheme = "Basic ";
        if (!authHeader.startsWith(authScheme)) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Invalid 'Authorization' scheme. Must be " + authScheme));
            return;
        }

        String credentials = authHeader.substring(authScheme.length()); // QWxhZGRpbjpvcGVuIHNlc2FtZQ==
        String userPass;
        try {
            userPass = new String(Base64.getDecoder().decode(credentials));
        } catch (IllegalArgumentException ex) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Invalid credentials. Base64 decode error " + ex.getMessage()));
            return;
        }
        String[] parts = userPass.split(":", 2);
        if (parts.length != 2) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Invalid user-pass. Missing symbol ':' "));
            return;
        }

        UserAccess ua = dataAccessor.getUserAccessByCredentials(parts[0], parts[1]);
        if (ua == null) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Credentials rejected. Access denied"));
            return;
        }
        AccessToken at = dataAccessor.getTokenByUserAccess(ua);
        JwtToken jwt = JwtToken.fromAccessToken(at);
        jwt.setSignature(
                Base64.getEncoder().encodeToString(
                        signatureService.getSignatureBytes(jwt.getBody(), "secret")
                ));
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(jwt.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print(
                gson.toJson("POST works. JWT: " + req.getAttribute("JWT") +
                        ", status: " + req.getAttribute("JwtStatus"))
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(
                gson.toJson("PUT works")
        );
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(gson.toJson("DELETE works"));
    }
}
