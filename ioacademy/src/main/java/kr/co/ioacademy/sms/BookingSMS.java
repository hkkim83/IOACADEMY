package kr.co.ioacademy.sms;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.dao.SMSDAO;
import kr.co.ioacademy.util.StringUtil;

public class BookingSMS extends SMS{

  public BookingSMS(SMSDAO smsDAO, Map<String, String> params, List<Map<String, String>> list) {
    super(smsDAO, params, list);
  }
  
  /**
   * SMS 내용 만들기
   */
  public String makeContents(Map<String, String> map) {
    StringBuilder sb = new StringBuilder();
    sb.append("[아이오교육센터]\n");
    sb.append(map.get("name")+"님, ");
    sb.append(StringUtil.cut(map.get("curri_name"),18)+"과목이 ");
    sb.append("모집중입니다.\n");
    sb.append("개강일 : "+map.get("start_ymd"));
    return sb.toString();
  }
}
