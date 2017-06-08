package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class ApplicationsDAO extends BaseDAO {
  
  /**
   * 수강신청 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return getSqlSession().selectList("sql.applications.selectList", params);
  }
  
  /**
   * SMS받을 수강신청 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectSMSList(Map<String, String> params) {
    return getSqlSession().selectList("sql.applications.selectSMSList", params);
  }
  
  /**
   * 수강신청 아이디 중복 조회
   * 
   * @param params
   * @return
   */
  public boolean checkID(Map<String, String> params) {
    Map<String, String> map = getSqlSession().selectOne("sql.applications.checkID", params);
    return map != null ? true : false;    
  }
  
  /**
   * 수강신청 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    return getSqlSession().insert("sql.applications.insert", params);
  }
  
  /**
   * 수강신청 수정
   * 
   * @param params
   * @return
   */
  public int update(Map<String, String> params) {
    return getSqlSession().update("sql.applications.update", params);
  }
  
  /**
   * 수강신청 삭제
   * 
   * @param params
   * @return
   */
  public int delete(Map<String, String> params) {
    return getSqlSession().delete("sql.applications.delete", params);
  }
}