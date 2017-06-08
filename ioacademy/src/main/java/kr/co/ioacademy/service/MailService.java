package kr.co.ioacademy.service;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
  private final Log logger = LogFactory.getLog(this.getClass());
  
  @Value("${mail.fromUser}") private String fromUser;
  
  @Autowired
  private JavaMailSender mailSender;
  
  /**
   * 메일 보내기
   * 
   * @param map
   */
  public boolean sendMail(Map<String, String> map) {
    MimeMessage message = mailSender.createMimeMessage();
    
    try {
      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
      String toUserList = map.get("toUser");
      messageHelper.setSubject(map.get("subject"));
      messageHelper.setTo(toUserList);
      messageHelper.setFrom(map.get("fromUser"));
      messageHelper.setText(map.get("text"), true);
      mailSender.send(message);
      
    } catch (MessagingException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  /**
   * 메일 보내기
   * 
   * @param map
   */
  public boolean sendMail(String toUser, String password) {
    Map<String, String> emailInfo = new HashMap<String, String>();
    String text = "안녕하세요. 아이오 교육센터입니다.<br/>";
    text += "요청하신 비밀번호는 아래와 같습니다.<br/>";
    text += "비밀번호 : <b>" + password + "</b><br/><br/><br/>";
    text += "<h3><a href='http://ioacademy.co.kr/signin'>아이오 교육센터 접속하기</a></h3>";
    logger.info("fromUser:::"+fromUser);
    emailInfo.put("subject", "[아이오 교육센터] 변경된 비밀번호 정보입니다.");
    emailInfo.put("toUser", toUser);
    emailInfo.put("fromUser", fromUser);
    emailInfo.put("text", text);
    return sendMail(emailInfo);
  }
  
  /**
   * 비밀번호 생성하기
   * @return
   */
  public String createPassword() {
	String str="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; // 랜덤으로 표시될 문자
	int i=0, len=8;
	double d = 0;
	String ret ="";
	for (int x=0;x<len;x++) {
	  d=Math.random()*61;
	  i=(int)(Math.round(d));
	  ret+=str.charAt(i);
    }
	return ret;
  }
}
