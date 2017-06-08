package kr.co.ioacademy.core.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionResolver implements HandlerExceptionResolver {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public ModelAndView resolveException(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		
		String message = "";
		
		if(ex instanceof DuplicateKeyException) {
			logger.info("여기 안오나???\n");
			message = "키중복";
		} else {
			message = "관리자문의";
		}
		
		return handleMessage(message);
	}
	
	protected ModelAndView handleMessage(String message) {
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("result", message);
		return modelAndView;
	}

}
