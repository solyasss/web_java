package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.DataAccessor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.config.ConfigService;
import services.kdf.KdfService;
import services.signatures.SignatureService;
import services.timestamp.TimeStampService;

import java.io.IOException;

@Singleton
public class HomeServlet extends HttpServlet
{
    private final DataAccessor dataAccessor;
    private final KdfService kdfService;
    private final SignatureService signatureService;

    @Inject
    public HomeServlet(KdfService kdfService, DataAccessor dataAccessor, SignatureService signatureService) {
        this.kdfService = kdfService;
        this.dataAccessor = dataAccessor;
        this.signatureService = signatureService;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HomeServlet::doGet");

        req.setAttribute("HomeServlet",
                "Hello from HomeServlet "
                        + kdfService.dk("123", "")
                        + "<br/>"
//                        + (dataAccessor.install() ? "Install OK" : "Install error" )
                        + "<br/>"
//                        + (dataAccessor.seed() ? "Seed OK" : "Seed error" )
                +(signatureService.getSignatureHex("123","456"))
                        + "<br/>JWT: " + req.getAttribute("JWT")
                        + "<br/>JwtStatus: " + req.getAttribute("JwtStatus")

        );



        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }

}
