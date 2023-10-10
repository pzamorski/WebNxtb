package web;

import com.webnxtb.DataBase.Konfiguracja;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@WebServlet(name = "KonfiguracjaServlet", urlPatterns = {"/KonfiguracjaServlet"})
public class KonfiguracjaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        saveToDataBase(request, response);
    }

    private void saveToDataBase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nazwaKonfiguracji = request.getParameter("config-name");
        if (nazwaKonfiguracji == null || nazwaKonfiguracji.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format daty
            Date currentDate = new Date(); // Aktualna data
            nazwaKonfiguracji = dateFormat.format(currentDate); // Ustaw nazwę na datę
        }

        String uzytkownikXtb = request.getParameter("xtb-username");
        String hasloXtb = request.getParameter("xtb-password");
        String symbol = request.getParameter("symbol");
        String periodCode = request.getParameter("period-code");
        int liczbaWarstw = Integer.parseInt(request.getParameter("layers"));


        String[] iloscNeuronow = new String[liczbaWarstw];
        for (int i = 0; i < liczbaWarstw; i++) {
            iloscNeuronow[i]=request.getParameter("neurons-layer-"+(i+1));
        }


        String czestotliwoscWyswietlaniaWynikow = request.getParameter("display-frequency");

        Konfiguracja konfiguracja = new Konfiguracja();
        konfiguracja.setNazwaKonfiguracji(nazwaKonfiguracji);
        konfiguracja.setUzytkownikXtb(uzytkownikXtb);
        konfiguracja.setHasloXtb(hasloXtb);
        konfiguracja.setSymbol(symbol);
        konfiguracja.setPeriodCode(periodCode);
        konfiguracja.setLiczbaWarstw(liczbaWarstw);
        konfiguracja.setIloscNeuronow(Arrays.toString(iloscNeuronow));
        konfiguracja.setCzestotliwoscWyswietlaniaWynikow(czestotliwoscWyswietlaniaWynikow);
        konfiguracja.insert();

        request.getRequestDispatcher("/").forward(request, response);


    }
}
