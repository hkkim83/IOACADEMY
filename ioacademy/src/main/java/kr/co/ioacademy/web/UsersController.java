package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.service.UsersService;
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
public class UsersController extends BaseController {
	
	private UsersService usersService;
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

  /**
   * 회원관리 화면
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/users")
	public ModelAndView view(@RequestParam Map<String, Object> params, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("admin/users");
		List<Map<String, String>> userList = usersService.selectUserList(params);
		modelAndView.addObject("list", userList); 
    
		return modelAndView;
	}

  /**
   * 회원정보 저장
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public Map<String, Object> delete(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session) {
    super.addLoginId(session, params);
    params.put("id", id);
    params.put("status", "delete");
    logger.info("delete params:::"+params);
    
    int ret = usersService.save(params);   
    if(ret < 1) return error();
    return success();
  }
  
  /**
   * 회원정보 저장
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> save(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session) {
    super.addLoginId(session, params);       // 아이디
    params.put("id", id);
    params.put("status", "update");
    logger.info("save params:::"+params);
    
    int ret = usersService.save(params);
    if(ret < 1) return error();
    return success();
  }  
  
  /**
   * 회원정보 저장
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public Map<String, Object> update(@PathVariable(value = "id") String id, @RequestBody String json, @RequestParam Map<String, String> params, HttpSession session) {
    StringUtil.stringToMap(params, json);
    super.addLoginId(session, params);       // 아이디
    params.put("id", id);
    params.put("status", "update");
    logger.info("update params:::"+params);
    
    int ret = usersService.save(params);
    if(ret < 1) return error();
    return success();
  }
}


