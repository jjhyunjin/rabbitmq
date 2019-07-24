package rabbitmq.test.roundrobin;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

// Round-Robin
public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost("10.206.21.47");
        factory.setVirtualHost("log");
        factory.setUsername("log");
        factory.setPassword("log");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

           // String message = String.join(" ", argv);
            
            for(int i = 0 ; i < 100 ; i++) {
                String message = "Hello World!" + i;
                channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }
            
        }
    }

}
