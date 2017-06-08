package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.ApplicationsDAO;
import kr.co.ioacademy.dao.UsersDAO;
import kr.co.ioacademy.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationsService extends BaseService {
  private SMSService smsService;
  private ApplicationsDAO applicationsDAO;
  private UsersDAO usersDAO;
  
  @Autowired
  public void setSMSService(SMSService smsService) {
    this.smsService = smsService;
  }
	@Autowired
	public void setApplicationsDAO(ApplicationsDAO applicationsDAO) {
		this.applicationsDAO = applicationsDAO;
	}
	@Autowired
	public void setUsersDAO(UsersDAO usersDAO) {
	  this.usersDAO = usersDAO;
	}	
	
  /**
   * 수강신청 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return applicationsDAO.selectList(params);
  }
  

  /**
   * 수강신청 아이디 중복 조회
   * 
   * @param params
   * @return
   */
  public boolean checkID(Map<String, String> params) {
    return applicationsDAO.checkID(params);
  }
  
  /**
   * 수강신청 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(Map<String, String> params) {
    String status = params.get("status");
    int ret = 0;
    if("delete".equals(status)) {
      // 수강신청시 관리자에가 sms메시지 보냄
      params.put("sms_kind"     , "02");
      params.put("sms_kind_name", "수강취소");
      smsService.sendSMS(params);
      // db삭제할 것이기 때문에 sms보내고 삭제
      ret += applicationsDAO.delete(params);
    } else if("insert".equals(status)) {
      String newTelNo = StringUtil.makeTelNo(params.get("tel_no1"),params.get("tel_no2"),params.get("tel_no3"));
      // 기존의 전화번호가 아니라면 사용자 전화번호 갱신
      if(!newTelNo.equals(params.get("tel_no"))) {
        params.put("tel_no", newTelNo);
        ret += usersDAO.update(params);
      }
      ret += applicationsDAO.insert(params);
      // db등록후 sms보냄
      params.put("sms_kind"     , "01");
      params.put("sms_kind_name", "수강신청");
      smsService.sendSMS(params);
    } else {
      ret += applicationsDAO.update(params);
    }
    return ret;
  }
}