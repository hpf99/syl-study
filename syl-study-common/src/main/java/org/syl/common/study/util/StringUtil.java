package org.syl.common.study.util;

public class StringUtil {

	public static boolean isBlank(String... str) {
		if (str != null && str.length > 0) {
			for (String s : str) {
				if (s == null || s.trim().length() == 0) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	
	public static boolean isNotBlank(String... str){
		return !isBlank(str);
	} 
	
	
	
	
	public static void main(String[] args) {
		boolean blank = isNotBlank(""," ");
		System.out.println(blank);
	}
}
