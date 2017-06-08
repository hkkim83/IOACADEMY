package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.service.CommonService;
import kr.co.ioacademy.service.LecturesService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LecturesController extends BaseController {

  private LecturesService lecturesService;
  private CommonService commonService;

  @Autowired
  public void setLecturesService(LecturesService lecturesService) {
    this.lecturesService = lecturesService;
  }
  
  @Autowired
  public void setCommonService(CommonService commonService) {
    this.commonService = commonService;
  }
  
  /**
   * 강좌개설관리 화면
   * @param request
   * @param response
   * @return
   */
	@RequestMapping(value = "/lectures")
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> params) {
		ModelAndView modelAndView = new ModelAndView("admin/lectures");
    params.put("lec_flag", "1");
    List<Map<String, String>> list = lecturesService.selectList(params);
    modelAndView.addObject("list" , list);
    modelAndView.addObject("params", params);

		return modelAndView;
	}
	
	/**
	 * 강좌개설등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/lectures/{id}/{lec_id}", method = RequestMethod.GET)
  public ModelAndView lectures(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestParam Map<String, String> params) {
		ModelAndView modelAndView = new ModelAndView("admin/lecture");
		params.put("curri_id", id);
		if(!lec_id.equals("new"))
		  params.put("lec_id", lec_id);
    Map<String, String> resultMap = lecturesService.selectLecture(params);
    params.put("group_code", "011");
    List<Map<String, String>> appList = commonService.selectCodeList(params);
    params.put("group_code", "012");
    List<Map<String, String>> lecKindList = commonService.selectCodeList(params);
    params.put("group_code", "013");
    List<Map<String, String>> lecTypeList = commonService.selectCodeList(params);
    
    modelAndView.addObject("val", resultMap);
    modelAndView.addObject("appList"    , appList);
    modelAndView.addObject("lecKindList", lecKindList);
    modelAndView.addObject("lecTypeList", lecTypeList);
    modelAndView.addObject("params", params);
    
		return modelAndView;
	}
 
  /**
   * 강좌 저장
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/lectures/{id}/{lec_id}", method = RequestMethod.POST)
  @ResponseBody
  public ModelAndView save(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestParam Map<String, String> params, HttpServletRequest req, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);
    String applicationType = StringUtil.ArrayToString(req.getParameterValues("application_type"));
    
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", lec_id.equals("new") ? "insert" : "update");
    params.put("application_type", applicationType);
    
    logger.info("save params:::"+params);
    lecturesService.save(params);
    
    redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");   
    return new ModelAndView("redirect:/lectures/{id}/"+params.get("lec_id"));
  } 
  
  /**
   * 강좌 삭제
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/lectures/{id}/{lec_id}", method = RequestMethod.DELETE)
  @ResponseBody
  public Map<String, Object> delete(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestParam Map<String, String> params, HttpSession session) {
    super.addLoginId(session, params);
    
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", "delete");
    
    logger.info("delete params:::"+params);
    int ret = lecturesService.save(params);
    if(ret < 1) return error();
    return success();
  } 
  
  /**
   * 강좌 상태 수정
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/lectures/{id}/{lec_id}", method = RequestMethod.PUT)
  @ResponseBody
  public Map<String, Object> modify(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestBody String json, @RequestParam Map<String, String> params, HttpSession session) {
    StringUtil.stringToMap(params, json);
    super.addLoginId(session, params);
    
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", "update");
    logger.info("modify params:::"+params);
   
    int ret = lecturesService.save(params);
    if(ret < 1) return error();
    return success();
  } 
}

