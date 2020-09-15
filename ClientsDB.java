import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ClientsDB {
    private final Map<String, User> usersDB = new HashMap<>();//k:ip, v:user
    public final Map<String, Integer> resultMap = new HashMap<>();//k:id, v:entries

    public void userDBParser() {//читаем данные клиентов
        try {
            URL urlUsersDB = new URL("https://github.com/netology-code/java-homeworks/blob/master/tree-collections/4.5.3/users.db");
            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(urlUsersDB.openStream()));
                String string = reader.readLine();
                while (string != null) {
                    if (string.contains("class=\"blob-code blob-code-inner js-file-line\">")) {//ищем в каждой строке подобную конструкцию
                        String subString = string.substring(string.indexOf(">") + 1, string.lastIndexOf("<"));//разделяем эти строки по тегам
                        String[] clientsDataList = subString.split(";", 4);//по разделителю
                            User user = new User(clientsDataList[1], clientsDataList[2], clientsDataList[3].split("</")[0]);//собираем пользователя
                            usersDB.put(clientsDataList[0], user);//добавляем в HashMap
                    }
                    string = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    public void fillEntriesMap(ServerLog serverLog) {
        for (Map.Entry<String, User> user : usersDB.entrySet()) {
            //k:id, v:entries
            resultMap.merge(user.getValue().getId(), serverLog.findIP(user.getKey()), (a, b) -> Integer.sum(a, b));
        }
    }

    public String findHacker() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> item : resultMap.entrySet()) {
            if (item.getValue() == Collections.max(resultMap.values())) {
                for (Map.Entry<String, User> user : usersDB.entrySet()){
                    if (user.getValue().getId().equals(item.getKey())){
                        sb
                                .append("Предполагаемый злоумышленник: " + user.getValue().getFullname())
                                .append("\n")
                                .append("Адрес: " + user.getValue().getAddress())
                                .append("\n")
                                .append("Количество входов: " + item.getValue());
                        return sb.toString();
                    }
                }

            }
        }
        return null;
    }

}

