package filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.jwt.JwtToken;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.signatures.SignatureService;



import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.util.Base64;


@Singleton
public class AuthFilter implements Filter {

    private final SignatureService signatureService;

    @Inject
    public AuthFilter(SignatureService signatureService)
    {
        this.signatureService = signatureService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String authKey = "JwtStatus";
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || "".equals(authHeader)) {
            req.setAttribute(authKey, "Missing 'Auth' header");
        } else {
            String authScheme = "Bearer ";
            if (!authHeader.startsWith(authScheme)) {
                req.setAttribute(authKey, "Invalid Authorization scheme. Must be " + authScheme);
            } else {
                String jwt = authHeader.substring(authScheme.length()).trim();
                if ((jwt.startsWith("\"") && jwt.endsWith("\"")) || (jwt.startsWith("'") && jwt.endsWith("'"))) {
                    jwt = jwt.substring(1, jwt.length() - 1).trim();
                }
                int dotCount = (int) jwt.chars().filter(c -> c == '.').count();
                if (dotCount != 2) {
                    req.setAttribute(authKey, dotCount < 2 ? "Invalid JWT structure: too few parts" : "Invalid JWT structure: too many parts");
                } else {
                    String[] parts = jwt.split("\\.", -1);
                    if (parts.length != 3) {
                        req.setAttribute(authKey, "Invalid JWT structure");
                    } else if (parts[0].isEmpty() || parts[1].isEmpty() || parts[2].isEmpty()) {
                        req.setAttribute(authKey, "Invalid JWT structure: empty segment(s)");
                    } else {
                        String jwtBody = parts[0] + "." + parts[1];
                        String signature = Base64.getEncoder().encodeToString(
                                signatureService.getSignatureBytes(jwtBody, "secret")
                        );
                        if (parts[2].equals(signature)) {
                            req.setAttribute("JWT", JwtToken.fromParts(parts));
                        } else {
                            req.setAttribute(authKey, "Invalid JWT signature");
                        }
                    }
                }
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() { }
}