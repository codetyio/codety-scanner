package io.codety.scanner.util;

public class StringUtil {

    public static String toCamelCaseWord(String str){
        if(str == null){
            return str;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if(i == 0) {
                builder.append(Character.toUpperCase(str.charAt(i)));
            }else{
                builder.append(Character.toLowerCase(str.charAt(i)));
            }
        }
        return builder.toString();

    }

}
