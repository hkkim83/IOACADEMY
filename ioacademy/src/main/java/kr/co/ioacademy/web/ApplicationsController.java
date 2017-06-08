package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.service.ApplicationsService;
import kr.co.ioacademy.service.CommonService;
import kr.co.ioacademy.service.CurriculumsService;
import kr.co.ioacademy.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicationsController extends BaseController {

  private ApplicationsService applicationsService;
  private CurriculumsService curriculumsService;
  private CommonService commonService;

  @Autowired
  public void setCurriculumsService(CurriculumsService curriculumsService) {
    this.curriculumsService = curriculumsService;
  }
  @Autowired
  public void setApplicationsService(ApplicationsService applicationsService) {
    this.applicationsService = applicationsService;
  }
  @Autowired
  public void setCommonService(CommonService commonService) {
    this.commonService = commonService;
  }
  
  /**
   * 수강신청 관리 화면
   * @param request
   * @param response
   * @return
   */
	@RequestMapping(value = "/applications")
	public ModelAndView view(@RequestParam Map<String, String> params, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("admin/applications");
    params.put("lec_flag", "2");
    params.put("group_code", "201");
    List<Map<String, String>> curriculumList = curriculumsService.selectCurriculumList(params);
    List<Map<String, String>> appList = applicationsService.selectList(params);
    List<Map<String, String>> codeList = commonService.selectCodeList(params);
    modelAndView.addObject("list" , appList);
    modelAndView.addObject("curriculumList" , curriculumList);
    modelAndView.addObject("codeList" , codeList);
		return modelAndView;
	}
	
  /**
   * 과정목록 삭제
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/applications/{id}/{lec_id}", method = RequestMethod.DELETE)
  @ResponseBody
  public Map<String, Object> delete(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestBody String json, @RequestParam Map<String, String> params, HttpSession session) {
    StringUtil.stringToMap(params, json);
    super.addLoginId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", "delete");

    logger.info("delete params:::"+params);
    int ret = applicationsService.save(params);
    if(ret < 1) return error();
    return success();
  }
  
  /**
   * 과정목록 저장
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/applications/{id}/{lec_id}", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> save(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestParam Map<String, String> params, HttpSession session) {
    super.addLoginId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", "update");
    logger.info("save params:::"+params);
    
    int ret = applicationsService.save(params);
    if(ret < 1) return error();
    return success();
  }
  
  /**
   * 과정목록 입금여부 저장
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/applications/{id}/{lec_id}", method = RequestMethod.PUT)
  @ResponseBody
  public Map<String, Object> update(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestBody String json, @RequestParam Map<String, String> params, HttpSession session) {
    StringUtil.stringToMap(params, json);
    super.addLoginId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", "update");
    
    logger.info("update params:::"+params);
    
    int ret = applicationsService.save(params);
    if(ret < 1) return error();
    return success();
  }
}
