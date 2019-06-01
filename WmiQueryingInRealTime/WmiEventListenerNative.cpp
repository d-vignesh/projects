#include <windows.h>
#define _WIN32_DCOM
#include <iostream>
using namespace std ; 
#include <wbemidl.h>
#pragma comment(lib, "wbemuuid.lib")
#include <jni.h>
#include "LogEventListener.h"
#include <comdef.h>
#include <string>
#include <time.h>
#include <OleAuto.h>
#include <atlstr.h>
#include <sstream>
#include <Unknwn.h>


JNIEXPORT void JNICALL Java_LogEventListener_listenToEvents(JNIEnv *env, jobject classobj, jstring query)

{

	cout << "inside the listener method" << endl ; 
	const char *Cquery = env->GetStringUTFChars(query,NULL);
	cout << "convert the string" << endl ; 
	jclass ListenerClass = env->GetObjectClass(classobj);
	if(ListenerClass == NULL)
	{
		cout << "could not find the LogEventListener Class "<< endl ;
		return ;  
	}
	cout << "got the listener class references" << endl ; 
	jmethodID indexMethodId = env->GetMethodID( ListenerClass , "indexingLogsToES" , "(Ljava/util/LinkedHashMap;)V");
	if( indexMethodId == NULL )
	{
		cout << "could not get the indexingLogsToES method " << endl ; 
		return ; 
	}
	
	cout << "got the index method references" << endl ; 

	HANDLE hToken = NULL ;
	LPCTSTR lpszPrivilege = "SeSecurityPrivilege";
	BOOL bEnablePrivilege = true ; 

	TOKEN_PRIVILEGES tp ; 
	LUID luid ; 

	if ( !LookupPrivilegeValue(
		NULL,
		lpszPrivilege,
		&luid ))
	 {
		cout << "LookupPrivilegeValue error : %u \n" << GetLastError() ;
		return  ; 
	}

	cout << "status of LookUpPrivilegeValue " << GetLastError() << endl ; 

	tp.PrivilegeCount = 1 ; 
	tp.Privileges[0].Luid = luid ; 
	if( bEnablePrivilege )
	{
		tp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED ; 
	}
	else
	{
		tp.Privileges[0].Attributes = 0 ;
	}

	if(!OpenProcessToken(GetCurrentProcess(),
	   TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY , & hToken ))
	{
		cout << "erroe in open process %u\n" << GetLastError();
		return ;
	}

	if( !AdjustTokenPrivileges(
		hToken,
		FALSE ,
		&tp,
		sizeof(TOKEN_PRIVILEGES),
		(PTOKEN_PRIVILEGES) NULL,
		(PDWORD) NULL ))
	     {
		cout << "AdjustTokenPrivileges error : %u\n" << GetLastError();
		return; 
	     }

	    if(GetLastError() == ERROR_NOT_ALL_ASSIGNED)
	    {
		cout << "The token does not have the specified privilege \n" ;
		return ;  
	    }



	// Initializing the COM
	HRESULT hr ; 
	hr = CoInitializeEx( 0 , COINIT_MULTITHREADED );
	if(FAILED(hr))
	{
		cout << "first failed to initialize COM Library " << hex << hr << endl ; 
		return ;
	}

	//Initialize COM security
	hr = CoInitializeSecurity (
		NULL,
		-1,
		NULL,
		NULL,
		RPC_C_AUTHN_LEVEL_DEFAULT,
		RPC_C_IMP_LEVEL_IMPERSONATE,
		NULL,
		EOAC_NONE,
		NULL
	);



	if(FAILED(hr))
	{
		cout << "second failed to initilize security."<< hex << hr << endl ; 
		CoUninitialize();
		return ;
	}

  	cout << "Initilized the COM"<< endl ;

	//Initializing the IWbemLocator throught a call to CoCreateInstance.
	IWbemLocator *pLoc = 0 ; 
	
	hr = CoCreateInstance(CLSID_WbemLocator,0,CLSCTX_INPROC_SERVER,IID_IWbemLocator,(LPVOID*) & pLoc);
	if(FAILED(hr))
	{
		cout << "failed to create IWbemLocator object"<< hex << hr << endl ; 
		CoUninitialize();
		return ;
	}

	//Connect to WMI through a call to ConnectServer method of IWbemLocator
	IWbemServices *pSvc = 0 ; 
	
	hr = pLoc->ConnectServer(
		_bstr_t(L"ROOT\\CIMV2"),
		NULL,
		NULL,
		0,
		NULL,
		0,
		0,
		&pSvc);

	if(FAILED(hr))
	{
		cout << "could not connect to WMI from ConnectServer method"<<hex<<hr<<endl;
		pLoc->Release();
		CoUninitialize();
		return ;
	}
			
  	cout << "Connected to WMI" << endl ; 

	//Setting security level on a Wmi connection
	
	hr = CoSetProxyBlanket(pSvc,
		RPC_C_AUTHN_WINNT,
		RPC_C_AUTHZ_NONE,
		NULL,
		RPC_C_AUTHN_LEVEL_CALL,
		RPC_C_IMP_LEVEL_IMPERSONATE,
		NULL,
		EOAC_NONE
	);

	if(FAILED(hr))
	{
		cout << "could not set security level on wmi connection" << hex << hr << endl;
		pSvc->Release();
		pLoc->Release();
		CoUninitialize();
		return ;
	}

  	cout << "Security level set on wmi connection" << endl;
	

	//Using the ExecNotificationQuery method that sets a temprorary consumer that listen for the events specified in the query

	IEnumWbemClassObject* pEnumerator = NULL ; 
	hr = pSvc->ExecNotificationQuery (
		bstr_t("WQL"),
		bstr_t(Cquery),
		//bstr_t("select * from __InstanceCreationEvent where TargetInstance ISA 'Win32_NTLogEvent'"),
		WBEM_FLAG_FORWARD_ONLY | WBEM_FLAG_RETURN_IMMEDIATELY,
		NULL,
		&pEnumerator);

	if(FAILED(hr))
	{
		cout << "Query for operating system failed for Win32_NTLogEvent" << hex << endl ; 
		pSvc->Release();
		pLoc->Release();
		CoUninitialize();
		return ; 
	}
  	cout << "data is obtained from the operating system" << endl ; 
	
	//Getting the data from the query for Win32_NTLogEvent

	
	string logname ; int lognum = 0 ;

	if(pEnumerator)
	{
		while ( true )
		{
			lognum++ ; 
			
			
			IWbemClassObject *pclsobj = NULL ,*pclsEvent = NULL ; 
			ULONG uReturn = 0 ; 
			_variant_t vtProp , vtProp1 ;

		
			HRESULT hr = pEnumerator->Next(WBEM_INFINITE,1,&pclsEvent,&uReturn);
			if( 0 == uReturn )
			{
				cout << "loop broke" << endl ;
				cout << "uReturn value :" << uReturn << endl ; 
				break;
			}
  			cout << "uReturn value :" << uReturn << endl ; 

			//Get the object to which the monitoring is created using the TargetInstance property of the Object.
			hr = pclsEvent->Get(_bstr_t(L"TargetInstance"),0,&vtProp1,0,0);
		
 
			//The ExecNotificationQuery method doesnot return the Wmi objects directly and it only returns a notification of the event object.
			//To get the properties of the object convert the obtained reference into an actual Wmi object.	
			IUnknown* str = vtProp1; 
			hr = str->QueryInterface(IID_IWbemClassObject , reinterpret_cast< void** >(&pclsobj));
			
			cout <<"QueryInterface return status" << GetLastError() << endl ; 

			//GETTING THE REFERENCE TO MAPCLASS
			jclass MapClass = env->FindClass("java/util/LinkedHashMap");
			if(MapClass == NULL)
			{
				cout << "MapClass null "<< endl ; 
				return ; 
			}
			jsize len = 1 ; 
	
			//CONSTRUCTOR FOR MAP
			jmethodID mapConstructor = env->GetMethodID(MapClass,"<init>","()V");
			if(mapConstructor == NULL)
			{
				cout << "mapconstructor null "<< endl ; 
				return ; 
			}

			//GETTING THE OBJECT OF THE MAP
			jobject mapobj = env->NewObject(MapClass,mapConstructor,len);
			if(mapobj == NULL)
			{
				cout << "mapobj null "<< endl ; 
				return ; 
			} 
			
			//GETTING THE METHOD ID OF PUT METHOD
			jmethodID putMethod = env->GetMethodID(MapClass , "put" , "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
			if(putMethod == NULL)
			{
				cout << "putMethod null "<< endl ; 
				return ; 
			}

			//GETTING THE REFERENCE FOR INTEGER CLASS
			jclass IntegerClass = env->FindClass("java/lang/Integer");
			if(IntegerClass == NULL)
			{
				cout << "IntegerClass null "<< endl ; 
				return ; 
			}

			//GETTING THE ID OF INTEGER CONSTRUCTOR
			jmethodID IntegerConstructor = env->GetMethodID(IntegerClass , "<init>" , "(I)V");
			if(IntegerConstructor == NULL)
			{
				cout << "IntegerConstructor null "<< endl ; 
				return ; 
			}
  //			cout << "putmethod found " << endl ; 
	
			string Properties[11] = { "Message" ,"ComputerName", "Logfile" , "SourceName" , "Type" , "EventCode" , "EventIdentifer" , "EventType" , "RecordNumber" , "TimeGenerated" , "TimeWritten"};
	
	
		
			int index = 0 ; 
			for( index = 0 ; index < 11 ; index++ )
			{
			
				LPWSTR prop ;
				std::wstring property(Properties[index].begin() , Properties[index].end()) ; 
				prop = (LPWSTR) property.c_str();
				hr = pclsobj->Get(prop, 0 , &vtProp , 0 , 0 );
			
				if(index < 5 )
				{ 
					BSTR bpropertyvalue = vtProp.bstrVal ;
			 
					std::wstring wpropertyvalue(bpropertyvalue,SysStringLen(bpropertyvalue));      //CONVERTING BSTR TO WSTRING
					string propertyvalue(wpropertyvalue.begin(),wpropertyvalue.end());
					propertyvalue.assign(wpropertyvalue.begin(),wpropertyvalue.end());             //CONVERTING WSTRING TO C++ STRING
				
					jstring jpropertyvalue = env->NewStringUTF(propertyvalue.c_str());            //COVERTING C++ STRING TO C CHARARRAY AND THEN TO JSTRING
					env->CallObjectMethod( mapobj , putMethod , env->NewStringUTF(Properties[index].c_str()) , jpropertyvalue); //STORING THE VALUES IN MAP
				//	cout << Properties[index] << ":" <<  propertyvalue << endl ;  
					VariantClear(&vtProp);
				}
				else if( index < 9 )
				{
				
					int propertyvalue ;             //CONVERTING UINT TO INT

					if( index == 5 )
						{ propertyvalue = vtProp.uiVal ;} 
					else if(index == 6 )
						{ propertyvalue = vtProp.ulVal ;} 
					else if(index == 7)
						{ propertyvalue = vtProp.intVal ;} 
					else 
						{ propertyvalue = vtProp.ulVal ; } 

					jint jpropertyvalue = propertyvalue ;		   // CONVERTING INT TO JINT
					jobject IntegerObj = env->NewObject(IntegerClass , IntegerConstructor , jpropertyvalue ); //CREATING AN INTEGER OBJECT
					if(IntegerObj == NULL)
					{
      						cout << "IntegerObj null "<< endl ; 
						return ; 
					}
					env->CallObjectMethod( mapobj , putMethod ,  env->NewStringUTF(Properties[index].c_str()) , IntegerObj ); //STORING THE VALUE IN THE MAP
				//	cout << Properties[index] << ":" <<  propertyvalue << endl ;
					VariantClear(&vtProp);
		     	     }
			      else
			      {
				CString gendate(vtProp);
			//	cout << " gendate : " << gendate << endl ;
				string s(gendate); 
				string year(s,0,4);
				string month(s,4,2);
				string day(s,6,2);
				string hour(s,8,2);
				string minute(s,10,2);
				string sec(s,12,2);
				string millisec(s,15,6);

				struct tm t = {0} ; 
				stringstream yearstrm(year) ; 
				int yr = 0 ; yearstrm >> yr ; 
				stringstream monthstrm(month) ;
				int mn = 0 ; monthstrm >> mn ; 
				stringstream daystrm(day) ;
				int dy = 0 ; daystrm >> dy ; 
				stringstream hourstrm(hour) ;
				int hr = 0 ; hourstrm >> hr ; 
				stringstream minstrm(minute) ;
				int min = 0 ;minstrm >> min ; 
				stringstream secstrm(sec) ;
				int sc = 0 ;secstrm >> sc ;
				stringstream millisecstrm(millisec) ;
				int msec = 0 ; millisecstrm >> msec ; 
							

				t.tm_year = yr-1900 ; 
				t.tm_mon = mn-1 ; 
				t.tm_mday = dy ; 
				t.tm_hour = hr ; 
				t.tm_min = min ; 
				t.tm_sec = sc ; 
				time_t time = mktime(&t) * 1000 + msec ;
			//	cout << yr <<"-" << mn <<"-" << dy << "-" << hr << ":" <<  min << ":" << sc << endl ; 
				stringstream timegen ; 
				timegen << time ; 
				std::string timestr = timegen.str() ;
			//	cout << timestr << endl ;  	

				jstring jgenerateddate = env->NewStringUTF(timestr.c_str());
				env->CallObjectMethod( mapobj , putMethod , env->NewStringUTF(Properties[index].c_str()) , jgenerateddate ); 
				VariantClear(&vtProp);	
			     }

		
		   }
		cout << "calling index method" << endl ; 
		env->CallVoidMethod( classobj , indexMethodId , mapobj );

		cout << "event occured" << lognum << endl ;
		pclsobj->Release();
		pclsEvent->Release();
		VariantClear(&vtProp1);
		VariantClear(&vtProp);
	 
	    } 

       }
	
	pSvc->Release();
	pLoc->Release();
	
	pEnumerator->Release();
	CoUninitialize();
	return ;
		 
}