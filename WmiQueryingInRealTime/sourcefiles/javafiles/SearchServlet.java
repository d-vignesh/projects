
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat ; 
import java.util.ArrayList ; 
import java.util.LinkedHashMap ;
import java.util.TimeZone;
import java.util.Date ; 
import org.json.JSONException;
import org.json.JSONObject ; 
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.client.RestHighLevelClient ; 


@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static int lognum = 0 ; 
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter outobj = response.getWriter();
		long overallLastQueriedTime = 0 ; 

		try {

		long lastQueried = 0 , generatedTime = 0 ;
		int recordId = 0 , recordNumber = 0 ,length = 0 , logRecordNumber = 0 ; 
		boolean reset ;
		
		String formattedDate = "" ;
		ArrayList<String> loglist = new ArrayList<String>();
		
		//Class that reads the name of the logfile that are registered with the computer
		ReadingLogName readClassObj = new ReadingLogName();
		readClassObj.readLogEvents(loglist);
		
		//Class to create an RestHighLevelClient to communicate to Elasticsearch.
		ClientCreation clientobj = new ClientCreation();
		RestHighLevelClient client = clientobj.createClient();
		
		//Class that contains the native method to read all the log files.
		WmiClientClassJson obj = new WmiClientClassJson();
		
		
		for(String logname : loglist )
		{
		
			if(logname.equals("Security")) 
			{
				continue ; 
			}	
	
			lastQueried = 0 ; generatedTime = 0 ; recordId = 0 ; recordNumber = 0 ; length = 0 ; logRecordNumber = 0 ; 
			reset = false ;  
			
			String searchquery = "SELECT * from Win32_NTLogEvent where ( Logfile = '"+logname+"')";
			
			
			//GETTING THE LAST QUERIED TIME OF THE LOGNAME
			GetLastRetrievedTime timeClassobj = new GetLastRetrievedTime(); 
			LastQueriedDetails timedetails = new LastQueriedDetails();
			
			
			timedetails = timeClassobj.getLastRetrievedTime(logname);
			
			//CONVERTING THE EPOCHTIME IN MILLISECONDS TO REAL WORLD DATE
			if(timedetails.getRecordId() != 0)
			{
				
				lastQueried = (timedetails.getLastQueried()); 
				recordId = timedetails.getRecordId(); 
				recordNumber = timedetails.getRecordNumber(); 
				
				Date date = new Date(lastQueried) ;
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				formattedDate = dateFormat.format(date) ;
				System.out.println(formattedDate);
				searchquery = searchquery.replace(")"," AND TimeGenerated >= '"+formattedDate+"' AND RecordNumber > "+recordNumber +")") ;
			}
			
			System.out.println(searchquery);
			
			LinkedHashMap<String , Object> map = new LinkedHashMap<String , Object>(11);
			ArrayList<LinkedHashMap<String , Object>> list = new ArrayList<LinkedHashMap<String , Object>>(10000);
		
			obj.createConnection(searchquery , map , list ); //method that reads the log files.
			
			length = list.size();
			
			for( int i = 0 ; i < length ; i++ ) {
				LinkedHashMap<String,Object> mapp = (LinkedHashMap<String,Object>) list.get(i) ; 
				generatedTime = Long.parseLong(mapp.get("TimeGenerated").toString());
				logRecordNumber = Integer.parseInt(mapp.get("RecordNumber").toString()) ; 
				
				if(generatedTime >= lastQueried )
				{
					lastQueried = generatedTime ; 
					if(lastQueried > overallLastQueriedTime)
					{
						overallLastQueriedTime = lastQueried ; 
					}
					recordNumber = logRecordNumber ;
				}
			}

			 
		
			IndexingLogDataToES indexingobj = new IndexingLogDataToES();
			indexingobj.indexLogData(list , logname, client);
			
			IndexTimeValueToDB timeIndexObj = new IndexTimeValueToDB();
			timeIndexObj.indexTimeValue(logname , recordId , lastQueried , recordNumber);
		
			outobj.println(searchquery);
		//	System.out.println("------------------------------"+logname+" indexed to elasticsearch--------------------------");
		//	outobj.println("------------------------------"+logname+" indexed to elasticsearch--------------------------");
		
			
		}

		Date timeretrieved = new Date(overallLastQueriedTime);
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		outobj.println("The logs generated till "+formatter.format(timeretrieved)+" are retrieved ");

		//Class that contains to method that sets up an event listener for the event occuring after this.
		LogEventListener listenerobj = new LogEventListener(client);
		listenerobj.listenToLogEntry(overallLastQueriedTime);
		
		client.close(); 
		}
		catch(Exception e ) {outobj.println(e);}
		
	} 

}
