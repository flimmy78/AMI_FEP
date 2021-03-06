package cn.hexing.fas.protocol.zj.ggcodec;

import org.apache.log4j.Logger;

import cn.hexing.exception.MessageDecodeException;
import cn.hexing.fas.model.HostCommand;
import cn.hexing.fas.model.HostCommandResult;
import cn.hexing.fas.protocol.data.DataMappingZJ;
import cn.hexing.fas.protocol.zj.ErrorCode;
import cn.hexing.fas.protocol.zj.codec.AbstractMessageDecoder;
import cn.hexing.fas.protocol.zj.parse.ParseTool;
import cn.hexing.fk.message.IMessage;
import cn.hexing.fk.model.BizRtu;
import cn.hexing.fk.model.MeasuredPoint;
import cn.hexing.fk.model.RtuManage;
import cn.hexing.fk.utils.HexDump;
import cn.hexing.fk.utils.StringUtil;

/**
 * 控制操作类(功能码：14H)响应消息解码器
 * @author Administrator
 *	2012年12月3日18:47:45
 */
public class C14MessageDecoder extends AbstractMessageDecoder{
	private static final Logger log = Logger.getLogger(C14MessageDecoder.class);

	public Object decode(IMessage message) {
		HostCommand hc=new HostCommand();
		BizRtu rtu=(RtuManage.getInstance().getBizRtuInCache(HexDump.toHex(message.getRtua())));
		try{
			if(ParseTool.getOrientation(message)==DataMappingZJ.ORIENTATION_TO_APP){	//是终端应答
				int rtype=(ParseTool.getErrCode(message));
				
				if(DataMappingZJ.ERROR_CODE_OK==rtype){		//终端正常应答
					hc.setStatus(HostCommand.STATUS_SUCCESS);
					byte[] data=ParseTool.getData(message);	//取应答数据
					if(data==null || data.length<=0){
						//错误数据
						throw new MessageDecodeException("空数据体");
					}
					int point=data[0];
					String meterNo = "";
					for(int i = 5 ; i >=0 ; i --){
						meterNo+=HexDump.toHex(data[i]);
					}
    				//通过meterNo查找测量点号
    				MeasuredPoint mp=rtu.getMeasuredPointByTnAddr(meterNo);
    				point=Integer.parseInt(mp.getTn());			
					int loc=6;		//前6个字节是电表通信地址
					if(data.length>3){
						toResult(data,loc,point,hc);
					}else{
						//错误数据
						throw new MessageDecodeException("数据长度不对");
					}
				}else{
					//异常应答
					byte[] data=ParseTool.getData(message);
        			if(data!=null && data.length>0){
        				if(data.length==1){//终端只回错误码
        					hc.setStatus(ErrorCode.toHostCommandStatus(data[0]));
        				}else{//终端回数据标识+错误码
        					toResult(data,6,data[0],hc);//前6个字节是电表通信地址
        				}
        			}else{
        				hc.setStatus(HostCommand.STATUS_RTU_FAILED);
        			}
				}
			}else{
				//主站召测 目前是前置机之间通信（设置前置机参数）
				byte[] data=ParseTool.getData(message);	//取设置数据
				if((data!=null) && (data.length>0)){
					String code=ParseTool.BytesToHexC(data,5,2);
					if(code.equals("7100") || code.equals("7101") || code.equals("7102")){//同步终端参数
						//List rtus=(List)Parser39.parsevalue(data,7,0,0);		
					}
				}
			}
		}catch(Exception e){
			throw new MessageDecodeException(e);
		}
		return hc;
	}
	
	/**
	 * 返回设置结果
	 * @param data
	 * @return
	 */
	private void toResult(byte[] data,int loc,int point,HostCommand hc){
		try{
			int iloc=loc;
			while(iloc<data.length){				
				int datakey=((data[iloc+1] & 0xff)<<8)+(data[iloc] & 0xff);//数据标识
				iloc+=2;				
				String result=ParseTool.ByteToHex(data[iloc]);
				setItemResult(hc,point,ParseTool.IntToHex(datakey),result);
				/*if(!result.equals("00")){
					hc.setStatus(ErrorCode.toHostCommandStatus(data[iloc]));
				}*/
				iloc+=1;
			}
		}catch(Exception e){
			log.error(StringUtil.getExceptionDetailInfo(e));
		}
	}
	
	private void setItemResult(HostCommand hc,int point,String code,String result){
		HostCommandResult hcr=new HostCommandResult();
		hcr.setTn(""+point);
		hcr.setCode(code);
		hcr.setValue(result);
		hc.addResult(hcr);		
	}
}
