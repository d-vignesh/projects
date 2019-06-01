import java.util.ArrayList ; 

public class ReadingLogName {
	static {
		System.load("C:\\Users\\vignesh-pt1616\\Desktop\\ReadingLogs\\x64\\Debug\\ReadingLogs.dll");
	}
	
	public native void readLogEvents(ArrayList<String> loglist);
}
