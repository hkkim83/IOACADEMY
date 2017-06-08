package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.UsersDAO;
import kr.co.ioacademy.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersService extends BaseService {
	private UsersDAO usersDAO;
	
	@Autowired
	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	
	/**
	 * 로그인 사용자 조회
	 * @param param
	 * @return
	 */
	public User selectLoginUser(Map<String, String> params) {
		return usersDAO.selectLoginUser(params);
	}
	
  /**
   * 사용자 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectUserList(Map<String, Object> params) {
    return usersDAO.selectUserList(params);
  }
  
  /**
   * 중복 사용자 체크
   * 
   * @param params
   * @return
   */
  public boolean checkID(Map<String, String> params) {
    return usersDAO.checkID(params);
  }
  
  /**
   * 사용자 정보 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(Map<String, String> params) {
    String status = params.get("status");
    int ret = 0;
    if("delete".equals(status)) {
      ret += usersDAO.delete(params);
    } else if("insert".equals(status)) {
      ret += usersDAO.insert(params);
    } else {
      ret += usersDAO.update(params);
    }
    return ret;
  }
  
  /**
   * 사용자 비밀번호 수정 
   * 
   * @param params
   * @return
   */
  @Transactional
  public int updateMypage(Map<String, String> params) {
    return usersDAO.updateMypage(params);
  }
  
  /**
   * 사용자 비밀번호 수정 
   * 
   * @param params
   * @return
   */
  @Transactional
  public int updatePassword(Map<String, String> params) {
	return usersDAO.updatePassword(params);
  }
}



