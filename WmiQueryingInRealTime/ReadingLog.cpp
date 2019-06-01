using namespace std ; 
#include <windows.h>
#include <stdio.h>
#include <winevt.h>
#include "ReadingLogName.h"
#include <jni.h>
#include <iostream>
#include <string>
#include <atlstr.h>
#pragma comment(lib , "wevtapi.lib")

JNIEXPORT void JNICALL Java_ReadingLogName_readLogEvents(JNIEnv *env, jobject obj , jobject listobj)

{
	EVT_HANDLE hChannels = NULL ; 
	LPWSTR pBuffer = NULL ; 
	LPWSTR pTemp = NULL ; 
	DWORD dwBufferSize = 0 ; 
	DWORD dwBufferUsed = 0 ; 
	DWORD status = ERROR_SUCCESS ; 

	//Getting ListClass reference
	jclass ListClass = env->GetObjectClass(listobj);
	if( ListClass == NULL )
	{
		cout << "ListClass null " << endl ; 
		return ;
	}

	//Getting ArrayList add method 
	jmethodID addMethod = env->GetMethodID(ListClass , "add" , "(Ljava/lang/Object;)Z");
	if(addMethod == NULL)
	{
		cout << "addMethod null "<< endl ; 
		return ; 
	}
	cout << "add method found " << endl ; 


	//Getting a handle to the enumerator that contains the names of the channels
	//registered in the computer
	hChannels = EvtOpenChannelEnum( NULL , 0 );
	
	if(NULL == hChannels)
	{
		wprintf(L"EvtOpenChannelEnum failed with %lu \n",GetLastError());
		goto cleanup ; 
	}

	wprintf(L"List of Channels\n");

	while(true)
	{
		//Enumerate through the list of channel names
		if(!EvtNextChannelPath( hChannels , dwBufferSize , pBuffer , &dwBufferUsed))
		{
			status = GetLastError();

			if(ERROR_NO_MORE_ITEMS == status )
			{
				break;
			}

			else if ( ERROR_INSUFFICIENT_BUFFER == status )
			{
				//reallocate the buffer if the buffer is not enough
				dwBufferSize = dwBufferUsed ; 
				pTemp = (LPWSTR) realloc (pBuffer , dwBufferSize * sizeof(WCHAR));
				if(pTemp)
				{
					pBuffer = pTemp ; 
					pTemp = NULL ; 
					EvtNextChannelPath(hChannels , dwBufferSize , pBuffer , &dwBufferUsed);
				}
				else
				{
					wprintf(L"realloc failed\n");
					status = ERROR_OUTOFMEMORY ; 
					goto cleanup ; 
				}
			}
			else
			{
				wprintf(L"EvtNextChannelPath failed with %lu \n",status);
			}
		}
			string logname ;
			int len ;
			char* stringptr = 0 ;
			UINT codepage = CP_ACP ; 
			
			len = WideCharToMultiByte(codepage,0,pBuffer,-1,0,0,0,0);
			
			if(len > 0 ) {
				stringptr = new char[len];
				int rc = WideCharToMultiByte(codepage,0,pBuffer,-1,stringptr,len,0,0);
				if(rc != 0 ) {
					stringptr[len-1] = 0 ; 
					logname = stringptr;
				}
			}
			delete [] stringptr ;
			//cout << logname << endl ;  
			env->CallBooleanMethod(listobj,addMethod,env->NewStringUTF(logname.c_str()));
		}

		cleanup :
			if (hChannels)
				EvtClose(hChannels);
			if (pBuffer)
				free(pBuffer);
}