package web.loger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Loger {
    public static void log(String logMessage) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        System.out.println(formattedTime + " " + logMessage);
        LogWebSocket.sendLogMessage(" "+formattedTime+" "+logMessage+"\n");
    }
}
