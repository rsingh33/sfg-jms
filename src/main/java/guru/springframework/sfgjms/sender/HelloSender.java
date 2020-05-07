package guru.springframework.sfgjms.sender;


import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        System.out.println("Sending out a message after every 2 seconds " + LocalTime.now());
        HelloMessage message = HelloMessage
                .builder()
                .message("Sherlock Holmes 223 G Baker Street London" ).build();
        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);
        System.out.println("Message sent to Queue!");
    }
}
