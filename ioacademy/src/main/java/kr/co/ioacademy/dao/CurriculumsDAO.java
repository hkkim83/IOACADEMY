package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class CurriculumsDAO extends BaseDAO {
  
  /**
   * 과정 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectCurriculumList(Map<String, String> params) {
    return getSqlSession().selectList("sql.curriculums.selectList", params);
  }
  
  /**
   * 메인화면 과정 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectMainList(Map<String, String> params) {
    return getSqlSession().selectList("sql.curriculums.selectMainList", params);
  }

  /**
   * 과정안내 화면 과정 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectCourseList(Map<String, String> params) {
    return getSqlSession().selectList("sql.curriculums.selectCourseList", params);
  }

  /**
   * 과정 조회
   * 
   * @param params
   * @return
   */
  public Map<String, Object> selectCurriculum(Map<String, String> params) {
    return getSqlSession().selectOne("sql.curriculums.selectList", params);
  }
  
  /**
   * 과정 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    return getSqlSession().insert("sql.curriculums.insert", params);
  }
  
  /**
   * 과정 수정
   * 
   * @param params
   * @return
   */
  public int update(Map<String, String> params) {
    return getSqlSession().update("sql.curriculums.update", params);
  }
  
  /**
   * 과정 삭제
   * 
   * @param params
   * @return
   */
  public int delete(Map<String, String> params) {
    return getSqlSession().delete("sql.curriculums.delete", params);
  }
  
  /**
   * 과정 복사
   * 
   * @param params
   * @return
   */
  public int copy(Map<String, String> params) {
    return getSqlSession().insert("sql.curriculums.copy", params);
  }
  
}