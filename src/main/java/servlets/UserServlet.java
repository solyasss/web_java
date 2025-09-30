package servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.DataAccessor;
import data.dto.User;
import data.dto.UserAccess;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Singleton
public class UserServlet extends HttpServlet
{
    private final DataAccessor dataAccessor;
     private final Gson gson = new Gson();

    @Inject
    public UserServlet(DataAccessor dataAccessor)
    {
        this.dataAccessor = dataAccessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Автентифікація за RFC 7617
        String authHeader = req.getHeader("Authorization");
        if(authHeader == null || "".equals(authHeader)) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Missing 'Authorization' header")
            );
            return;
        }

        String authScheme = "Basic ";
        if (!authHeader.startsWith(authScheme)) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Invalid Authorization scheme. Must be " + authScheme)
            );
            return;
        }

        String credentials = authHeader.substring(authScheme.length());
        String userPass;
        try {
            userPass = new String(
                    Base64.getDecoder().decode(credentials)
            ); // Aladdin:open sesame
        } catch (IllegalArgumentException ignored) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Invalid credentials. Base64 decode error " + authScheme)
            );
            return;
        }

        String[] parts = userPass.split(":", 2);
        if (parts.length != 2) {
            resp.setStatus(401);
            resp.getWriter().print(
                    gson.toJson("Invalid user-pass. Missing ':' ")
            );
            return;
        }

        UserAccess ua = dataAccessor.getUserAccessByCredentials(parts[0], parts[1]);

        resp.setHeader("Content-Type", "application/json");
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");

        resp.getWriter().print(

                gson.toJson(ua)

        );

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.getWriter().print(
                gson.toJson("POST works")
        );
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Content-Type", "application/json");

        resp.getWriter().print(
                gson.toJson("PUT works")
        );
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(gson.toJson("PATCH works"));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(gson.toJson("DELETE works"));
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
        resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        resp.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,PATCH,DELETE");

    }

}


