import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ClientsDB {
    private final Map<String, User> usersDB = new HashMap<String, User>();//k:id, v: user(as object)
    private final Map<String, Set<String>> ipDB = new HashMap<String, Set<String>>();//k:id, v: set(ip)
    public final Map<String, Integer> resultMap = new HashMap<String, Integer>();//k:id, v:entries

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
                        if (!usersDB.containsKey(clientsDataList[1])) { //если такого id ещё нет, добавляем в usersDB, создавая пользователя
                            usersDB.put(clientsDataList[1], new User(clientsDataList[1], clientsDataList[2], clientsDataList[3].split("</")[0]));
                        }
                        if (!ipDB.containsKey(clientsDataList[1])) {//если такого id ещё нет
                            ipDB.put(clientsDataList[1], new HashSet<>());//добавляем key и пустой hashset
                        }
                        ipDB.get(clientsDataList[1]).add(clientsDataList[0]);//если есть, вносим в hashset
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
//        System.out.println("ipDB " + ipDB);
    }

    public void fillEntriesMap(ServerLog serverLog) {
        for (Map.Entry<String, Set<String>> item : ipDB.entrySet()) { //k:id, v: set(ip)
            for (String ip : item.getValue()) {//перебираем все ip в set
                if (!resultMap.containsKey(item.getKey())) {//если в мапе ещё нет такого id
                    resultMap.put(item.getKey(), serverLog.findIP(ip));//добавляем этот id и число входов
                } else {//если в мапе уже есть этот id, добавляем ещё число входов под другим ip
                    resultMap.put(item.getKey(), resultMap.get(item.getKey()) + serverLog.findIP(ip));
                }
            }
        }
//        System.out.println("usersDB" + usersDB);
//        System.out.println("resultMap" + resultMap);
    }

    public String findHacker() {
        int maxNumberOfEntries = Collections.max(resultMap.values());//нашли максимальное число входов
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> item : resultMap.entrySet()) {
            if (item.getValue() == maxNumberOfEntries) {//перебираем весь map, сравнива с максимальным числом входов
                for (Map.Entry<String, User> user : usersDB.entrySet()) {
                    if (user.getValue().getId().equals(item.getKey())) {
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

