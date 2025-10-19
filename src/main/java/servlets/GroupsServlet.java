package servlets;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.DataAccessor;
import data.dto.ProductGroup;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;


@Singleton
public class GroupsServlet extends HttpServlet {

    private final DataAccessor dataAccessor;
    private final Gson gson = new Gson();

    @Inject
    public GroupsServlet(DataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductGroup> groups = dataAccessor.getProductGroups();
        String fileurl = String.format("%s://%s:%d%s/file/",
                req.getScheme(),
                req.getServerName(),
                req.getServerPort(),
                req.getContextPath()
        );
        for(ProductGroup group : groups) {
            group.setImageUrl(fileurl + group.getImageUrl());
        }
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        resp.getWriter().print(
                gson.toJson(groups)
        );
    }

}
