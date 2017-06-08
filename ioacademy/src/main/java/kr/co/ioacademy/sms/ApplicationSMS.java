package kr.co.ioacademy.sms;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.dao.SMSDAO;

public class ApplicationSMS extends SMS{
  
  public ApplicationSMS(SMSDAO smsDAO, Map<String, String> params, List<Map<String, String>> list) {
    super(smsDAO, params, list);
  }
  
  /**
   * SMS 내용 만들기
   */
  public String makeContents(Map<String, String> map) {
    StringBuilder sb = new StringBuilder();
    sb.append("["+map.get("sms_kind_name")+"] ");
    sb.append(map.get("name")+", ");
    sb.append(map.get("application_type_name")+", ");
    sb.append(map.get("curri_name")+"\n");
    sb.append("개강일 : "+map.get("start_ymd"));
    return sb.toString();
  }
}
