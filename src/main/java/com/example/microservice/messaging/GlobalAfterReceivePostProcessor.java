package com.example.microservice.messaging;

import com.example.microservice.exception.IncorrectBearerException;
import com.example.microservice.exception.enums.IncorrectBearerType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.example.microservice.configuration.RabbitMQConfig.QUEUE_BEARER_CHECK;

@Slf4j
@AllArgsConstructor
public class GlobalAfterReceivePostProcessor implements MessagePostProcessor {

    private RabbitTemplate rabbitTemplate;

    @Override
    public Message postProcessMessage(Message message) {
        log.info("checkBearer");
        if (!message.getMessageProperties().getHeaders().containsKey("bearerToken")) {
            return message;
        }
        Boolean bearerToken = (Boolean) rabbitTemplate.convertSendAndReceive(
            QUEUE_BEARER_CHECK,
            (Object) message.getMessageProperties().getHeader("bearerToken")
        );

        if (bearerToken == null) {
            throw new IncorrectBearerException(IncorrectBearerType.NULL);
        } else if (Boolean.FALSE.equals(bearerToken)) {
            throw new IncorrectBearerException(IncorrectBearerType.INCORRECT);
        }
        log.info("checkBearer ok");
        return message;
    }
}
