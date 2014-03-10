package cn.hexing.fk.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *@filename	Counter.java
 *TODO
 */
public class Counter {
	private static final Log log=LogFactory.getLog(Counter.class);
	public static final long DEFAULT_LIMIT_VAL=1000;
	private long count;		//����
	private long limit;		//ͳ�ƴ�����ֵ
	private long time;		//�ϴ�ͳ��ʱ��
	private long guard;		
	private long speed;		//ÿ���Ӵ�������
	private String name;
	
	public Counter(){
		this(DEFAULT_LIMIT_VAL,"");
	}
	
	public Counter(long limit,String name){
		this.time=System.currentTimeMillis();
		this.count=0;
		this.limit=limit;
		this.guard=0;
		this.speed=0;
		this.name=name;
	}
	
	public synchronized void add(){
		try{
			count++;
			guard++;
			if(guard>=limit){					
				speed=guard*60000L/(System.currentTimeMillis()-time);
				time=System.currentTimeMillis();
				guard=0;
				log.info(" counter--"+name+"'s speed is:"+speed+"/min , sum is "+count);
			}
		}catch(Exception e){
			//���Խ�籨�������㴦��
			this.count=0;
			this.guard=0;
		}
	}
	
	public synchronized void add(long cc){
		try{
		count+=cc;
		guard+=cc;
		if(guard>=limit){
			long speed=guard*60000L/(System.currentTimeMillis()-time);
			time=System.currentTimeMillis();
			guard=0;
			log.info(" counter--"+name+"'s speed is:"+speed+"/min , sum is "+count);
		}
		}catch(Exception e){
			//
		}
	}

	/**
	 * @return ���� speed��
	 */
	public long getSpeed() {
		return speed;
	}

	/**
	 * @param speed Ҫ���õ� speed��
	 */
	public void setSpeed(long speed) {
		this.speed = speed;
	}

	/**
	 * @return ���� count��
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count Ҫ���õ� count��
	 */
	public void setCount(long count) {
		this.count = count;
	}
}