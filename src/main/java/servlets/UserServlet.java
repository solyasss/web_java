package servlets;

import com.google.gson.Gson;
import com.google.inject.Singleton;
import data.dto.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Singleton
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User()
                .setId(UUID.randomUUID())
                .setName("Петрович")
                .setEmail("user@i.ua");

        Gson gson = new Gson();
        resp.setHeader("Content-Type", "application/json");
        resp.getWriter().print(
                gson.toJson(user)
        );
    }
}
