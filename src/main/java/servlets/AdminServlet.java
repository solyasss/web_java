package servlets;


import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.DataAccessor;
import data.jwt.JwtToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.FileItem;
import services.form.FormParseException;
import services.form.FormParseResult;
import services.form.FormParseService;
import services.storage.StorageService;

import java.io.IOException;

@Singleton
public class AdminServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final DataAccessor dataAccessor;
    private final FormParseService formParseService;
    private final StorageService storageService;

    @Inject
    public AdminServlet(DataAccessor dataAccessor, FormParseService formParseService, StorageService storageService) {
        this.dataAccessor = dataAccessor;
        this.formParseService = formParseService;
        this.storageService = storageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JwtToken jwtToken = (JwtToken) req.getAttribute("JWT");
        if(jwtToken == null) {
            resp.setStatus(401);
            resp.setContentType("text/plain");
            resp.getWriter().print( req.getAttribute("JwtStatus") );
            return;
        }
        String slug = req.getPathInfo() ;
        switch(slug) {
            case "/groups": this.getGroups(req, resp); break;
            default:
                resp.setStatus(404);
                resp.setContentType("text/plain");
                resp.getWriter().print( "Slug " + slug + " not found" );
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JwtToken jwtToken = (JwtToken) req.getAttribute("JWT");
        if(jwtToken == null) {
            resp.setStatus(401);
            resp.setContentType("text/plain");
            resp.getWriter().print( req.getAttribute("JwtStatus") );
            return;
        }
        String slug = req.getPathInfo() ;
        switch(slug) {
            case "/group": this.postGroup(req, resp); break;
            default:
                resp.setStatus(404);
                resp.setContentType("text/plain");
                resp.getWriter().print( "Slug " + slug + " not found" );
        }
    }

    private void getGroups(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.getWriter().print(
                gson.toJson(dataAccessor.adminGetProductGroups())
        );
    }

    private void postGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String savedName = "No files";
        try {
            FormParseResult res = formParseService.parse(req);
            for (FileItem item : res.getFiles().values()) {
                savedName = storageService.save(item);
            }
        }
        catch (FormParseException ex) {
            savedName = ex.getMessage();
        }

        resp.setContentType("application/json");
        resp.getWriter().print(
                gson.toJson(savedName)
        );
    }
}
