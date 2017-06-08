package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class LecturesDAO extends BaseDAO {
  
  /**
   * 강좌 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return getSqlSession().selectList("sql.lectures.selectList", params);
  }
  
  /**
   * 강좌 조회
   * 
   * @param params
   * @return
   */
  public Map<String, String> selectLecture(Map<String, String> params) {
    return getSqlSession().selectOne("sql.lectures.select", params);
  }
  
  /**
   * 강좌 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    return getSqlSession().insert("sql.lectures.insert", params);
  }
  
  /**
   * 강좌 수정
   * 
   * @param params
   * @return
   */
  public int update(Map<String, String> params) {
    return getSqlSession().update("sql.lectures.update", params);
  }
  
  /**
   * 강좌 삭제
   * 
   * @param params
   * @return
   */
  public int delete(Map<String, String> params) {
    return getSqlSession().delete("sql.lectures.delete", params);
  }
  
  /**
   * 강좌 복사
   * 
   * @param params
   * @return
   */
  public int copy(Map<String, String> params) {
    return getSqlSession().insert("sql.lectures.copy", params);
  }
  
}