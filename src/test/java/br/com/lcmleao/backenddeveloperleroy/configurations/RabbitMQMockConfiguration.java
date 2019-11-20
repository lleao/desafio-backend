package br.com.lcmleao.backenddeveloperleroy.configurations;


import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQMockConfiguration {
    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin adm = new RabbitAdmin(connectionFactory);
        adm.declareExchange(new DirectExchange("amq.topic"));
        adm.declareQueue( new Queue("SheetProcessor"));
        return adm;
    }
    @Bean
    ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(new MockConnectionFactory());
    }
}
