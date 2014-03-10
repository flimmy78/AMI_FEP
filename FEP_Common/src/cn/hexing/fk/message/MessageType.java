package cn.hexing.fk.message;

public class MessageType {
	public static final MessageType MSG_INVAL = new MessageType("�Ƿ����ͱ���");
	public static final MessageType MSG_ZJ = new MessageType("�㽭��Լ");
	public static final MessageType MSG_GW_10 = new MessageType("�������ܲɼ���Լ2010�汾");
	public static final MessageType MSG_DLMS = new MessageType("DLMS��Լ");
	public static final MessageType MSG_ANSI = new MessageType("ANSI��Լ");
	public static final MessageType MSG_DLMS_HDLC = new MessageType("DLMS��·���Լ");
	public static final MessageType MSG_GW_04 = new MessageType("����04��Լ");
	public static final MessageType MSG_GW_96 = new MessageType("����96��Լ");
	public static final MessageType MSG_WEB = new MessageType("��վWEB�ӿڹ�Լ");
	public static final MessageType MSG_HR = new MessageType("��¡���̹�Լ");
	public static final MessageType MSG_HG = new MessageType("���ڳ��̹�Լ");
	public static final MessageType MSG_MONITOR = new MessageType("���");
	public static final MessageType MSG_GATE = new MessageType("����");
	public static final MessageType MSG_UMS = new MessageType("�����Ͷ��ű���");
	public static final MessageType MSG_GATE_230 = new MessageType("230�ŵ��ӿڹ�Լ");
	public static final MessageType MSG_ZJPB = new MessageType("�㽭����Լ");
	public static final MessageType MSG_SAMPLE = new MessageType("simple message sample");
	public static final MessageType MSG_KILLTHREAD = new MessageType("kill thread message");
	public static final MessageType MSG_CALLUP = new MessageType("©�㲹��֪ͨ����");
	public static final MessageType MSG_BYTES = new MessageType("�ֽ�������Ϣ");
	public static final MessageType MSG_BENGAL = new MessageType("Bengal");
	public static final MessageType MSG_GG = new MessageType("���ݹ�Լ");
	private final String desc;

	private MessageType(String msgType){
		desc = msgType;
	}

	public String toString() {
		return desc;
	}
}