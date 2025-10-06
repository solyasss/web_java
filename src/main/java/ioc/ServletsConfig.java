package ioc;

import com.google.inject.servlet.ServletModule;
import filters.AuthFilter;
import filters.CorsFilter;
import servlets.HomeServlet;
import servlets.UserServlet;

public class ServletsConfig extends ServletModule
{
    @Override
    protected void configureServlets()
    {

        filter("/*").through(CorsFilter.class);
        filter("/*").through(AuthFilter.class);


        serve("/").with(HomeServlet.class);
        serve("/user").with(UserServlet.class);

    }
}
