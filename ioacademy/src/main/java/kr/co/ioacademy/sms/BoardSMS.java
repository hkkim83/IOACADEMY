package kr.co.ioacademy.sms;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.dao.SMSDAO;

public class BoardSMS extends SMS{

  public BoardSMS(SMSDAO smsDAO, Map<String, String> params, List<Map<String, String>> list) {
    super(smsDAO, params, list);
  }
  
  /**
   * SMS 내용 만들기
   */
  public String makeContents(Map<String, String> map) {
    StringBuilder sb = new StringBuilder();
    sb.append("["+map.get("sms_kind_name")+"] ");
    sb.append(map.get("curri_name")+" 과목에 새로운 질문이 등록되었습니다.\n");   
    return sb.toString();
  }
}
