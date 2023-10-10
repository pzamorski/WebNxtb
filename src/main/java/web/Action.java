package web;

import com.webnxtb.DataBase.Konfiguracja;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import com.webnxtb.UCISequenceClassification;
import org.json.JSONObject;
import web.loger.Loger;

@WebServlet(name = "Action", urlPatterns = {"/Action"})
public class Action extends HttpServlet {
    int id;
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        post(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        post(request, response);
    }

    private void post(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(requestBody.toString());
        String idButton = jsonObject.getString("idButton");

        String[] split = idButton.split("@");
        id = Integer.parseInt(split[1]);
        String action = split[0];

        if (action.equals("start")) {
            start();
        } else if (action.equals("stop")) {
            Loger.log("Stop");
        } else if (action.equals("delete")) {
            del();
            Loger.log("Usunieto");
        }


    }

    private void start(){
        Loger.log("Start UCISequenceClassification");
        Thread thread = new Thread(() -> {
            Konfiguracja konfiguracja = (Konfiguracja) new Konfiguracja().select(id);
            try {
                UCISequenceClassification.run(konfiguracja);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();




    }

    private void del(){
        new Konfiguracja().usunEncjeById(id);
    }
}
