package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.ApplicationsDAO;
import kr.co.ioacademy.dao.BookingsDAO;
import kr.co.ioacademy.dao.BoardDAO;
import kr.co.ioacademy.dao.SMSDAO;
import kr.co.ioacademy.dao.UsersDAO;
import kr.co.ioacademy.sms.ApplicationSMS;
import kr.co.ioacademy.sms.BookingSMS;
import kr.co.ioacademy.sms.BoardSMS;
import kr.co.ioacademy.sms.LectureSMS;
import kr.co.ioacademy.sms.SMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SMSService extends BaseService {

  @Value("${api.id}")  private String apiId;
  @Value("${api.key}") private String apiKey;
  @Value("${api.num}") private String apiNum;
  
  private UsersDAO usersDAO;
  private BookingsDAO bookingsDAO;
  private ApplicationsDAO applicationsDAO;
  private BoardDAO boardDAO;
  private SMSDAO smsDAO;
  
  @Autowired
  public void setUsersDAO(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  } 
  @Autowired
  public void setBookingsDAO(BookingsDAO bookingsDAO) {
    this.bookingsDAO = bookingsDAO;
  } 
  @Autowired
  public void setApplicationsDAO(ApplicationsDAO applicationsDAO) {
    this.applicationsDAO = applicationsDAO;
  } 
  @Autowired
  public void setBoardDAO(BoardDAO boardDAO) {
    this.boardDAO = boardDAO;
  } 
	@Autowired
	public void setSMSDAO(SMSDAO smsDAO) {
		this.smsDAO = smsDAO;
	}
	
  /**
   * SMS 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return smsDAO.selectList(params);
  }
  
  /**
   * SMS 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(List<Map<String, String>> list) {
    int ret = 0;
    for(Map<String, String> map : list) {
      ret += smsDAO.insert(map);
    }
    return ret;
  }
  
  /**
   * SMS 보내기
   * @param params
   * @return
   */
  public List<Map<String, String>> sendSMS(Map<String, String> params) {
    String smsKind = params.get("sms_kind");
    params.put("api_id" , apiId);
    params.put("api_key", apiKey);
    params.put("api_num", apiNum);
    logger.info("params:::"+params);
    List<Map<String, String>> list = null;
    SMS sms = null;
    // 수강신청(01), 수강취소(02)
    if("01".equals(smsKind) || "02".equals(smsKind)) {
      list = usersDAO.selectSMSList(params);
      sms = new ApplicationSMS(smsDAO, params, list);
    }
    // 개강확정(05), 폐강확정(06)
    else if("05".equals(smsKind) || "06".equals(smsKind)) {
      list = applicationsDAO.selectSMSList(params);
      sms = new LectureSMS(smsDAO, params, list);
    }
    // QnA등록(07)
    else if("07".equals(smsKind)) {
      list = boardDAO.selectSMSList(params);
      sms = new BoardSMS(smsDAO, params, list);
    }
    // 찜예약(04)
    else {
      list = bookingsDAO.selectSMSList(params);
      sms = new BookingSMS(smsDAO, params, list);
    }
    logger.info(list);
    sms.sendSMS();
    return list;
  }
}