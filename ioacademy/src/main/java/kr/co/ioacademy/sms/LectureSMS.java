package kr.co.ioacademy.sms;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.dao.SMSDAO;
import kr.co.ioacademy.util.StringUtil;

public class LectureSMS extends SMS{
  
  public LectureSMS(SMSDAO smsDAO, Map<String, String> params, List<Map<String, String>> list) {
    super(smsDAO, params, list);
  }
  
  /**
   * SMS 내용 만들기
   */
  public String makeContents(Map<String, String> map) {
    StringBuilder sb = new StringBuilder();
    sb.append("[아이오교육센터]\n");
    sb.append(map.get("name")+"님, ");
    sb.append(StringUtil.cut(map.get("curri_name"), 15)+" 과목이 ");
    sb.append(map.get("sms_kind_name")+"되었습니다.\n");
    // 폐강일 경우
    if("06".equals(map.get("sms_kind")))
      sb.append("더 좋은 강좌로 찾아뵙겠습니다. 수강 신청해 주셔서 감사합니다.\n");
    sb.append("개강일 : "+map.get("start_ymd"));
    return sb.toString();
  }
}
