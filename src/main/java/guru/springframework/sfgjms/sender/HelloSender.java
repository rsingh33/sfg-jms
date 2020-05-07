package guru.springframework.sfgjms.sender;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {

        HelloMessage message = HelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Sherlock Holmes 223 G Baker Street London").build();
        jmsTemplate.convertAndSend(JMSConfig.MY_QUEUE, message);

    }

    @Scheduled(fixedRate = 5000)
    public void sendAndReceiveMessage() throws JMSException {

        HelloMessage message = HelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Name is Bond, James Bond").build();


        Message messageReceived = jmsTemplate.sendAndReceive(JMSConfig.SEND_RCV_QUEUE, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloMessage");
                    System.out.println("Sending message and waiting for a reply");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("KABOOOOOMMMMMMM");
                }
            }
        });
        System.out.println("Message Received as Response " +
                messageReceived.getBody(String.class));
    }
}
