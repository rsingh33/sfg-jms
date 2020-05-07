package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JMSConfig;
import guru.springframework.sfgjms.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JMSConfig.MY_QUEUE)
    public void listen(@Payload HelloMessage helloMessage,
                       @Headers MessageHeaders header,
                       Message message){

    }

    @JmsListener(destination = JMSConfig.SEND_RCV_QUEUE)
    public void listenAndRespond(@Payload HelloMessage helloMessage,
                       @Headers MessageHeaders header,
                       Message message) throws JMSException {

        HelloMessage payLoadMessage = HelloMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Bond who???????").build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(),payLoadMessage);

    }
}
