package com.sean.ttj.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

/**
 * Created by tengdj on 2016/6/24.
 */
public class ApplicationServer {

    public static String context = "";

    public static String PATH = "ttj-web/";

    public static String webAppPath = PATH+"src/main/webapp";

    public static String defaultsDescriptor = PATH+"src/test/resources/webdefault.xml";

    public static int port = 8050;

    public static void main(String[] args) {
        try {
            System.out.println("乱码测试");
            if (checkPort(port)) {
                throw new BindException("The port:: [" + port + "]  " + "is already in Use...");
            }

            Server server = new Server(port);
            WebAppContext webContext = new WebAppContext(webAppPath, context);

            webContext.setDefaultsDescriptor(defaultsDescriptor);

            webContext.setClassLoader(Thread.currentThread().getContextClassLoader());

            server.setHandler(webContext);

            server.setStopAtShutdown(true);

            server.start();

        } catch (Exception e) {
            System.err.print(e.toString());
        }

    }

    private static boolean checkPort(int port) {
        try {
            ServerSocket ss = new ServerSocket(port);
            ss.close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }

}
