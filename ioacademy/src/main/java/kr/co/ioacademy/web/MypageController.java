package kr.co.ioacademy.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.dto.User;
import kr.co.ioacademy.service.ApplicationsService;
import kr.co.ioacademy.service.BookingsService;
import kr.co.ioacademy.service.UsersService;
import kr.co.ioacademy.upload.ApplicationsUpload;
import kr.co.ioacademy.upload.Upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MypageController extends BaseController {
  
  @Value("${upload.dir}")  private String uploadDir;
  
  private UsersService usersService;
  private ApplicationsService applicationsService;
  private BookingsService bookingsService;
  
  @Autowired
  public void setUsersService(UsersService usersService) {
    this.usersService = usersService;
  }
  @Autowired
  public void setApplicationsService(ApplicationsService applicationsService) {
    this.applicationsService = applicationsService;
  }
  @Autowired
  public void setBookingsService(BookingsService bookingsService) {
    this.bookingsService = bookingsService;
  }
  
  /**
   * 마이페이지 화면
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/mypage")
  public ModelAndView view(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, String> params, HttpSession session) {
    super.addId(session, params);
    ModelAndView modelAndView = new ModelAndView("user/mypage");
    List<Map<String, String>> applicationList = applicationsService.selectList(params);
    List<Map<String, String>> bookingList     = bookingsService.selectList(params);
    
    modelAndView.addObject("applicationList", applicationList);
    modelAndView.addObject("bookingList", bookingList);
    return modelAndView;
  }
  
  /**
   * 회원 정보 업데이트
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/mypage", method = RequestMethod.POST)
  @ResponseBody 
  public ModelAndView save(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);
    super.addId(session, params);
    usersService.updateMypage(params);
    // 탈퇴가 아니면 세션 업데이트
    User user = usersService.selectLoginUser(params);
    
    if(user == null) {
      redirectAttr.addFlashAttribute("message", "로그인이 필요한 서비스입니다.");
      return new ModelAndView("redirect:/");
    }
    
    super.setLoginUser(session, user);
    redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
    return new ModelAndView("redirect:/mypage");
  }

  /**
   * 비밀번호 변경 
   * @param params
   * @param session
   * @param redirectAttr
   * @return
   */
  @RequestMapping(value = "/mypage/update", method = RequestMethod.POST)
  @ResponseBody 
  public ModelAndView update(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);
    super.addId(session, params);
    params.put("status", "update");
    
    Map<String, String> map = new HashMap<String, String>();
    map.put("email", super.getLoginEmail(session));
    map.put("password", params.get("cur_password"));
    
    User user = usersService.selectLoginUser(map);
    if(user == null) {
      redirectAttr.addFlashAttribute("cur_message", "현재 비밀번호가 옳바르지 않습니다.");
    } else {
      String cur_password = params.get("cur_password");
      String password = params.get("password");
      String re_password = params.get("re_password");
      if(cur_password.equals(password))
        redirectAttr.addFlashAttribute("new_message", "현재 비밀번호와 새 비밀번호가 같습니다.");
      else if(!re_password.equals(password))
        redirectAttr.addFlashAttribute("re_message", "새 비밀번호와 같지 않습니다.");
      else {
        usersService.save(params);
        redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
      }
    }
    return new ModelAndView("redirect:/mypage");
  }
  
  /**
   * 회원탈퇴
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/mypage/delete", method = RequestMethod.POST)
  @ResponseBody 
  public ModelAndView delete(@RequestParam Map<String, String> params, HttpSession session) {
    super.addId(session, params);
    params.put("status", "delete");
    
    int ret = usersService.save(params);
    if(ret < 1) return new ModelAndView("redirect:/mypage");
    
    return new ModelAndView("redirect:/signout");
  }
  
  /**
   * 환급 서류 업로드 
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/mypage/apply/{id}/{lec_id}", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadFile(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addId(session, params);            // 아이디
    super.addLoginId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    
    Upload upload = new ApplicationsUpload(uploadDir, params, super.getLoginUser(session));
    params = upload.go();

    int ret = applicationsService.save(params);
    if(ret < 0) return error();
    return success();
  }
  
  
  /**
   * 수강 취소 
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/mypage/apply/{id}/{lec_id}", method=RequestMethod.PUT)
  @ResponseBody
  public Map<String, Object> apply(@PathVariable(value = "id") String id, @PathVariable(value = "lec_id") String lec_id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addId(session, params);            // 아이디
    super.addLoginId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("lec_id", lec_id);
    params.put("status", "delete");
    
    logger.info("apply params:::"+params);
    int ret = applicationsService.save(params);
    if(ret < 0) return error();
    return success();
  }

  /**
   * 찜예약 취소 
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/mypage/book/{id}/{lec_id}", method=RequestMethod.PUT)
  @ResponseBody
  public Map<String, Object> book(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("status", "delete");
    
    logger.info("book params:::"+params);
    int ret = bookingsService.save(params);
    if(ret < 0) return error();
    return success();
  }
}

