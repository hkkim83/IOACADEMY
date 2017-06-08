package kr.co.ioacademy.core.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.dto.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Controller
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

  private final Log logger = LogFactory.getLog(this.getClass());  
  @Value("${permit.page}")  private String permitPage;
  @Value("${admin.page}")   private String adminPage;  
  
  private int LOGIN_CODE = 401;
  private int ROOT_CODE = 403;
  private String LOGIN_URI = "/signin";
  private String ROOT_URI = "/";
  
  /**
   * 서블릿에 전달되기 전에 세션 체크
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    
    boolean result = false;
    User user = (User) request.getSession().getAttribute(BaseController.USER_SESSION);
    String uri = request.getRequestURI();
    try {
      if (uri.startsWith("/resources") || uri.equals("/")) {
        result = true;
      } else {
        if (permit(request, handler, uri)) {
          result = true;
        } else {
          if (user == null || user.getId() == null) {
            sendRedirect(request, response, LOGIN_CODE, LOGIN_URI);
            result = false;
          } else {
            if(!"A".equals(user.getPriority()) && !"S".equals(user.getPriority())) {
              if(isAdmin(request, handler, uri)) {
                sendRedirect(request, response, ROOT_CODE, ROOT_URI);
                result = false;
              } else 
                result = true;
            } else 
              result = true;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    
    return result;
  }

  /**
   * 서블릿에 전달된 후에 사용자 정보 전달
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    String uri = request.getRequestURI();
    String method = request.getMethod();
    if (!uri.startsWith("/resources") && !uri.startsWith("/download") && !uri.startsWith("/preview") && method.equals("GET")) {
      User user = (User) request.getSession().getAttribute(BaseController.USER_SESSION);
      modelAndView.addObject("user", user);       
    }
  }

  
  /**
   * 권한에 따른 페이지 전환
   * @param request
   * @param handler
   * @param uri
   * @return
   * @throws IOException 
   */
  private void sendRedirect(HttpServletRequest request, HttpServletResponse response, int code, String uri) throws IOException {
   
    if(request.getHeader("accept").startsWith("application/json")) {
      response.sendError(code, uri);
    } else  {
      response.sendRedirect(uri);
    }
  }
  
  /**
   * 세션 관련 페이지 허용 여부
   * 
   * @param request
   * @param handler
   * @param uri
   * @return
   */
  private boolean permit(HttpServletRequest request, Object handler, String uri) {
    boolean ret = false;
    String arr[] = permitPage.split(",");
    String courses = "^/courses/[0-9]*";
    String preview = "^/preview/[0-9]*";
    for (String str : arr) {
      str = str.trim();
      if (uri.equals("/" + str) || uri.matches(courses) || uri.matches(preview)) {
        ret = true;
        break;
      }
    }
    return ret;
  }
  
  /**
   * 세션 관련 페이지 허용 여부
   * 
   * @param request
   * @param handler
   * @param uri
   * @return
   */
  private boolean isAdmin(HttpServletRequest request, Object handler, String uri) {
    boolean ret = false;
    String arr[] = adminPage.split(",");
    for (String str : arr) {
      str = str.trim();
      if (uri.equals("/" + str)) {
        ret = true;
        break;
      }
    }
    return ret;
  }
}
