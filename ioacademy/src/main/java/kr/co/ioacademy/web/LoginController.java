package kr.co.ioacademy.web;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.dto.User;
import kr.co.ioacademy.service.CurriculumsService;
import kr.co.ioacademy.service.MailService;
import kr.co.ioacademy.service.UsersService;
import kr.co.ioacademy.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController extends BaseController {
  
	private UsersService usersService;
  private CurriculumsService curriculumsService;
  private MailService mailService;

  @Autowired
  public void setUsersService(UsersService usersService) {
    this.usersService = usersService;
  }

	@Autowired
	public void setCurriculumsService(CurriculumsService curriculumsService) {
		this.curriculumsService = curriculumsService;
	}

  @Autowired
  public void setMailService(MailService mailService) {
    this.mailService = mailService;
  }

	/**
	 * 초기화면 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/")
	public ModelAndView root(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
	  ModelAndView modelAndView = new ModelAndView("index");
		List<Map<String, String>> list   = curriculumsService.selectMainList(null);
		modelAndView.addObject("list", list);
    return modelAndView;
	}
	
	/**
	 * 초기화면 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("index");
    List<Map<String, String>> list   = curriculumsService.selectMainList(null);
    modelAndView.addObject("list", list);
		return modelAndView;
	}
	
  
  /**
   * 회원가입 화면 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/signup")
  public ModelAndView signup(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("common/signup");
    return modelAndView;
  }

  /**
   * 회원가입
   * @param params
   * @param session
   * @param redirectAttr
   * @return
   */
  @RequestMapping(value = "/signup", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView join(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    // 이메일 중복체크
    if(usersService.checkID(params)) {
      redirectAttr.addFlashAttribute("message", "이미 존재하는 이메일 주소입니다.");
      return new ModelAndView("redirect:/signup");
    }
      
    // 사용자 등록
    params.put("status", "insert");
    usersService.save(params);
    
    return login(params, session, redirectAttr);
  } 
    
  /**
   * 로그인 화면 
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/signin")
  public ModelAndView signin(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("common/signin");
    modelAndView.addObject("referer", request.getHeader("referer"));
    return modelAndView;
  }

  /**
   *로그인
   * @param params
   * @param session
   * @param redirectAttr
   * @return
   */
  @RequestMapping(value = "/signin", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView login(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    // 1. 데이터 베이스 체크
    User user = usersService.selectLoginUser(params);
    if(user == null) {
      redirectAttr.addFlashAttribute("message", "이메일 주소 또는 비밀번호가 일치하지 않습니다.");
      return new ModelAndView("redirect:/signin");
    }

    super.setLoginUser(session, user);
    String referer = StringUtil.convertURI(params.get("referer"));
    
    return new ModelAndView("redirect:"+referer);
  } 
  	
  
  /**
   * 비밀번호 찾기
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/find")
  public ModelAndView find(HttpServletRequest request, HttpServletResponse response) {
    
    ModelAndView modelAndView = new ModelAndView("common/find");
    return modelAndView;
  }

  /**
   * 비밀번호 찾기
   * @param params
   * @param session
   * @return
   */
  @RequestMapping(value="/find", method=RequestMethod.POST)
  @ResponseBody
  public ModelAndView searchFor(@RequestParam Map<String, String> params, HttpSession session, RedirectAttributes redirectAttr) {
    // 이메일 존재 여부 체크
    if(!usersService.checkID(params)) {
      redirectAttr.addFlashAttribute("message", "이메일 주소가 존재하지 않습니다.");
    } else {
      String password = mailService.createPassword();
      params.put("password", password);
      // 신규 비밀번호 발번 후 메일 전송
      boolean bool = mailService.sendMail(params.get("email"), password);
      if(!bool) {
        redirectAttr.addFlashAttribute("message", "메일이 정상적으로 발송되지 않았습니다.");
      } else {
        redirectAttr.addFlashAttribute("message", "메일이 정상적으로 발송되었습니다.");
        usersService.updatePassword(params);
      }       
    }
    return new ModelAndView("redirect:/find");
  }
  
  /**
   * 로그아웃
   * @param session
   * @return
   */
	@SuppressWarnings("unchecked")
  @RequestMapping(value = "/signout")
  public ModelAndView logout(HttpSession session) {
	  //세션 속성 삭제
	  Enumeration<String> e = session.getAttributeNames();
    while(e.hasMoreElements()) {
      String name = e.nextElement();
      session.removeAttribute(name);
    }
    // 세션 제거
	  session.invalidate();
    return new ModelAndView("redirect:/");
  }
}