package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;
import kr.co.ioacademy.dto.User;

import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO extends BaseDAO {
  
  /**
   * 로그인 사용자 조회
   * 
   * @param params
   * @return
   */
  public User selectLoginUser(Map<String, String> params) {
    return getSqlSession().selectOne("sql.users.selectLoginUser", params);
  }
  
  /**
   * 사용자 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectUserList(Map<String, Object> params) {
    return getSqlSession().selectList("sql.users.selectList", params);
  }
  
  /**
   * SMS보낼 사용자 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectSMSList(Map<String, String> params) {
    return getSqlSession().selectList("sql.users.selectSMSList", params);
  }

  /**
   * 중복 사용자 체크
   * 
   * @param params
   * @return
   */
  public boolean checkID(Map<String, String> params) {
    Map<String, String> map = getSqlSession().selectOne("sql.users.checkID", params);
    return map != null ? true : false;
  }
  
  /**
   * 사용자 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    return getSqlSession().insert("sql.users.insert", params);
  }
  
  /**
   * 사용자 수정
   * 
   * @param params
   * @return
   */
  public int update(Map<String, String> params) {
    return getSqlSession().insert("sql.users.update", params);
  }
  
  /**
   * 사용자 삭제
   * 
   * @param params
   * @return
   */
  public int delete(Map<String, String> params) {
    return getSqlSession().insert("sql.users.delete", params);
  }
  
  /**
   * 사용자 정보 변경
   * 
   * @param params
   * @return
   */
  public int updateMypage(Map<String, String> params) {
    return getSqlSession().insert("sql.users.updateMypage", params);
  }
  
  /**
   * 사용자 비밀번호 변경
   * 
   * @param params
   * @return
   */
  public int updatePassword(Map<String, String> params) {
    return getSqlSession().insert("sql.users.updatePassword", params);
  }
}