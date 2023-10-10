package web;


import com.webnxtb.DataBase.Konfiguracja;
import com.webnxtb.DataBase.hibernate.EncjaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SetterServlet", urlPatterns = {"/"})
public class SetterServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        refreshForm(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        refreshForm(request, response);
    }


    private void refreshForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


       // EncjaDAO encjaDAO = new EncjaDAO<>(Konfiguracja.class);
        List<Konfiguracja> list=new Konfiguracja().pobierzListe();
//        List<Konfiguracja> list = (List<Konfiguracja>) encjaDAO.pobierzListe();

        request.setAttribute("listaKonfiguracji", list);
        request.getRequestDispatcher("TaskLists.jsp").forward(request, response);




    }


}