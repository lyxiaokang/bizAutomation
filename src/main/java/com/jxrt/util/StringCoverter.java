package com.jxrt.util;

public class StringCoverter {
    /** 
     * 把中文转成Unicode码 
     * @param str 
     * @return 
     */  
    public static String string2Unicode(String str){  
        String result="";  
        for (int i = 0; i < str.length(); i++){  
            int chr1 = (char) str.charAt(i);  
//            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
            	String strs = Integer.toHexString(chr1);
            	int m = strs.length();
            	for(int k=0;k<4-m;k++){
            		strs = "0"+strs;
            	}
            	result+="\\u" + strs;
            	//            	result+="\\\\u" + strs;
//            }else{  
//                result+=str.charAt(i);  
//            }  
        }  
        return result;  
    }
      
    public static String unicode2String(String utfString){  
    	boolean flag = false;
//    	utfString = utfString.replaceAll("u", "\\\\u");
    	utfString = utfString.replaceAll("u", "\\u");
        StringBuilder sb = new StringBuilder();  
        int i = -1;  
        int pos = 0;  
          
        while((i=utfString.indexOf("\\u", pos)) != -1){  
            sb.append(utfString.substring(pos, i));  
            if(i+5 < utfString.length()){  
                pos = i+6;  
                sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
            }  
            flag = true;
        }  
        
        if(flag)
        	return sb.toString();
        
        return utfString;
    }
    
    public static void main(String args[]){
    	String str = StringCoverter.string2Unicode("测试企业我的");
    	System.out.println(str);
    	System.out.println(unicode2String(str));
//    	String str2="\u767e\u5ea6\u65b0\u95fb";
//    	System.out.println(StringCoverter.unicode2String("\\u767e\\u5ea6\\u65b0\\u95fb"));
    }
}
