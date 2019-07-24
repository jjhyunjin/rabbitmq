package rabbitmq.test.fanout;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


// Publish/Subscribe (FanOut)
// receive client 2개 이상 띄어서 확인
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost("10.206.21.47");
        factory.setVirtualHost("log");
        factory.setUsername("log");
        factory.setPassword("log");
        
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            
            //String message = argv.length < 1 ? "info: Hello World!" :
            //        String.join(" ", argv);
            //channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            //System.out.println(" [x] Sent '" + message + "'");
            
            for(int i = 0 ; i < 100 ; i++) {
                String message = "Hello World!" + i;
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            }
            
        }
    }

}

