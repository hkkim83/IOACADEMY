package kr.co.ioacademy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.service.CommonService;
import kr.co.ioacademy.service.CurriculumsService;
import kr.co.ioacademy.upload.CurriculumsUpload;
import kr.co.ioacademy.upload.Upload;
import kr.co.ioacademy.util.FileUtil;
import kr.co.ioacademy.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CurriculumsController extends BaseController {
  
  @Value("${upload.dir}")  private String uploadDir;
  @Value("${image.dir}")   private String imageDir;
  private CurriculumsService curriculumsService;
  private CommonService commonService;
  

  @Autowired
  public void setCurriculumsService(CurriculumsService curriculumsService) {
    this.curriculumsService = curriculumsService;
  }
  
  @Autowired
  public void setCommonService(CommonService commonService) {
    this.commonService = commonService;
  }
  
  /**
   * 과정관리 화면
   * @param request
   * @param response
   * @return
   */
	@RequestMapping(value = "/curriculums")
	public ModelAndView view(@RequestParam Map<String, String> params) {
		ModelAndView modelAndView = new ModelAndView("admin/curriculums");
		List<Map<String, String>> list = curriculumsService.selectCurriculumList(params);
		modelAndView.addObject("list" , list);
		modelAndView.addObject("params", params);
		
		return modelAndView;
	}
	
	/**
	 * 과정등록 화면
	 * @param request
	 * @param response
	 * @return
	 */
  @RequestMapping(value = "/curriculums/{id}")
  public ModelAndView curriculum(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params) {
		
    ModelAndView modelAndView = new ModelAndView("admin/curriculum");
		params.put("curri_id", id);
    Map<String, Object> resultMap      = curriculumsService.selectCurriculum(params);
    params.put("group_code", "001");
    List<Map<String, String>> curriKindList = commonService.selectCodeList(params);
    params.put("group_code", "002");
    List<Map<String, String>> curriGradeList = commonService.selectCodeList(params);
    params.put("group_code", "003");
    List<Map<String, String>> curriTypeList = commonService.selectCodeList(params);
    params.put("group_code", "004");
    List<Map<String, String>> preCourseList = commonService.selectCodeList(params);
    
    modelAndView.addObject("val", resultMap);
    modelAndView.addObject("curriKindList" , curriKindList);
    modelAndView.addObject("curriGradeList", curriGradeList);
    modelAndView.addObject("curriTypeList" , curriTypeList);
    modelAndView.addObject("preCourseList" , preCourseList);

    return modelAndView;
	}

  /**
   * 과정등록 화면
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/curriculums/{id}/copy")
  public ModelAndView copy(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpSession session) {
    params.put("curri_id", id);
    params.put("status", "copy");
    super.addLoginId(session, params);
    curriculumsService.save(params);
    
    return new ModelAndView("redirect:/curriculums/"+params.get("curri_id"));
  }
  
  /**
   * 과정목록 삭제
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/curriculums/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public Map<String, Object> delete(@PathVariable(value = "id") String id, @RequestBody String json, @RequestParam Map<String, String> params, HttpSession session) {
    String realPath = session.getServletContext().getRealPath(imageDir);
    String[] fileName = new String[3];
    
    StringUtil.stringToMap(params, json);
    params.put("curri_id", id);
    params.put("status", "update");
    super.addLoginId(session, params);

    logger.info("delete params:::"+params);
    
    // 과정 삭제 시 첨부파일(이미지, 교재, 참고서적) 삭제 
    if(params.get("del_yn") != null) {
      Map<String, Object> resultMap      = curriculumsService.selectCurriculum(params);    
      fileName[0] = (String)resultMap.get("img_file");
      fileName[1] = (String)resultMap.get("ref_file");
      fileName[2] = (String)resultMap.get("smp_file");
    }
    
    int ret = curriculumsService.save(params);
    if(ret < 1) return error();
    
    // 과정 삭제 시 첨부파일(이미지, 교재, 참고서적) 삭제     
    if(params.get("del_yn") != null)
      FileUtil.deleteFile(realPath, fileName);
    return success();
  }
    
  /**
   * 과정목록 저장
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/curriculums/{id}", method = RequestMethod.POST)
  @ResponseBody
  public ModelAndView save(@PathVariable(value = "id") String id, @RequestParam Map<String, String> params, HttpServletRequest req, HttpSession session, RedirectAttributes redirectAttr) {
    String realPath = session.getServletContext().getRealPath(imageDir);
    String preCourse = StringUtil.ArrayToString(req.getParameterValues("pre_course"));
    super.addLoginId(session, params);
    params.put("curri_id"  , id);
    params.put("status"    , id.equals("new") ? "insert" : "update");
    if("on".equals(params.get("show_yn")))
      params.put("show_yn" , "1");
    if(StringUtil.isNull(params.get("show_yn")))
      params.put("show_yn" , "0");
    params.put("real_path" , realPath);
    params.put("pre_course", preCourse);
    
    logger.info("realPath::::::"+realPath);
    logger.info("save params:::"+params);
    
    curriculumsService.save(params);
    
    // 첨부파일 업로드한 경우 
    Upload upload = new CurriculumsUpload(uploadDir, params, super.getLoginUser(session));
    Map<String, String> uploadMap = upload.go();
    //logger.info("params:::"+uploadMap);
    if(upload.count() > 0 ) {
      curriculumsService.save(uploadMap);
    }
    
    redirectAttr.addFlashAttribute("message", "정상적으로 처리되었습니다.");
   
    return new ModelAndView("redirect:/curriculums/"+params.get("curri_id"));
  } 
}
