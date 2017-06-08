package kr.co.ioacademy.upload;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.ioacademy.dto.User;
import kr.co.ioacademy.util.FileUtil;

public class ApplicationsUpload extends Upload{
  private final Log logger = LogFactory.getLog(this.getClass());
  
  public ApplicationsUpload(String uploadDir, Map<String, String> params, User user) {
    super(uploadDir, params, user);
  }

  public Map<String, String> go() {
    String subPath    = "application";
    String fileName   = params.get("file_name");
    String curriId    = params.get("curri_id");
    String lecId      = params.get("lec_id");
    String fileExt    = FileUtil.getExtension(fileName);
    String reFileName = FileUtil.setExtension(userId+"_"+userName, fileExt);
    
    File srcFile = new File(uploadDir, fileName);
    String reFilePath = makeDir(subPath, curriId, lecId);
    logger.info("uploadDir:::"+uploadDir);
    
    File distFile = new File(reFilePath, reFileName);
    srcFile.renameTo(distFile);
    params.put("file_path", reFilePath);
    params.put("file_name", reFileName);
    return params;
  }
}
