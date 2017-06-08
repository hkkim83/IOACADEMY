package kr.co.ioacademy.base;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import kr.co.ioacademy.dto.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 사용자 세션 관리용
 * 
 * @author Administrator
 *
 */
abstract public class BaseController {
  protected final Log logger = LogFactory.getLog(this.getClass());
  public static final String USER_SESSION = "user";
  public static final String RESULT_CODE = "result_code";
  public static final String RESULT_MSG = "result_msg";
  public static final Map<String, Object> MSG_MAP = new HashMap<String, Object>();
  
  /**
   * 사용자 세션 설정
   * 
   * @param session
   * @param user
   */
  protected void setLoginUser(HttpSession session, User user) {
    session.setAttribute(USER_SESSION, user);
  }
  
  /**
   * 사용자 세선 반환
   * 
   * @param session
   * @return
   */
  protected User getLoginUser(HttpSession session) {
    return (User) session.getAttribute(USER_SESSION);
  }
  
  /**
   * 사용자 아이디 반환
   * 
   * @param session
   * @return
   */
  protected String getLoginId(HttpSession session) {
    User user = (User) session.getAttribute(USER_SESSION);
    return user.getId();
  }
  
  /**
   * 파라미터에 로그인 아이디 추가하기
   * 
   * @param session
   * @return
   */
  protected void addLoginId(HttpSession session, Map<String, String> params) {
    params.put("login_id", getLoginId(session));
  }
  
  /**
   * 파라미터에 아이디 추가하기
   * 
   * @param session
   * @return
   */
  protected void addId(HttpSession session, Map<String, String> params) {
    params.put("id", getLoginId(session));
  }
  
  /**
   * 사용자 이메일 반환
   * 
   * @param session
   * @return
   */
  protected String getLoginEmail(HttpSession session) {
    User user = (User) session.getAttribute(USER_SESSION);
    return user.getEmail();
  }
  
  /**
   * 성공 메시지 출력
   * 
   * @param session
   * @return
   */
  protected Map<String, Object> success() {
    MSG_MAP.clear();
    MSG_MAP.put(RESULT_CODE, "0001");
    MSG_MAP.put(RESULT_MSG, "정상적으로 처리되었습니다.");
    return MSG_MAP;
  }
  
  /**
   * 에러 메시지 출력
   * 
   * @param session
   * @return
   */
  protected Map<String, Object> error() {
    MSG_MAP.clear();
    MSG_MAP.put(RESULT_CODE, "9999");
    MSG_MAP.put(RESULT_MSG, "관리자에게 문의하세요.");
    return MSG_MAP;
  }

  /**
   * 에러 메시지 출력
   * 
   * @param session
   * @return
   */
  protected Map<String, Object> error(String value) {
    MSG_MAP.clear();
    MSG_MAP.put(RESULT_CODE, "9001");
    MSG_MAP.put(RESULT_MSG, "중복된 "+value+" 내역이 존재합니다.");
    return MSG_MAP;
  }
  
  /**
   * 에러 메시지 출력
   * 
   * @param session
   * @return
   */
  protected Map<String, Object> error(String key, String value) {
    MSG_MAP.clear();
    MSG_MAP.put(RESULT_CODE, key);
    MSG_MAP.put(RESULT_MSG, value);
    return MSG_MAP;
  }
}
