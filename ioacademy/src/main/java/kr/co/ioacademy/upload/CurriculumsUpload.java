package kr.co.ioacademy.upload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import kr.co.ioacademy.dto.User;
import kr.co.ioacademy.util.FileUtil;
import kr.co.ioacademy.util.StringUtil;

public class CurriculumsUpload extends Upload {
  
  public CurriculumsUpload(String uploadDir, Map<String, String> params, User user) {
    super(uploadDir, params, user);
  }
  
  /**
   * 파일명 변경
   */
  public Map<String, String> go() {
    Map<String, String> map = new HashMap<String, String>();
    String curriId  = params.get("curri_id");
    String realPath = params.get("real_path");
    map.put("curri_id", curriId);
    map.put("status"  , "update");
    map.put("login_id", params.get("login_id"));
    
    if(!StringUtil.isNull(params.get("img_file")))
      transfer(map, "img_file", "img_"+curriId, realPath);
    if(!StringUtil.isNull(params.get("ref_file")))
      transfer(map, "ref_file", "ref_"+curriId, realPath);
    if(!StringUtil.isNull(params.get("smp_file")))
      transfer(map, "smp_file", "smp_"+curriId, realPath);
    return map;    
  }

  private void transfer(Map<String, String> map, String src, String dist, String realPath) {
    if(StringUtil.isNull(params.get(src)))
      return;
    ++cnt;
    String fileName   = params.get(src);
    String fileExt    = FileUtil.getExtension(fileName);
    String reFileName = FileUtil.setExtension(dist, fileExt);
    
    File srcFile = new File(realPath, fileName);
    File distFile = new File(realPath, reFileName);
    srcFile.renameTo(distFile);
    map.put(src, reFileName);
  }
}
