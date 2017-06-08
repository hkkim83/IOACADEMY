package kr.co.ioacademy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {
  /**
   * 빈값 체크
   * 
   * @param str
   * @return
   */
  public static boolean isNull(String str) {
    if (str == null) str = "";
    else str = str.trim();
    return (str.length() == 0);
  }
  
  /**
   * uri 변경하기
   * @param str
   * @return
   */
  public static String convertURI(String uri) {
    if(isNull(uri))
      return "/";
    uri = uri.replace(":8080","");
    uri = uri.replace("http://","");
    String str =  uri.substring(uri.indexOf("/"), uri.length()); 
    
    if(isNull(uri) ||  uri.indexOf("find") > 0 || str.indexOf("signin") > 0 || str.indexOf("signup") > 0) 
      str = "/";
    return str;
  }
  
  /**
   * 코드명 변경
   * 
   * @param str
   * @return
   */
  public static void convertToCommName(Map<String, Object> map, List<Map<String, String>> codeList, String key) {
    String value = (String)map.get(key);
    if(isNull(value)) return;
    for (Map<String, String> tempMap : codeList) {
      String code = tempMap.get("comm_code");
      String name = tempMap.get("comm_name");
      if (value.indexOf(code) > -1) {
        value = value.replaceAll(code, name);
      }
    }
    map.put(key, value);
    convertToBr(map, key, ",");
  }
  

  /**
   * 전체 코드리스트에서 필요한 것만 빼내기
   * 
   * @param str
   * @return
   */
  public static void convertToCommCode(Map<String, String> map, List<Map<String, String>> codeList, String key) {
    String value = (String)map.get(key);
    if(isNull(value)) return;
    for(int i=codeList.size()-1; i>0; i--) {
      Map<String, String> tempMap = codeList.get(i);
      String code = tempMap.get("comm_code");
      // value값에 code가 존재하지 않으면
      if (value.indexOf(code) < 0) 
        codeList.remove(i);
    }
  }
  
  /**
   * 문자를 맵으로 변경
   * 
   * @param str
   * @return
   */
  public static void convertToMap(Map<String, Object> map, String key, String otherKey) {
    String value = (String)map.get(key);
    if(isNull(value)) return;
    value = value.replace("\r\n", "\n");
    value = value.replace("\n\n", "\n");
    
    String[] arr = value.split("@");
    
    if(arr.length < 1) return;
    
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    
    for (String str : arr) {
      String strArr[] = str.split("\n");
      
      if(strArr.length < 1) continue;
      if(isNull(strArr[0])) continue;
      Map<String, Object> desc = new HashMap<String, Object>();
      List<String> contentList = new ArrayList<String>();
      desc.put("title", strArr[0]);
      for(int i=1; i<strArr.length; i++) {
        contentList.add(strArr[i]);
      }
      desc.put("content", contentList);
      list.add(desc);
    }
    map.put(otherKey, list);
  }
    
  
  
  /**
   * 특수문자 변 <br>
   * 로 변경
   * 
   * @param str
   * @return
   */
  public static void convertToSquare(Map<String, Object> map, String key) {
    String value = (String)map.get(key);
    if(isNull(value)) return;
    value = value.replace("@", "◼︎");
    map.put(key, value);
  }
  
  /**
   * 개행 <br>
   * 로 변경
   * 
   * @param str
   * @return
   */
  public static void convertToBr(Map<String, Object> map, String key) {
    String value = (String)map.get(key);
    if(isNull(value)) return;
    value = value.replace("\r\n", "<br>");
    value = value.replace("\n", "<br>");
    map.put(key, value);
  }
  
  /**
   * 개행 <br>
   * 로 변경
   * 
   * @param str
   * @return
   */
  public static void convertToBrList(List<Map<String, String>> list, String[] keyArr) {
    if(list.size() < 1) return;
    for(Map<String, String> map : list) {
      for(String key : keyArr) {
        String value = map.get(key);
        if(isNull(value)) return;
        value = value.replaceAll("\r", "<br>").replaceAll("\r\n", "<br>");
        map.put(key, value);
      }
    }
  }
  
  /**
   * 개행 <br>
   * 로 변경
   * 
   * @param str
   * @return
   */
  public static void convertToBr(Map<String, Object> map, String key, String str) {
    String value = (String)map.get(key);
    if(isNull(value)) return;
    value = value.replace(str, "<br>");
    map.put(key, value);
  }
  
  public static String makeTelNo(String telNo1, String telNo2, String telNo3) {
    String telNo = telNo1+'-'+telNo2+'-'+telNo3;
    return telNo;
  }
  
  /**
   * 리스트 인자 배열에 담기
   * @param list
   * @return
   */
  public static String[] listToArray(List<Map<String, String>> list, String key) {
    String[] phoneArr = new String[list.size()];
    int index = 0;
    for(Map<String, String> map : list) {
      phoneArr[index++] = map.get(key);
    }
    return phoneArr;
  }
  
  /**
   * 배열을 문자로 만들기
   * @param array
   * @return
   */
  public static String ArrayToString(String[] arr) {
    if(arr == null || arr.length < 1) return "";
    StringBuilder sb = new StringBuilder();
    for(String str : arr) {
      sb.append(str).append(",");
    }
    String ret = sb.toString();
    ret = ret.substring(0, ret.length()-1);
    return ret;
  }
  
  /**
   * 바이트 길이 계산하기
   * @param str
   * @return
   */
  public static int getByteLength(String str) {
    return str.getBytes().length;
  }
  
  /**
   * 정해진 길이만큼 자르기
   * @param str
   * @param len
   * @return
   */
  public static String cut(String str, int len) {
    int l = 0;
    for (int i = 0; i < str.length(); i++) {
      l += (str.charAt(i) > 128) ? 2 : 1;
      if (l > len)
        return str.substring(0, i) + "...";
    }
    return str;
  }
  
  /**
   * 특수문자 변경하기 
   * @param str
   * @param len
   * @return
   */
  public static String convertEscape(String str) {
    str = str.replace("&", "＆");
    str = str.replace("<", "＜");
    str = str.replace(">", "＞");
    str = str.replace("'", "＇");
    str = str.replace("\"", "＂");
    return str;
  }
  
  public static void stringToMap(Map<String, String> map, String str) {
    System.out.println(str);
    String arr[] = str.split("&");
    for(String temp : arr) {
      String tempArr[] = temp.split("=");
      map.put(tempArr[0], tempArr[1]);
    }
  }
}