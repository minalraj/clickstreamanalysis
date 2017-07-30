package elasticsearch;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * creating an elastic search java client
 */
public class ESJavaClient {
    private TransportClient client;


    public ESJavaClient() throws UnknownHostException {
        client = new PreBuiltTransportClient(Settings.EMPTY).
                addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
    }


    public void makePostRequest(Map<String, Object> counts, String index, String type) throws UnknownHostException {
        IndexResponse response = client.prepareIndex(index, type).setSource(counts).get();
    }

    public static void main(String[] args) throws UnknownHostException {
        ESJavaClient client = new ESJavaClient();
        String index = "clicks";
        String type = "geostats";

        Map<String, Object> geoCounts = new HashMap<String, Object>();
        geoCounts.put("geo", "21.19998374, 72.84003943");
        geoCounts.put("geocount", 450);

        client.makePostRequest(geoCounts, index, type);

        geoCounts = new HashMap<String, Object>();
        geoCounts.put("geo", "47.57000205, -122.339985");
        geoCounts.put("geocount", 350);

        client.makePostRequest(geoCounts, index, type);
    }
}
