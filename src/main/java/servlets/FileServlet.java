package servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.storage.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
@Singleton
public class FileServlet extends HttpServlet
{
    private final StorageService storageService;
@Inject
    public FileServlet(StorageService storageService) {
        this.storageService = storageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String slug = req.getPathInfo() ;
        String id = req.getPathInfo();
        try (InputStream rdr = storageService.getStream(id)) {
            resp.setContentType("image/jpeg");
            byte[] buf = new byte[8192];
            int len;
            OutputStream w = resp.getOutputStream();
            while ((len = rdr.read(buf)) > 0) {
                w.write(buf, 0, len);
            }
        }
        catch (IOException ex) {
            resp.getWriter().print(ex.getMessage());
        }

    }
}

