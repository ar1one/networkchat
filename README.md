# Сетевой чат
**Цель:** необходимо разработать два приложения для обмена текстовыми сообщениями по сети с помощью консоли (терминала) между двумя и более пользователями.

**Первое приложение** - сервер чата, ожидает подключения пользователей, ведет статистику online пользователей.

**Второе приложение** - клиент чата, подключается к серверу чата и осуществляет доставку и получение новых сообщений от сервера и пользователей.

Все сообщения записываются в file.log как на сервере, так и на клиентах. File.log дополняется при каждом запуске, а также при отправленном или полученном сообщении. Выход из чата должен осуществляется по команде `exit`.

# Требования к серверу

1. Установка порта для подключения клиентов через файл настроек **settings.txt**;

2. Возможность подключиться к серверу в любой момент и присоединиться к чату как через терминал, так и через класс клиента;

3. Отправка новых сообщений клиентам;

4. Запись всех отправленных через сервер сообщений с указанием имени пользователя и времени отправки.

# Требования к клиенту
1. Выбор имени для участия в чате;

2. Инициализация настроек приложения: номер порта;

3. Подключение к указанному в настройках серверу;

4. Для выхода из чата нужно набрать команду выхода - “/exit”;

5. Каждое сообщение участников должно записываться в текстовый файл - файл логирования. При каждом запуске приложения файл должен дополняться.
