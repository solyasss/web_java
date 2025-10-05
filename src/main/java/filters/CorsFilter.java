package filters;

import com.google.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


@Singleton
public class CorsFilter implements Filter

{
    private FilterConfig filterConfig;
    private final Logger logger;

    @Inject
    public CorsFilter(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        chain.doFilter(request,response);

        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");

//        logger.log(Level.INFO, "CORS filter works");

        if ("OPTIONS".equals(req.getMethod())) {
            resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
            resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
        }


    }

    @Override
    public void destroy()
    {
      this.filterConfig = null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
      this.filterConfig=filterConfig;
    }
}
