package com.demo.server;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;

class SocketHandler extends  Thread{
    Socket socket;
    HashMap<String,Servlet> mapping;
    SocketHandler(Socket socket, HashMap<String,Servlet> mapping){
        this.socket=socket;
        this.mapping=mapping;
    }
    @Override
    public void run() {
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));

           CustomHttpRequest customHttpRequest= new CustomHttpRequest(bf);
           customHttpRequest.parse();//request parsed


//
            Servlet servlet = getServlet(customHttpRequest.getPathInfo());


            //parse CustomHttpResponse ->
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            CustomHttpResponse customHttpResponse= new CustomHttpResponse(out);
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();
            servlet.service(customHttpRequest, customHttpResponse);
            out.flush();
        }catch (IOException | ServletException ex){
            System.out.println("Exception 40"+this.getClass().getName());}
        finally{
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("exception can't close");
                System.out.println("Exception 46 "+this.getClass().getName());
            }
        }

    }
    private Servlet getServlet(String path){
        System.out.println(mapping);
        if(mapping.containsKey(path))
            return mapping.get(path);
        throw new RuntimeException("no path found like "+path);
    }
}