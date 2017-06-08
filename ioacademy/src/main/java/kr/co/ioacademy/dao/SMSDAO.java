package kr.co.ioacademy.dao;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseDAO;

import org.springframework.stereotype.Repository;

@Repository
public class SMSDAO extends BaseDAO {
  
  /**
   * SMS 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectList(Map<String, String> params) {
    return getSqlSession().selectList("sql.sms.selectList", params);
  }
  
  /**
   * SMS 등록
   * 
   * @param params
   * @return
   */
  public int insert(Map<String, String> params) {
    logger.info("SMSDAO:::::::::::::::::::::::::");
    return getSqlSession().insert("sql.sms.insert", params);
  }
  
}