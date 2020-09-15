import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ServerLog {
    private final Map<String, Integer> numberOfEntries = new HashMap<String, Integer>(); // k:ip, v:кол-во входов

    public void serversLogParser() {//читаем данные логов
        try {
            URL urlServersLog = new URL("https://github.com/netology-code/java-homeworks/blob/master/tree-collections/4.5.3/server.log");
            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(urlServersLog.openStream()));
                String string = reader.readLine();

                while (string != null) {
                    if (string.contains("class=\"blob-code blob-code-inner js-file-line\">")) {//ищем в каждой строке подобную конструкцию
                        String subString = string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"));//разделяем её по тегам
                        String ip = subString.split(":")[0];//забираем ip
                        numberOfEntries.merge(ip, 1, (a,b) -> a + 1);
                    }
                    string = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public Integer findIP(String ip) {//получить кол-во входов по ip
        return numberOfEntries.get(ip) == null ? 0 : numberOfEntries.get(ip);
    }

}