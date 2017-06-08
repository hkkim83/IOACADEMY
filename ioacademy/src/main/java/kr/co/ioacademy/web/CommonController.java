package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.service.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController extends BaseController {

	private CommonService commonService;

	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	 /**
   * 현재일시 조회
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/common/now")
  @ResponseBody
  public Map<String, Object> now(HttpServletRequest request, HttpServletResponse response) {
    Map<String, String> currentMap = commonService.selectNow();
    Map<String, Object> map = success();
    map.put("map" , currentMap);   
    return map;
  }
  
	/**
	 * 공통코드 리스트 조회
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/common/codeList")
	@ResponseBody
	public Map<String, Object> codeList(@RequestParam Map<String, String> params, HttpSession session) {
		List<Map<String, String>> list = commonService.selectCodeList(params);
		Map<String, Object> map = success();
    map.put("list" , list);		
		return map;
	}

	@RequestMapping(value = "/process")
  public ModelAndView process(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("/user/process");
    return modelAndView;
  } 

  @RequestMapping(value = "/company")
  public ModelAndView company(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("/user/company");
    return modelAndView;
  } 

  @RequestMapping(value = "/terms")
  public ModelAndView terms(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("/user/terms");
    return modelAndView;
  }	

  @RequestMapping(value = "/privacy")
  public ModelAndView privacy(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("/user/privacy");
    return modelAndView;
  } 

  @RequestMapping(value = "/policy")
  public ModelAndView policy(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("/user/policy");
    return modelAndView;
  }
}