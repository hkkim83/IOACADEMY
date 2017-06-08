package kr.co.ioacademy.upload;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kr.co.ioacademy.dto.User;

abstract public class Upload {
  protected final Log logger = LogFactory.getLog(this.getClass());
  protected String uploadDir;
  protected Map<String, String> params;
  protected String userId;
  protected String userName;
  protected int cnt;
  
  public Upload(String uploadDir, Map<String, String> params, User user) {
    this.cnt = 0;
    this.uploadDir = uploadDir;
    this.params = params;
    this.userId = user.getId();
    this.userName = user.getName();
  }

  public int count() {
    return cnt;
  }
  
  abstract public Map<String, String> go();

  
  /**
   * 디렉토리 생성
   * @param rootPath
   * @param subPath
   * @return
   */
  protected String makeDir(String subPath, String currId, String lecId) {
    File filePath = new File(uploadDir+"/"+subPath+"/"+currId+"/"+lecId);
    logger.info(uploadDir+"/"+subPath+"/"+currId+"/"+lecId);
    if(!filePath.exists()) filePath.mkdirs();
    return filePath.getPath();
  }  
  
  /**
   * 디렉토리 생성
   * @param rootPath
   * @param subPath
   * @return
   */
  protected String makeDir(String subPath, String currId) {
    File filePath = new File(uploadDir+"/"+subPath+"/"+currId);
    if(!filePath.exists()) filePath.mkdirs();
    return filePath.getPath();
  } 
  
  /**
   * 디렉토리 생성
   * @param rootPath
   * @param subPath
   * @return
   */
  protected String makeDir(String subPath) {
    File filePath = new File(uploadDir+"/"+subPath);
    if(!filePath.exists()) filePath.mkdirs();
    return filePath.getPath();
  }
}
