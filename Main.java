public class Main {

    public static void main(String[] args) {
        /*Неизвестный попытался произвести атаку на сервер интернет-провайдера, но администратор вовремя спохватился и успел снять логи перед тем, как отключить всю систему. В логах не так много ip-адресов. Известно, что злоумышленник делал больше всего запросов на сервер. Нужно как можно скорее выяснить ip-адрес злоумышленника, отыскать его данные в базе клиентов и отправить данные в полицию.*/
        ClientsDB newClient = new ClientsDB();
        ServerLog newLog = new ServerLog();

        //забираем данные с github
        newClient.userDBParser();//собираем данные по пользователям
        newLog.serversLogParser();//собираем лог

        newClient.fillEntriesMap(newLog);

        System.out.println(newClient.findHacker());

    }


}

