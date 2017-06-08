package kr.co.ioacademy.util;

import java.io.File;

public class FileUtil {
  /**
   * 디렉토리 생성
   * @param rootPath
   * @param userId
   * @return
   */
  public static String makeDir(String rootPath, String subPath) {
    File filePath = new File(rootPath+"/"+subPath);
    if(!filePath.exists()) filePath.mkdirs();
    return filePath.getPath();
  }
  
  /**
   * 확장자 가져오기
   * @param fileName
   * @return
   */
  public static String getExtension(String fileName) {
    int index = fileName.lastIndexOf(".");
    return fileName.substring(index+1, fileName.length());
  }

  
  /**
   * 확장자 설정하기
   * @param fileName
   * @param ext
   * @return
   */
  public static String setExtension(String fileName, String ext) {
    return fileName+"."+ext;
  }
  
  /**
   * 파일 삭제 
   * @param fileName
   * @return
   */
  public static boolean deleteFile(String filePath, String fileName) {
    File file = new File(filePath+"/"+fileName);
    boolean ret = true;
    if(file.exists()) ret = file.delete();
    return ret;
  }
  
  /**
   * 파일 삭제 
   * @param fileName
   * @return
   */
  public static boolean deleteFile(String filePath, String[] fileName) {
    File[] file = new File[fileName.length];
    boolean ret = true;
    for(int i = 0; i < fileName.length; i++) {
      if(StringUtil.isNull(fileName[i]) || "no_image.png".equals(fileName[i]))
        continue;
      file[i] = new File(filePath+"/"+fileName[i]);
      if(file[i].exists()) ret = file[i].delete();
    }
    return ret;
  }
}