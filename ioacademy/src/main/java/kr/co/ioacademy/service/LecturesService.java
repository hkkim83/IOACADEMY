package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.LecturesDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LecturesService extends BaseService {
  private SMSService smsService;
	private LecturesDAO lecturesDAO;
  @Autowired
  public void setSMSService(SMSService smsService) {
    this.smsService = smsService;
  }
	@Autowired
	public void setLecturesDAO(LecturesDAO lecturesDAO) {
		this.lecturesDAO = lecturesDAO;
	}
	
  /**
   * 강좌 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return lecturesDAO.selectList(params);
  }
  
  /**
   * 강좌 조회
   * 
   * @param params
   * @return
   */
  public Map<String, String> selectLecture(Map<String, String> params) {
    return lecturesDAO.selectLecture(params);
  }
  
  /**
   * 강좌 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(Map<String, String> params) {
    String status    = params.get("status");
    String lecStatus = params.get("lec_status"); // 강좌상태
    int ret = 0;
    if("delete".equals(status)) {
      ret += lecturesDAO.delete(params);
    } else if("insert".equals(status)) {
      ret += lecturesDAO.insert(params);
      // 과정이 개설되면 찜한 사용자들에게 메시지 발송
      params.put("sms_kind"     , "04");
      params.put("sms_kind_name", "찜예약");  
      smsService.sendSMS(params);    
    } else {
      // 개강일 경우
      if("02".equals(lecStatus)) {
        params.put("sms_kind"     , "05");
        params.put("sms_kind_name", "개강확정");
        smsService.sendSMS(params);
      } 
      // 폐강일 경우
      else if("99".equals(lecStatus)) {
        params.put("sms_kind"     , "06");
        params.put("sms_kind_name", "폐강");   
        smsService.sendSMS(params);
      }
      ret += lecturesDAO.update(params);
    }
    return ret;
  }
}