package kr.co.ioacademy.service;

import java.util.List;
import java.util.Map;

import kr.co.ioacademy.base.BaseService;
import kr.co.ioacademy.dao.CommonDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommonService extends BaseService {
	private CommonDAO commonDAO;
	
	@Autowired
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	
	 /**
   * 현재일시 조회
   * @return
   */
  public Map<String, String> selectNow() {
    return commonDAO.selectNow();
  }
  
	/**
	 * 공통코드 조회
	 * @param groupCode
	 * @return
	 */
	public List<Map<String, String>> selectCodeList(Map<String, String> params) {
		return commonDAO.selectList(params);
	}
	
	@Transactional
	public void save(Map<String, Object> param) {
		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = (List<Map<String, String>>)param.get("list");
		for(Map<String, String> map : list) {
			if("delete".equals(map.get("status"))) {
				commonDAO.delete(map);
			} else if("update".equals(map.get("status"))) {
				commonDAO.update(map);
			} else if("insert".equals(map.get("status"))) {
				commonDAO.insert(map);
			}
		}
	}
}

