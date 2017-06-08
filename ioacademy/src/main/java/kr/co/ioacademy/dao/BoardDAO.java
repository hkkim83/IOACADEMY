package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO extends BaseDAO {
  
  /**
   * 과정 QnA 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return getSqlSession().selectList("sql.board.selectList", params);
  }
  
  /**
   * 과정 QnA 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectSMSList(Map<String, String> params) {
    return getSqlSession().selectList("sql.board.selectSMSList", params);
  }
  
  /**
   * 과정 QnA 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    return getSqlSession().insert("sql.board.insert", params);
  }
  
  /**
   * 과정 QnA 수정
   * 
   * @param params
   * @return
   */
  public int update(Map<String, String> params) {
    return getSqlSession().update("sql.board.update", params);
  }
  
  /**
   * 과정 QnA 삭제
   * 
   * @param params
   * @return
   */
  public int delete(Map<String, String> params) {
    return getSqlSession().delete("sql.board.delete", params);
  }
}