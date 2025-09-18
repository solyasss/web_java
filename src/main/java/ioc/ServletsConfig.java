package ioc;

import com.google.inject.servlet.ServletModule;
import servlets.HomeServlet;
import servlets.UserServlet;

public class ServletsConfig extends ServletModule
{
    @Override
    protected void configureServlets()
    {
       serve("/").with(HomeServlet.class);
       serve("/user").with(UserServlet.class);
    }
}
