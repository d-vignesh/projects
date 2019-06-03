import paramiko
import logging
logging.basicConfig(format = '%(asctime)s - %(levelname)s - %(message)s : ', level=logging.DEBUG)


class SSH_Util(object):

	def __init__(self):
		self.client = None
		self.cmd_output = None
		self.cmd_error = None

	def connect(self, host, username, password):
		''' Login to the remote server '''
		connected = True
		try :
			logging.info('Establishing SSH connection')
			self.client = paramiko.SSHClient()
			''' setting the set_missing_host_key_policy to AutoAddPolicy
			so the machine will allow doing ssh to any server
			without prompting an erro in case of unknown server '''
			self.client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
			# connect to the server
			self.client.connect(hostname = host, username = username, password = password, timeout=20) 
		except paramiko.AuthenticationException :
			logging.error('authentication to the server failed, verify the credentials')
			connected = False
		except paramiko.SSHException as e:
			logging.error('could not establish ssh connection : %s' %e)
			result = False
			connected = False
		except Exception,e:
			logging.error('exception in connecting to the server')
			logging.error(e)
			connected = False
		return connected

	def execute_command(self, command):
		''' executes the command on the remote server.
		Returns a tuple containing an integer status and two strings
		the first contains stdout and second contains stderror from the command.
		'''
		executed = True
		try :
			if self.client != None :
				logging.info('executing the command : '+ command)
				stdin, stdout, stderr = self.client.exec_command(command)
				self.cmd_output = stdout.read()
				self.cmd_error = stderr.read()
				if self.cmd_error :
					logging.error('error occured when executing the command :'+ command)
					logging.error('error is :'+ self.cmd_error)
				
				else :
					logging.info('command executed successfully : '+ command)
					logging.debug('command output is :'+ self.cmd_output)
			else :
				logging.error('connection not established')
				executed = False
		except Exception,e:
			logging.error('exception : %s', e)
			executed = False
		return executed

	def upload_file(self, file_local_path, file_remote_path):
		''' uploads the file from client "file_local_path" into the remote server "file_remote_path" '''
		uploaded = True
		try :
			if self.client != None:
				ftp_client = self.client.open_sftp()
				logging.info('uploading the file : ' + file_local_path + ' to the server path ' + file_remote_path )
				ftp_client.put(file_local_path, file_remote_path)
				ftp_client.close()
			else :
				logging.error('SSH connection not established')
		except Exception,e:
			logging.error('exception : %s', e)
			uploaded = False
		return uploaded

	def download_file(self, file_remote_path, file_local_path):
		''' downloads the file from server "file_remote_path" to the client "file_local_path" 
		the "file_remote_path" and "file_local_path" should be absolute path
		the file to be copied and the file to which the content will be copied must exist 
		'''
		downloaded = True
		try :
			if self.client != None:
				ftp_client = self.client.open_sftp()
				logging.info('downloading the server file : ' + file_remote_path + ' to the client path ' + file_remote_path )
				ftp_client.get(file_remote_path, file_local_path)
				ftp_client.close()
			else :
				logging.error('SSH connection not established')
		except Exception,e:
			logging.error('exception : %s', e)
			downloaded = False
		return downloaded

if __name__ == '__main__':
	logging.info('Stack creation script started')
	ssh_obj = SSH_Util()
	ssh_obj.connect(openstack_client_host, openstack_client_username, openstack_client_password)
	logging.debug('starting command execution')
	ssh_obj.execute_command(['ls -a'])
	logging.debug('starting file upload')
	ssh_obj.download_file('/vignesh/test.py', 'vignesh/upgrade_files/test1.py')
	

