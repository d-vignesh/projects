import org.elasticsearch.client.RestHighLevelClient ;
import org.elasticsearch.client.RestClient ;

import org.apache.http.HttpHost ;

import java.io.IOException ; 

public class ClientCreation {
	
	RestHighLevelClient client ; 
	public RestHighLevelClient createClient() {
	client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost",9200,"http"))
				);
		
		return client ; 
	}
	
	public void closeClient() throws IOException {
		client.close();
	}

}
