import com.adventnet.persistence.DataAccess ; 
import com.adventnet.persistence.Row ; 
import com.adventnet.persistence.DataObject ;
import com.adventnet.ds.query.Criteria ;  
import com.adventnet.ds.query.Column ; 
import com.adventnet.ds.query.QueryConstants ;
import com.adventnet.persistence.DataAccessException ; 
import com.adventnet.ds.query.UpdateQuery ; 
import com.adventnet.ds.query.UpdateQueryImpl ;
import com.adventnet.ds.query.Table ;  
import com.adventnet.persistence.DataAccessException ; 
import com.adventnet.persistence.WritableDataObject ; 

class IndexTimeValueToDB {

	public void indexTimeValue(String logname , int recordId , long queriedTime , int recordNumber) throws DataAccessException
		{
		
			if(recordId != 0)
			{
				UpdateQuery query = new UpdateQueryImpl("LastQueriedTime");
				Criteria criteria = new Criteria(new Column("LastQueriedTime","LOGNAME"),logname,QueryConstants.EQUAL);
				query.setCriteria(criteria) ; 
				query.setUpdateColumn("LASTQUERIED",new Long(queriedTime));
				query.setUpdateColumn("RECORDID",recordNumber);
				
				DataAccess.update(query);	
			}

			else
			{
				Row row = new Row("LastQueriedTime");
			//	row.set("LOGID");
				row.set("LOGNAME",logname);
				row.set("LASTQUERIED",new Long(queriedTime));
				row.set("RECORDID",new Integer(recordNumber));

				DataObject d = new WritableDataObject();
				d.addRow(row) ; 
				DataAccess.add(d);
			}
		}

}