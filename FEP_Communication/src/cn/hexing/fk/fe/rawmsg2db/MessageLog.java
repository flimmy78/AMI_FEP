package cn.hexing.fk.fe.rawmsg2db;

import java.io.Serializable;
import java.util.Date;

/**
 * �ն�����/������Ϣ��ӳ����
 */
public class MessageLog implements Serializable{
	private static final long serialVersionUID = 879756271368369058L;
	/** �ն��߼���ַ */
    private String logicAddress;
    /** ������(�ն��߼���ַǰ��λ) */
    private String qym;
    /** ������ */
    private String kzm;
    /** Դ��ַ */
    private String srcAddr;
    /** Ŀ���ַ */
    private String destAddr;
    /** ͨѶ��ʽ */
    private String txfs;
    /** ͨѶʱ�� */
    private Date time;   
    /** ���Ĵ�С */
    private int size;
    /** ԭʼ���� */
    private String body;
    /** ���������0�ɹ���1ʧ�� */
    private String result;
    
    private String fwqm;		//��������

    
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		//���Ʊ�����ⳤ�ȣ����Ᵽ����������Ƴ��������ݿ���ֶγ���һ��
		if (body.length()>=4000)
			body=body.substring(0,3999);
		this.body = body;
	}


	public String getDestAddr() {
		return destAddr;
	}


	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}


	public String getKzm() {
		return kzm;
	}


	public void setKzm(String kzm) {
		this.kzm = kzm;
	}


	public String getLogicAddress() {
		return logicAddress;
	}


	public void setLogicAddress(String logicAddress) {
		this.logicAddress = logicAddress;
	}


	public String getQym() {
		return qym;
	}


	public void setQym(String qym) {
		this.qym = qym;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public String getSrcAddr() {
		return srcAddr;
	}


	public void setSrcAddr(String srcAddr) {
		this.srcAddr = srcAddr;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public String getTxfs() {
		return txfs;
	}


	public void setTxfs(String txfs) {
		this.txfs = txfs;
	}


	public String toString() {
		return "rtua="+logicAddress + ",message="+body;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}

	public String getFwqm() {
		return fwqm;
	}

	public void setFwqm(String fwqm) {
		this.fwqm = fwqm;
	}
}