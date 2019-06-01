
import org.elasticsearch.client.RestHighLevelClient ; 
import org.elasticsearch.action.index.IndexRequest ; 
import org.elasticsearch.action.index.IndexResponse ; 

import java.util.LinkedHashMap ;
import java.util.Date ; 
import java.text.DateFormat ; 
import java.text.SimpleDateFormat ; 
import java.io.IOException ;

import com.adventnet.persistence.DataAccessException ; 


public class LogEventListener {

	static {
		System.load("C:\\Users\\vignesh-pt1616\\Desktop\\LogEventListenerLibrary\\x64\\Debug\\LogEventListenerLibrary.dll");
	}

	RestHighLevelClient client ; 

	public LogEventListener() {}

	public LogEventListener(RestHighLevelClient client) 
	{
		this.client = client ; 	
	}

	public native void listenToEvents(String query);

	public void listenToLogEntry( long lastRetrievedTime )
	{
		//Obtaining the time upto with the quering has been done and forming the query for monitoring logs genereted after that specified time
		String timeToStartMonitoring ;
		Date date = new Date(lastRetrievedTime);
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		timeToStartMonitoring = formatter.format(date) ;
		String query = "Select * from __InstanceCreationEvent where TargetInstance ISA 'Win32_NTLogEvent' AND TargetInstance.TimeGenerated > '"+timeToStartMonitoring+"'" ; 
		System.out.println(query);

		LogEventListener listenerobj = new LogEventListener();
		listenerobj.listenToEvents(query);
	}

	public void indexingLogsToES(LinkedHashMap<String,Object> map) throws IOException,DataAccessException
	{	
		String logname = (String) map.get("Logfile");
		int recordId = 1 ; 
		long lastQueried = Long.parseLong(map.get("TimeGenerated").toString());	
		int recordNumber = Integer.parseInt(map.get("RecordNumber").toString()) ; 

		IndexTimeValueToDB timeIndexObj = new IndexTimeValueToDB();
		timeIndexObj.indexTimeValue(logname , recordId , lastQueried , recordNumber);	
		
		System.out.println("indexed to DB");	

		IndexRequest indexRequest = new IndexRequest("logfilecontents","logs").source(map);
		IndexResponse indexResponse = client.index(indexRequest);
		System.out.println("indexed an event to ES");
	}

}
			