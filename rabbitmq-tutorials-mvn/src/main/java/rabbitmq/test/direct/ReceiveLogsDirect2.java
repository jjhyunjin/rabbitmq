package rabbitmq.test.direct;
import com.rabbitmq.client.*;

public class ReceiveLogsDirect2 {

    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("10.206.21.47");
        factory.setVirtualHost("log");
        factory.setUsername("log");
        factory.setPassword("log");
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        //if (argv.length < 1) {
       //     System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
        //    System.exit(1);
       // }

        //for (String severity : argv) {
            channel.queueBind(queueName, EXCHANGE_NAME, "info");
            channel.queueBind(queueName, EXCHANGE_NAME, "warning");
            channel.queueBind(queueName, EXCHANGE_NAME, "error");
        //}
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}

