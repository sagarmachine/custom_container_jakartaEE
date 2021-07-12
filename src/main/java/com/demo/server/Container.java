package com.demo.server;

import jakarta.servlet.Servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Properties;

public class Container {
    String configFile;
    int port;
    HashMap<String,Servlet> mapping= new HashMap<>();
    Container(int port, String configFile){
        this.port=port;
        this.configFile=configFile;
    }
    protected void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        loadProperties();

        while (true) {
            SocketHandler socketHandler = new SocketHandler(serverSocket.accept(), mapping);

            socketHandler.start();
        }
    }
    private void loadProperties() throws IOException {
        InputStream inputStream= getClass().getClassLoader().getResourceAsStream("mapping.properties");
        if(inputStream==null)
             throw  new RuntimeException("file not found");
        Properties properties= new Properties();
        properties.load(inputStream);
        properties.forEach((key, value)-> {
            Servlet servlet=initializeServlets((String) value);
            //servlet.init();
            mapping.put((String) key, servlet);

        });
    }

    private Servlet initializeServlets(String className){
        System.out.println("com.demo.client."+className);
        try {
            return (Servlet)Class.forName("com.demo.client."+className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.exit(1);
        return null;
    }

}
