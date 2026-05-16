Микросервисы User-Service и Notification-Service

Проект состоит из двух микросервисов:
1)user-service - управление пользователями (CRUD операции);
2)notification-service - отправка email уведомлений через Kafka


Оптимально - запуск с Docker:

1) Запуск инфраструктуры

-Запуск PostgreSQL:

docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=password postgres:15

-Запуск Zookeeper и Kafka:

docker run -d --name zookeeper -p 2181:2181 wurstmeister/zookeeper

docker run -d --name kafka -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  wurstmeister/kafka

-Запуск тестового SMTP сервера (MailHog):

docker run -d --name mailhog -p 1025:1025 -p 8025:8025 mailhog/mailhog


Для тестов при отправке писем весьма желательно использовать MailHog (запускается через Docker выше).



Для просмотра отправленных писем: http://localhost:8025



Вкратце как работает проект:
1. Код использует Spring Mail для отправки email через SMTP протокол
2. В конфигурации (`application.yml`) указаны параметры SMTP сервера
3. Для работы нужен реальный или тестовый SMTP сервер (например MailHog - запускается через Docker).
   


Как проверить работу email:

1)Использовать MailHog (желательно):
   
   docker run -d --name mailhog -p 1025:1025 -p 8025:8025 mailhog/mailhog

2)Затем в файле application.yml notification-service:
  
mail:
    host: localhost
    port: 1025
    username: ""
    password: ""

3)Открыть http://localhost:8025 для просмотра писем.




Используемые технологии в данном задании:

-Java 17

-Docker

-Spring Boot 3.1.5

-PostgreSQL

-Apache Kafka

-Spring Kafka

-Spring Mail (SMTP)

-Spring Data JPA

-Maven
