package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class BookingsDAO extends BaseDAO {
  
  /**
   * 찜 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return getSqlSession().selectList("sql.bookings.selectList", params);
  }
  
  
  /**
   * SMS받을 찜 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectSMSList(Map<String, String> params) {
    return getSqlSession().selectList("sql.bookings.selectSMSList", params);
  }
  
  /**
   * 찜 아이디 중복 조회
   * 
   * @param params
   * @return
   */
  public boolean checkID(Map<String, String> params) {
    Map<String, String> map = getSqlSession().selectOne("sql.bookings.checkID", params);
    return map != null ? true : false;    
  }
  
  /**
   * 찜 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    return getSqlSession().insert("sql.bookings.insert", params);
  }
  
  /**
   * 찜 수정
   * 
   * @param params
   * @return
   */
  public int update(Map<String, String> params) {
    return getSqlSession().update("sql.bookings.update", params);
  }
  
  /**
   * 찜 삭제
   * 
   * @param params
   * @return
   */
  public int delete(Map<String, String> params) {
    return getSqlSession().delete("sql.bookings.delete", params);
  }
}