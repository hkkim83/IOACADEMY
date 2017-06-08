package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.service.BookingsService;
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
public class BookingsController extends BaseController {

  private CurriculumsService curriculumsService;
  private BookingsService bookingsService;

  @Autowired
  public void setCurriculumsService(CurriculumsService curriculumsService) {
    this.curriculumsService = curriculumsService;
  }
  @Autowired
  public void setBookingsService(BookingsService bookingsService) {
    this.bookingsService = bookingsService;
  }
  
  /**
   * 수강예약 관리 화면
   * @param request
   * @param response
   * @return
   */
	@RequestMapping(value = "/bookings")
	public ModelAndView view(@RequestParam Map<String, String> params, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("admin/bookings");
		List<Map<String, String>> curriculumList = curriculumsService.selectCurriculumList(params);
    List<Map<String, String>> list = bookingsService.selectList(params);
    modelAndView.addObject("curriculumList" , curriculumList);
    modelAndView.addObject("list" , list);
		return modelAndView;
	}
	
	 
  /**
   * 수강예약 삭제
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/bookings/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public Map<String, Object> delete(@PathVariable(value = "id") String id, @RequestBody String json, @RequestParam Map<String, String> params, HttpSession session) {
    StringUtil.stringToMap(params, json);
    params.put("curri_id", id);
    params.put("status", "delete");

    logger.info("delete params:::"+params);
    int ret = bookingsService.save(params);
    if(ret < 1) return error();
    return success();
  }
}
