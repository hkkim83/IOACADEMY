package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class CommonDAO extends BaseDAO {
  
  /**
   * 현재일시 조회
   * @param groupCode
   * @return
   */
  public Map<String, String> selectNow() {
    return getSqlSession().selectOne("sql.common.selectNow");
  }
  
	/**
	 * 공통코드 리스트 조회
	 * @param groupCode
	 * @return
	 */
	public List<Map<String, String>> selectList(Map<String, String> params) {
		return getSqlSession().selectList("sql.common.selectList", params);
	}
	
	/**
	 * 공통코드 등록
	 * @param param
	 */
	public void insert(Map<String, String> param) {
		getSqlSession().insert("sql.common.insert", param);
	}

	/**
	 * 공통코드 수정
	 * @param param
	 */
	public void update(Map<String, String> param) {
		getSqlSession().update("sql.common.update", param);
	}
	
	/**
	 * 공통코드 삭제
	 * @param param
	 */
	public void delete(Map<String, String> param) {
		getSqlSession().delete("sql.common.delete", param);
	}
}