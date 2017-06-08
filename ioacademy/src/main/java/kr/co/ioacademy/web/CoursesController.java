package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.dto.User;
import kr.co.ioacademy.service.ApplicationsService;
import kr.co.ioacademy.service.BoardService;
import kr.co.ioacademy.service.BookingsService;
import kr.co.ioacademy.service.CommonService;
import kr.co.ioacademy.service.CurriculumsService;
import kr.co.ioacademy.service.LecturesService;
import kr.co.ioacademy.service.UsersService;
import kr.co.ioacademy.util.StringUtil;

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
public class CoursesController extends BaseController {

  @Value("${upload.dir}")  private String uploadDir;
  
  private UsersService usersService;
  private CurriculumsService curriculumsService;
  private LecturesService lecturesService;
  private CommonService commonService;
  private BoardService boardService;
  private ApplicationsService applicationsService;
  private BookingsService bookingsService;

  @Autowired
  public void setUsersService(UsersService usersService) {
    this.usersService = usersService;
  }
  
  @Autowired
  public void setCurriculumsService(CurriculumsService curriculumsService) {
    this.curriculumsService = curriculumsService;
  }
  @Autowired
  public void setLecturesService(LecturesService lecturesService) {
    this.lecturesService = lecturesService;
  }
  @Autowired
  public void setCommonService(CommonService commonService) {
    this.commonService = commonService;
  }
  @Autowired
  public void setBoardService(BoardService boardService) {
    this.boardService = boardService;
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
   * 과정안내 화면
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/courses")
	public ModelAndView courses(@RequestParam Map<String, String> params, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("user/courses");
    params.put("curri_grade", "01");
    List<Map<String, String>> list1 = curriculumsService.selectCourseList(params);
    params.put("curri_grade", "02");
    List<Map<String, String>> list2 = curriculumsService.selectCourseList(params);
    params.put("curri_grade", "03");
    List<Map<String, String>> list3 = curriculumsService.selectCourseList(params);
    modelAndView.addObject("list1" , list1);
    modelAndView.addObject("list2" , list2);
    modelAndView.addObject("list3" , list3);
		return modelAndView;
	}
  
  /**
   * 과정안내 상세 화면
   * @param request
   * @param response
   * @return
   */  
  @RequestMapping(value = "/courses/{id}")
  public ModelAndView courses(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params) {
    params.put("curri_id", id);
    params.put("group_code", "004");
    params.put("lec_flag", "1");
    
    Map<String, Object> resultMap      = curriculumsService.selectCurriculum(params);
    List<Map<String, String>> lecList  = lecturesService.selectList(params);
    List<Map<String, String>> codeList = commonService.selectCodeList(params);
    List<Map<String, String>> boardList = boardService.selectList(params);
    
    String[] keyArr =  {"question", "reply"};
    // 개행을 br태그로 변환
    StringUtil.convertToMap(resultMap, "curri_desc", "sections");
    StringUtil.convertToBr(resultMap, "curri_info");
    StringUtil.convertToSquare(resultMap, "curri_info");
    StringUtil.convertToBr(resultMap, "curri_target", ".");
    StringUtil.convertToBr(resultMap, "edu_environment", ",");
    StringUtil.convertToBrList(boardList, keyArr);
    // 코드를 명으로 변환
    StringUtil.convertToCommName(resultMap, codeList, "pre_course");
    
    ModelAndView modelAndView = new ModelAndView("user/course");
    modelAndView.addObject("val"  , resultMap);
    modelAndView.addObject("list" , lecList);
    modelAndView.addObject("qnaList", boardList);
    return modelAndView;
  }
  
  /**
   * QnA 글쓰기
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/courses/{id}/write", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView write(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);  // 로그인 아이디
    super.addId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("status", "insert");

    logger.info("write params:::"+params);
    boardService.save(params);   
    
    redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
    
    return new ModelAndView("redirect:/courses/{id}");
  } 
  
  /**
   * QnA 글쓰기
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/courses/{id}/modify/{board_id}", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView modify(@PathVariable(value = "id") String id, @PathVariable(value = "board_id") String board_id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);  // 로그인 아이디
    super.addId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("board_id", board_id);

    logger.info("modify params:::"+params);
    boardService.save(params);
    
    redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
    
    return new ModelAndView("redirect:/courses/{id}");
  }
  
  /**
   * QnA 글쓰기
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/courses/{id}/delete/{board_id}", method=RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> delete(@PathVariable(value = "id") String id, @PathVariable(value = "board_id") String board_id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);  // 로그인 아이디
    super.addId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("board_id", board_id);
    params.put("status", "delete");

    logger.info("delete params:::"+params);

    int ret = boardService.save(params);
    if(ret < 1) return error();
    return success();
  }
 
    
  /**
   * 수강신청 화면
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/courses/{id}/apply")
  public ModelAndView apply(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params) {
    ModelAndView modelAndView = new ModelAndView("user/application");
    params.put("curri_id", id);
    params.put("lec_flag", "1");
    params.put("group_code", "011");
    
    List<Map<String, String>> lecList = lecturesService.selectList(params);
    List<Map<String, String>> appList = commonService.selectCodeList(params); 
    
    StringUtil.convertToCommCode(lecList.get(0), appList, "application_type");
    
    modelAndView.addObject("val"    , lecList.get(0));
    modelAndView.addObject("appList", appList);

    return modelAndView;
  }
  
  /**
   * 찜예약 화면
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/courses/{id}/book")
  public ModelAndView book(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session) {
    ModelAndView modelAndView = new ModelAndView("user/book");
    params.put("curri_id", id);
    
    List<Map<String, String>> curriculumList  = curriculumsService.selectCurriculumList(params);
    modelAndView.addObject("val" , curriculumList.get(0));

    return modelAndView;
  }
  
  
  /**
   * 수강신청하기
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/courses/{id}/apply", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView apply(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);  // 로그인 아이디
    super.addId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("status", "insert");

    logger.info("apply params:::"+params);
    
    // 수강신청 중복 체크
    if(applicationsService.checkID(params))
      redirectAttr.addFlashAttribute("error_message", "이미 수강신청을 하셨습니다.");
    else {
      applicationsService.save(params);
      // 수강신청이면 세션 업데이트
      User user = usersService.selectLoginUser(params);
      
      if(user == null) {
        return new ModelAndView("redirect:/signin");
      }
  
      super.setLoginUser(session, user);  
      redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
    }
    return new ModelAndView("redirect:/courses/{id}/apply");
  }
   
  /**
   * 찜예약하기 
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value = "/courses/{id}/book", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView book(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    super.addLoginId(session, params);  // 로그인 아이디
    super.addId(session, params);       // 아이디
    params.put("curri_id", id);
    params.put("status", "insert");

    logger.info("book params:::"+params);
    
    // 찜예약 중복 체크
    if(bookingsService.checkID(params))
      redirectAttr.addFlashAttribute("error_message", "이미 찜예약을 하셨습니다.");
    else {
      bookingsService.save(params);
      // 찜예약이면 세션 업데이트
      User user = usersService.selectLoginUser(params);
      
      if(user == null) {
        return new ModelAndView("redirect:/signin");
      }
  
      super.setLoginUser(session, user);  
      redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
    }
    return new ModelAndView("redirect:/courses/{id}/book");
  }
}