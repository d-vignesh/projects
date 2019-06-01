import com.adventnet.persistence.DataAccess ; 
import com.adventnet.persistence.Row ; 
import com.adventnet.persistence.DataObject ;
import com.adventnet.ds.query.Criteria ;  
import com.adventnet.ds.query.Column ; 
import com.adventnet.ds.query.QueryConstants ;
import com.adventnet.persistence.DataAccessException ; 

import java.util.Iterator ; 

class GetLastRetrievedTime {

	public LastQueriedDetails getLastRetrievedTime(String logname) throws DataAccessException {
	
		//String[] timedetails = new String[3];
		LastQueriedDetails timedetails = new LastQueriedDetails() ; 

		Criteria condition = new Criteria(new Column("LastQueriedTime","LOGNAME"),logname,QueryConstants.EQUAL ) ;
		DataObject dataset = DataAccess.get("LastQueriedTime", condition);
		Iterator it = dataset.getRows("LastQueriedTime");
		
		while(it.hasNext())
		{
			Row row = (Row)it.next();
			timedetails.setRecordId((int)row.get(1)) ; 
			timedetails.setLastQueried((long)row.get(3)) ; 
			timedetails.setRecordNumber((int)row.get(4)) ; 
		}	

		
	     return timedetails ; 		
	}
}