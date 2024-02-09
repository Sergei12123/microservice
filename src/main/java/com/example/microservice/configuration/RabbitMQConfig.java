package com.example.microservice.configuration;

import com.example.microservice.messaging.GlobalAfterReceivePostProcessor;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    private static final String MAIN_DLX_EXCHANGE = "main.dlx.exchange";

    public static final String QUEUE_BEARER_CHECK = "bearer.check";

    public static final String QUEUE_TRAINING_ITEM_UPDATE = "training.item.update";

    public static final String QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD = "training.item.trainer.workload";

    private final CachingConnectionFactory cachingConnectionFactory;

    @Bean
    public Queue trainingItemUpdateQueue() {
        return QueueBuilder.durable(QUEUE_TRAINING_ITEM_UPDATE)
            .deadLetterExchange(MAIN_DLX_EXCHANGE)
            .build();
    }

    @Bean
    public Queue trainingItemTrainerWorkloadQueue() {
        return QueueBuilder.durable(QUEUE_TRAINING_ITEM_TRAINER_WORKLOAD)
            .deadLetterExchange(MAIN_DLX_EXCHANGE)
            .build();
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setObservationEnabled(true);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(Jackson2JsonMessageConverter jackson2JsonMessageConverter,
                                                                               GlobalAfterReceivePostProcessor afterReceivePostProcessor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        factory.setAfterReceivePostProcessors(afterReceivePostProcessor);
        factory.setErrorHandler(new ConditionalRejectingErrorHandler(
            new ConditionalRejectingErrorHandler.DefaultExceptionStrategy() {
                @Override
                public boolean isFatal(@NonNull Throwable t) {
                    return true;
                }
            }));
        factory.setObservationEnabled(true);
        return factory;
    }

    @Bean
    public GlobalAfterReceivePostProcessor afterReceivePostProcessor(RabbitTemplate rabbitTemplate) {
        return new GlobalAfterReceivePostProcessor(rabbitTemplate);
    }

}
