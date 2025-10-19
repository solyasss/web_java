package servlets;


import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import data.DataAccessor;
import data.dto.ProductGroup;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class AdminServlet extends HttpServlet {

    private final DataAccessor dataAccessor;
    private final Gson gson = new Gson();
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
        if (jwtToken == null) {
            resp.setStatus(401);
            resp.setContentType("text/plain");
            resp.getWriter().print(req.getAttribute("JwtStatus"));
            return;
        }

        String slug = req.getPathInfo();

        switch (req.getPathInfo()) {
            case "/groups":
                this.getGroups(req, resp);
                break;
            default:
                resp.setStatus(404);
                resp.setContentType("text/plain");
                resp.getWriter().print("Slug " + slug + " not found!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JwtToken jwtToken = (JwtToken) req.getAttribute("JWT");
        if (jwtToken == null) {
            resp.setStatus(401);
            resp.setContentType("text/plain");
            resp.getWriter().print(req.getAttribute("JwtStatus"));
            return;
        }

        String slug = req.getPathInfo();

        switch (req.getPathInfo()) {
            case "/group":
                this.postGroup(req, resp);
                break;
            default:
                resp.setStatus(404);
                resp.setContentType("text/plain");
                resp.getWriter().print("Slug " + slug + " not found!");
        }
    }

    private void getGroups(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.getWriter().print(
                gson.toJson(dataAccessor.adminGetProductGroups())
        );
    }

    private void postGroup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            FormParseResult res = formParseService.parse(req);
            Map<String, String> fields = res.getFields();
            Collection<FileItem> files = res.getFiles().values();
            Map<String, String> errors = new java.util.HashMap<>();

            String name = fields.get("pg-name");
            String description = fields.get("pg-description");
            String slug = fields.get("pg-slug");
            String parentId = fields.get("pg-parent-id");

            if (name == null || name.isBlank()) {
                errors.put("pg-name", "Обязательное поле");
            } else if (name.length() > 64) {
                errors.put("pg-name", "Максимум 64 символа");
            }

            if (description == null || description.isBlank()) {
                errors.put("pg-description", "Обязательное поле");
            }

            if (slug == null || slug.isBlank()) {
                errors.put("pg-slug", "Обязательное поле");
            } else if (slug.length() > 64) {
                errors.put("pg-slug", "Максимум 64 символа");
            } else if (!slug.matches("^[a-z0-9-]+$")) {
                errors.put("pg-slug", "Только [a-z0-9-]");
            }

            if (parentId != null && !parentId.isBlank()) {
                try {
                    UUID pid = UUID.fromString(parentId);
                    if (!dataAccessor.productGroupExists(pid)) {
                        errors.put("pg-parent-id", "Группа не найдена");
                    }
                } catch (IllegalArgumentException e) {
                    errors.put("pg-parent-id", "Неверный UUID");
                }
            }

            if (files.isEmpty()) {
                errors.put("pg-image", "Требуется файл");
            }

            if (!errors.isEmpty()) {
                resp.setStatus(400);
                resp.getWriter().print(gson.toJson(java.util.Map.of("errors", errors)));
                return;
            }

            if (dataAccessor.productGroupSlugExists(slug)) {
                resp.setStatus(409);
                resp.getWriter().print(gson.toJson(java.util.Map.of("message", "Слаг уже занят", "errors", java.util.Map.of("pg-slug", "Слаг уже занят"))));
                return;
            }

            ProductGroup productGroup = new ProductGroup();
            productGroup.setName(name);
            productGroup.setDescription(description);
            productGroup.setSlug(slug);
            if (parentId != null && !parentId.isBlank()) {
                productGroup.setParentId(UUID.fromString(parentId));
            }
            productGroup.setImageUrl(storageService.save(files.stream().findFirst().get()));

            dataAccessor.addProductGroup(productGroup);
            resp.getWriter().print(gson.toJson("Ok"));
        } catch (FormParseException ex) {
            resp.setStatus(400);
            resp.getWriter().print(gson.toJson(java.util.Map.of("message", ex.getMessage())));
        }
    }

}
