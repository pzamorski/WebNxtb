package web;

import com.webnxtb.DataBase.Konfiguracja;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Main", urlPatterns = {"/Main"})
public class Main extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        post(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        post(request, response);
    }

    private void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        int idKonfiguration = Integer.parseInt(request.getParameter("Open_Confinguration_button").split("@")[0]);
        Konfiguracja konfiguracja = (Konfiguracja) new Konfiguracja().select(idKonfiguration);
        request.setAttribute("konfiguracja", konfiguracja);
        request.getRequestDispatcher("/main.jsp").forward(request, response);

    }
}
