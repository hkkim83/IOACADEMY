package kr.co.ioacademy.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.ioacademy.base.BaseController;
import kr.co.ioacademy.util.FileUtil;
import kr.co.ioacademy.util.StringUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController extends BaseController {
  @Value("${download.dir}") private String downloadDir;
  @Value("${upload.dir}")   private String uploadDir;
  @Value("${image.dir}")    private String imageDir;
  
  /**
   * 파일 다운로드
   * @param fileName
   * @param response
   * @return
   * @throws UnsupportedEncodingException 
   */
  @RequestMapping(value = "/download")
  @ResponseBody
  public FileSystemResource download(@RequestParam Map<String, String> params, HttpServletResponse response) throws UnsupportedEncodingException {
    String filePath = StringUtil.isNull(params.get("file_path")) ? downloadDir : params.get("file_path");
    String fileName = params.get("file_name");
    
    File file = new File(filePath +"/"+fileName);
    logger.info("download filePath::::"+file.getPath());
    logger.info("download fileName::::"+file.getName());
    fileName = URLEncoder.encode(file.getName(), "utf-8");
    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    
    return new FileSystemResource(file);
    
  }
  
  /**
   * 파일 다운로드
   * @param fileName
   * @param response
   * @return
   * @throws UnsupportedEncodingException 
   */
  @RequestMapping(value = "/preview/{id}")
  public ModelAndView preview(@PathVariable(value = "id") String id, HttpServletResponse response) throws UnsupportedEncodingException {
  String fileName = "smp_"+id+".pdf";  
  ModelAndView modelAndView = new ModelAndView("user/preview");
	modelAndView.addObject("data", imageDir+"/"+fileName);
	return modelAndView;    
  }
  
  /**
   * 파일 업로드
   * @param file
   * @param request
   * @param response
   * @throws IllegalStateException 
   * @throws IOException 
   */
  @RequestMapping(value = "/upload")
  @ResponseBody
  public Map<String, Object> upload(HttpSession session, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException 
  {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    LocalDateTime dateTime = LocalDateTime.now();
    String uploadTime = dateTime.format(formatter);
    // 파일명
    String fileName = file.getOriginalFilename();
    String fileExt  = FileUtil.getExtension(fileName);
    String reFileName = FileUtil.setExtension(uploadTime, fileExt);
    
    File newFile = new File(uploadDir, reFileName);
    file.transferTo(newFile);
    
    Map<String, Object> map = success();
    map.put("file_name", reFileName);
    logger.info("upload map:::"+map);
    return map;
  } 

  /**
   * 이미지 업로드
   * @param file
   * @param request
   * @param response
   * @throws IllegalStateException 
   * @throws IOException 
   */
  @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> uploadImage(HttpSession session, @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException 
  {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    LocalDateTime dateTime = LocalDateTime.now();
    String uploadTime = dateTime.format(formatter);
    // 파일명
    String fileName = file.getOriginalFilename();
    String fileExt  = FileUtil.getExtension(fileName);
    String reFileName = FileUtil.setExtension(uploadTime, fileExt);
    String realPath = session.getServletContext().getRealPath(imageDir);

    File newFile = new File(realPath, reFileName);
    file.transferTo(newFile);
    
    Map<String, Object> map = success();
    map.put("file_name", reFileName);
    logger.info("uploadImage map:::"+map);
    return map;
  } 
}
