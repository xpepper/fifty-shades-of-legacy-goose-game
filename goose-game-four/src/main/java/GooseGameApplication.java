import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GooseGameApplication extends HttpServlet {

    List<String> names = new ArrayList<>();
    GooseGame gooseGame;

    public GooseGameApplication(GooseGame gooseGame)
    {
        this.gooseGame = gooseGame;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(names.isEmpty()) {
            write(resp, "empty");
        }
        else {
            String url[] = req.getRequestURI().split("/");
            String player = url[url.length -2];
            write(resp, gooseGame.MovePlayer(player));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        names.add(req.getParameter("name"));

        write(resp, gooseGame.AddPlayer(req.getParameter("name")));
    }

    private void write(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().write(message);
    }
}
