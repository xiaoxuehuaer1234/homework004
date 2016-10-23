package Servlet;

/**
 * Created by lenovo on 2016/10/14.
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginServlet implements Servlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public ServletConfig getServletConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void service(ServletRequest request, ServletResponse res) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("paw");
        System.out.println(username + " " + password);
        PrintWriter out2 = res.getWriter();
        out2.write("<p>abcdefg</p>");
        out2.println("<html><head><title>Hello</title></head><body><h1>Hello," + username + "</h1></body></html>");
    }

    @Override
    public String getServletInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
