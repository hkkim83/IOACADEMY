package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.BookingsDAO;
import kr.co.ioacademy.dao.UsersDAO;
import kr.co.ioacademy.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingsService extends BaseService {
	private BookingsDAO bookingsDAO;
	private UsersDAO usersDAO;
  
	@Autowired
	public void setBookingsDAO(BookingsDAO bookingsDAO) {
		this.bookingsDAO = bookingsDAO;
	}

  @Autowired
  public void setUsersDAO(UsersDAO usersDAO) {
    this.usersDAO = usersDAO;
  }
  
  /**
   * 찜 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return bookingsDAO.selectList(params);
  }
  
  /**
   * 찜 아이디 중복 조회
   * 
   * @param params
   * @return
   */
  public boolean checkID(Map<String, String> params) {
    return bookingsDAO.checkID(params);
  }
  
  /**
   * 찜 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(Map<String, String> params) {
    String status = params.get("status");
    int ret = 0;
    if("delete".equals(status)) {
      ret += bookingsDAO.delete(params);
    } else if("insert".equals(status)) {
      String newTelNo = StringUtil.makeTelNo(params.get("tel_no1"),params.get("tel_no2"),params.get("tel_no3"));
      // 기존의 전화번호가 아니라면 사용자 전화번호 갱신
      if(!newTelNo.equals(params.get("tel_no"))) {
        params.put("tel_no", newTelNo);
        ret += usersDAO.update(params);
      }
      ret += bookingsDAO.insert(params);
    } else {
      ret += bookingsDAO.update(params);
    }
    return ret;
  }
}