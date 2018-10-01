package org.goose.game;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * /players/add     name=pippo
 * /players/{name}/rolls    dice1=5   dice2=4       (opzionali)
 */
public class GooseGameWrapperServlet extends HttpServlet {
    private GooseGame goose = new GooseGame();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // names.add(req.getParameter("name"));
        System.out.println("requst - " + req.getPathInfo());
        String[] y = req.getPathInfo().split("/");

        if (y[y.length - 1].equals("add")) {
            write(resp, goose.add(req.getParameter("name")));
        } else if (y[y.length - 1].equals("rolls")) {
            write(resp, goose.userWrite("move " + y[y.length - 2] + " " + req.getParameter("dice1") +
                    ", " + req.getParameter("dice2")));
        } else {
            throw new ServletException("HTTP 500 : Internal server error");
        }

    }

    private void write(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().write(message);
    }
}
