package consumer;

import clicks.UserClick;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.IterableSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;


import java.util.*;

/**
 * Consumer class that consumes the stream of records produced by the producer
 */
public class StreamConsumer {
    private  static Scanner in;

    //map to count clicks based on userId
    static Map<Integer, Integer> userCountMap = new HashMap<Integer, Integer>();

    //map to count clicks based on gender
    static Map<String, Integer> genderCountMap = new HashMap<String, Integer>();

    //map to count clicks based on geo location(India)
    static Map<String, Integer> geoLocCountMap = new HashMap<String, Integer>();

    //map to count clicks based on age group
    static Map<Integer, Integer> ageGrpCountMap = new HashMap<Integer, Integer>();


    public static void main(String[] argv)throws Exception {

        if (argv.length != 2) {
            System.err.printf("Usage: %s <topicName> <groupId> \n",
                    StreamConsumer.class.getSimpleName());
            System.exit(-1);
        }
        in = new Scanner(System.in);
        String topicName = argv[0];
        String groupId = argv[1];

        ConsumerThread consumerRunnable = new ConsumerThread(topicName, groupId);
        consumerRunnable.start();
        String line = "";
        while (!line.equals("exit")) {
            line = in.next();
        }
        consumerRunnable.getKafkaConsumer().wakeup();
        System.out.println("Stopping consumer......");
        consumerRunnable.join();



    }

    private static  class ConsumerThread extends Thread {
        private String topicName;
        private String groupId;
        private KafkaConsumer<String, JsonNode> kafkaConsumer;


        public ConsumerThread(String topicName, String groupId) {
            this.topicName = topicName;
            this.groupId = groupId;
        }

        public void run() {
            Properties configProperties = new Properties();
            configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
            configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.connect.json.JsonDeserializer");
            configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

            kafkaConsumer = new KafkaConsumer<String, JsonNode>(configProperties);
            kafkaConsumer.subscribe(Arrays.asList(topicName));
            ObjectMapper mapper = new ObjectMapper();

            //Start processing user records

            try {
                while (true) {
                    ConsumerRecords<String, JsonNode> records = kafkaConsumer.poll(100);
                    for (ConsumerRecord<String, JsonNode> record : records) {
                        JsonNode jsonNode = record.value();

                        UserClick userClick = mapper.treeToValue(jsonNode, UserClick.class);
                        System.out.println(userClick);

                        // update userID count
                        int userUpdatedCount =1;
                        if(userCountMap.containsKey(userClick.getuserID())) {
                            userUpdatedCount = userCountMap.get(userClick.getuserID()) +1;
                        }
                        userCountMap.put(userClick.getuserID(), userUpdatedCount);

                        System.out.println("User id count map : " + mapper.writeValueAsString(userCountMap));



                        // update gender count
                        int genderUpdatedCount =1;
                        if(genderCountMap.containsKey(userClick.getGender().toLowerCase())){
                            genderUpdatedCount = genderCountMap.get(userClick.getGender().toLowerCase()) +1;
                        }
                        genderCountMap.put(userClick.getGender().toLowerCase(), genderUpdatedCount);
                        System.out.println("Gender map : " + mapper.writeValueAsString(genderCountMap));



                        // update geoLocation count
                        int geoLocUpdatedCount =1;
                        if(geoLocCountMap.containsKey(userClick.getGeoLocation().toLowerCase())){
                            geoLocUpdatedCount = geoLocCountMap.get(userClick.getGeoLocation().toLowerCase()) +1;
                        }
                        geoLocCountMap.put(userClick.getGeoLocation().toLowerCase(), geoLocUpdatedCount);
                        System.out.println("Geo map : " + mapper.writeValueAsString(geoLocCountMap));


                        /** assigning keys to age grps:
                         * age: 15-19 -> key: 15
                         *      20-24 ->      20
                         *      25-29 ->      25
                         *      30-34 ->      30
                         *      35-39 ->      35
                         *      40-44 ->      40
                         *      45-49 ->      45
                         *      50 and above  50
                         */

                        int ageMapKey;
                        if (userClick.getAge() >= 50) {
                            ageMapKey = 50;
                        } else {
                            int remainder = userClick.getAge() % 5;
                            ageMapKey = userClick.getAge() - remainder;
                        }


                        // update age group count
                        int ageGrpUpdatedCount1 =1;
                        if(ageGrpCountMap.containsKey(ageMapKey)) {
                            ageGrpUpdatedCount1 = ageGrpCountMap.get(ageMapKey) +1;
                        }
                        ageGrpCountMap.put(ageMapKey, ageGrpUpdatedCount1);
                        System.out.println( " Age grp count : "+ mapper.writeValueAsString(ageGrpCountMap));


                    }



                }
            } catch (WakeupException ex) {
                System.out.println("Exception caught" + ex.getMessage());

            } catch (JsonProcessingException e) {
                e.printStackTrace();

            } finally {
                kafkaConsumer.close();
                System.out.println("After closing kafka StreamConsumer");

            }


        }

        public KafkaConsumer<String, JsonNode> getKafkaConsumer() {
            return this.kafkaConsumer;
        }
    }
}
