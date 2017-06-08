package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.BoardDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService extends BaseService {
	private BoardDAO boardDAO;
  private SMSService smsService;
  
	@Autowired
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	
  @Autowired
  public void setSMSService(SMSService smsService) {
    this.smsService = smsService;
  }
	
  /**
   * 과정 QnA 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return boardDAO.selectList(params);
  }
  
  /**
   * 과정 QnA 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(Map<String, String> params) {
    String status = params.get("status");
    int ret = 0;
    if("delete".equals(status)) {
      ret += boardDAO.delete(params);
    } else if("insert".equals(status)) {
      ret += boardDAO.insert(params);
      params.put("sms_kind"     , "07");
      params.put("sms_kind_name", "QnA등록");
      smsService.sendSMS(params);
    } else {
      ret += boardDAO.update(params);
    }
    return ret;
  }
}