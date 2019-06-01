import java.util.ArrayList ; 
import java.util.LinkedHashMap ; 

public class WmiClientClassJson {

	static {
		System.load("C:\\Users\\vignesh-pt1616\\Desktop\\WmiClientLibraryJson\\x64\\Debug\\WmiClientLibraryJson.dll");
	}
	
	public native void createConnection(String query , LinkedHashMap<String,Object> map , ArrayList<LinkedHashMap<String,Object>> list);
}
