/**
 * �������SocketIoThread����ʵ��socket IO�̳߳صĹ��ܡ���ͨ���̳߳�ʵ�֡�
 * ����socketChannel�����IO������ÿ��SocketChannelֻ�ܹ�����ĳ���ض��̡߳�
 */
package cn.hexing.fk.sockserver.io;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import cn.hexing.fk.common.spi.socket.IClientIO;
import cn.hexing.fk.common.spi.socket.IServerSideChannel;

/**
 *
 */
public class SocketIoThreadPool {
	private static final Logger log = Logger.getLogger(SocketIoThreadPool.class);
	private int ioThreadSize;
	private int port;				//һ���ӦServer�Ķ˿�
	private IClientIO ioHandler;
	private ArrayList<SocketIoThread> threads = new ArrayList<SocketIoThread>();
	
	public SocketIoThreadPool(int port, int ioSize,IClientIO ioHandler){
		this.port = port;
		ioThreadSize = ioSize;
		this.ioHandler = ioHandler;
	}
	
	public void start(){
		for(int i=0; i<ioThreadSize; i++ )
			threads.add(new SocketIoThread(port,ioHandler,i));
		//�ȴ�SocketIoThreadִ����ɡ�
		Thread.yield();
	}
	
	public void stop(){
		for(int i=0; i<ioThreadSize; i++ ){
			threads.get(i).stopThread();
		}
		threads.clear();
	}
	
	/**
	 * �첽Socket��������Socket client���ȵķ��䵽����IO�����̡߳�
	 * @param client
	 */
	public void acceptNewClient(IServerSideChannel client){
		scheduleClient(client,true);
	}
	
	public void addConnectedClient(IServerSideChannel client){
		scheduleClient(client,false);
	}
	
	private void scheduleClient(IServerSideChannel client, boolean acceptMode){
		//ð�������㷨������client������С���̡߳�
		SocketIoThread cur=null,q=null;
		int min = Integer.MAX_VALUE;
		for(int i=0; i<ioThreadSize; i++ ){
			q = threads.get(i);
			if(q.getClientSize()<min ){
				cur = q;
				min = cur.getClientSize();
			}
		}
		if( null == cur ){
			log.error("scheduleClient failed. cur thread == null.");
			return;
		}
		if( acceptMode )
			cur.acceptClient(client);
		else
			cur.addConnectedClient(client);
	}
}