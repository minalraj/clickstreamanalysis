package producer;

import clicks.UserClick;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Scanner;

/**
 * Producer class that produces a stream of user records
 */
public class StreamProducer {

    private static Scanner in;

    public static void main(String[] argv)throws Exception {
        if(argv.length != 1){
            System.err.println("Please specify 1 Parameter");
            System.exit(-1);
        }

        String topicName = argv[0];
        in = new Scanner(System.in);
        System.out.println("Enter message(type quit to exit)");


        //configuring producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.connect.json.JsonSerializer");
        Producer producer = new KafkaProducer(configProperties);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("Provide input : ");
        String line = in.nextLine();
        while (!line.equals("exit")) {
            UserClick  userclick = new UserClick();
            userclick.parseString(line);
            JsonNode jsonNode = objectMapper.valueToTree(userclick);
            ProducerRecord<String, JsonNode> rec = new ProducerRecord<String, JsonNode>(topicName, jsonNode);
            producer.send(rec);
            System.out.println("Provide input : ");
            line = in.nextLine();
        }
        in.close();
        producer.close();

    }


}

