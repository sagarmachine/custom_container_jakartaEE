package com.demo.client;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    public HelloServlet(){
        System.out.println(this.getClass().toString()+" instantiated");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out= resp.getWriter();
        System.out.println("servlet");
        out.println("<html><body><h1>Hello</h1></body></html>");
        out.println("<form action=\"login\" method=\"post\">");
        out.println("<label for=\"fname\">First name:</label><br>");
        out.println("<input type=\"text\" id=\"fname\" value=\"Mike\"><br><br>");
        out.println("<label for=\"lname\">Last name:</label><br>");
        out.println("<input type=\"text\" id=\"lname\" value=\"Walker\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
    }

}
