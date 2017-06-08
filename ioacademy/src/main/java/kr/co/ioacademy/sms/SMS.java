package kr.co.ioacademy.sms;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.dao.SMSDAO;
import kr.co.ioacademy.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gabia.api.ApiClass;
import com.gabia.api.ApiResult;

abstract public class SMS {
  protected final Log logger = LogFactory.getLog(this.getClass());
  protected Map<String, String> params;
  protected List<Map<String, String>> list;
  protected SMSDAO smsDAO;
  
  public SMS(SMSDAO smsDAO, Map<String, String> params, List<Map<String, String>> list) {
    this.params = params;
    this.list   = list;
    this.smsDAO = smsDAO;
  }
  
  public void sendSMS() {
    
    String apiId  = params.get("api_id");
    String apiKey = params.get("api_key");
    String apiNum = params.get("api_num");
    ApiClass api = new ApiClass(apiId, apiKey);
    
    // 메시지 발송
    for(Map<String, String> map : list) {
      map.put("sms_kind"     , params.get("sms_kind"));
      map.put("sms_kind_name", params.get("sms_kind_name"));
      String smsType = "06".equals(map.get("sms_kind")) ? "lms" : "sms";
      map.put("sms_type", smsType);
      map.put("message", StringUtil.convertEscape(makeContents(map)));
      
      // 단문 발송 테스트
      String sendArr[] = new String[7];
      sendArr[0] = smsType;                   // 발송 타입 sms or lms
      sendArr[1] = map.get("send_key");       // 결과 확인을 위한 KEY ( 중복되지 않도록 생성하여 전달해 주시기 바랍니다. )
      sendArr[2] = "[아이오교육센터]";            // LMS 발송시 제목으로 사용 SMS 발송시는 수신자에게 내용이 보이지 않음.
      sendArr[3] = map.get("message");        // 본문 (90byte 제한)
      sendArr[4] = apiNum;                    // 발신 번호
      sendArr[5] = map.get("tel_no");         // 수신 번호
      sendArr[6] = "0";                       // 예약 일자 "2013-07-30 12:00:00" 또는 "0" 0또는 빈값(null)은 즉시 발송 
      for(int i=0; i<sendArr.length; i++)
        logger.info(sendArr[i]);
      String responseXml = api.send(sendArr);
      logger.info("response xml : \n" + responseXml);
      ApiResult res = api.getResult( responseXml );
      logger.info( "code = [" + res.getCode() + "] mesg=[" + res.getMesg() + "]" );
      
      if( res.getCode().compareTo("0000") == 0 )
      {
        String resultXml = api.getResultXml(responseXml);
        logger.info("result xml : \n" + resultXml);
      }
      
      //실제 발송 결과 조회      
      String recArr[] = new String[2];
      recArr[0] = "status_by_ref";     
      recArr[1] = map.get("send_key");       // 발송 시 사용하였던 _UNIQUE_KEY_ 로 조회
      
      responseXml = api.send(recArr);
      res = api.getResult( responseXml );
      if( res.getCode().compareTo("0000") == 0 )
      {
        String resultXml = api.getResultXml(responseXml);
        logger.info("result xml : \n" + resultXml);
      }
      map.put("res_code", res.getCode());
      
      // 전송 결과 DB에 저장 
      smsDAO.insert(map);
    }
  }
  
  abstract public String makeContents(Map<String, String> map);
}
