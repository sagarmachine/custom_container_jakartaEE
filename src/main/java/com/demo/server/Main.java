package com.demo.server;


import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
         Container container = new Container(8080, "mapping.properties");
         container.start();
    }
}

