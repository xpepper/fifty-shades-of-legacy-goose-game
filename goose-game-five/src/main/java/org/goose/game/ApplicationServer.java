package org.goose.game;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class ApplicationServer {

    private int port;
    private Servlet servlet;
    private Server server;

    public ApplicationServer(int port, Servlet servlet) {

        this.port = port;
        this.servlet = servlet;
    }

    public void start() throws Exception {
        server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(servlet), "/*");
        server.setHandler(handler);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
