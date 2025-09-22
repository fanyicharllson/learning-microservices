package org.charllson.notificationservice;

import lombok.extern.slf4j.Slf4j;
import org.charllson.notificationservice.event.OrderPlaceEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic",groupId = "notification-group-id")
    public void handleNotification(OrderPlaceEvent orderPlaceEvent) {
        //send email notification
        log.info("Received order place event notification - {} ", orderPlaceEvent.getOrderNumber());

    }

}
