package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.kdf.KdfService;
import services.timestamp.TimeStampService;

import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet
{

    private final KdfService kdfService;
    private final TimeStampService timestampService;

    @Inject
    public HomeServlet(KdfService kdfService, TimeStampService timestampService)
    {
        this.kdfService = kdfService;
        this.timestampService = timestampService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {

        System.out.println("HomeServlet::doGet");

        req.setAttribute("HomeServlet",
                "Hello from HomeServlet " + kdfService.dk("123", ""));

        req.setAttribute("UnixTimeStampSeconds",
                String.valueOf(timestampService.seconds_for_now()));

        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }
}
