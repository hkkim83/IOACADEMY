package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.CurriculumsDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CurriculumsService extends BaseService {
	private CurriculumsDAO curriculumsDAO;
	
	@Autowired
	public void setCurriculumsDAO(CurriculumsDAO curriculumsDAO) {
		this.curriculumsDAO = curriculumsDAO;
	}
	
  /**
   * 과정 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectCurriculumList(Map<String, String> params) {
    return curriculumsDAO.selectCurriculumList(params);
  }
  
  /**
   * 메인화면 과정 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectMainList(Map<String, String> params) {
    return curriculumsDAO.selectMainList(params);
  }
  
  /**
   * 과정 목록 조회
   * 
   * @param params
   * @return
   */
  public List<Map<String, String>> selectCourseList(Map<String, String> params) {
    return curriculumsDAO.selectCourseList(params);
  }
  
  /**
   * 과정 조회
   * 
   * @param params
   * @return
   */
  public Map<String, Object> selectCurriculum(Map<String, String> params) {
    return curriculumsDAO.selectCurriculum(params);
  }
  
  /**
   * 과정 저장
   * 
   * @param params
   * @return
   */
  @Transactional
  public int save(Map<String, String> params) {
    String status = params.get("status");
    int ret = 0;
    if("delete".equals(status)) {
      ret += curriculumsDAO.delete(params);
    } else if("insert".equals(status)) {
      ret += curriculumsDAO.insert(params);
    } else if("copy".equals(status)) {
      ret += curriculumsDAO.copy(params);
    } else {
      ret += curriculumsDAO.update(params);
    }
    return ret;
  }
}