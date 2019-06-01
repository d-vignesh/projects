import java.util.ArrayList ; 
import java.util.LinkedHashMap ; 
import java.io.IOException ;


import org.elasticsearch.client.RestHighLevelClient ;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.action.index.IndexRequest ; 
import org.elasticsearch.action.index.IndexResponse ; 
import org.elasticsearch.action.bulk.BulkRequest ; 
import org.elasticsearch.action.bulk.BulkResponse ; 

public class IndexingLogDataToES {
	
	 
	
	public void indexLogData(ArrayList<LinkedHashMap<String,Object>> logdata , String logname ,RestHighLevelClient client) throws IOException
	{
		
		IndexRequest indexRequest;
		 
		
		int length = logdata.size();
		
		for(int i = 0 ; i < length ; i++ )
		{
			int end = i + 100 ;
			BulkRequest bulkRequest = new BulkRequest() ; 
			
			while( i < end && i < length)
			{
				LinkedHashMap<String,Object> mapObj = (LinkedHashMap<String,Object>) logdata.get(i) ; 
				mapObj.put("LogType",logname);
				bulkRequest.add(new IndexRequest("logfilecontents" , "logs").source( mapObj ));
				i++ ; 
			}
			
			BulkResponse bulkResponse = client.bulk(bulkRequest);
		}
		

		
	}
}
