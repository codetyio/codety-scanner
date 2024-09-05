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

    public static String camelCaseSentencesToDashConnectedSentences(String str){
        if(str == null){
            return str;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(i == 0) {
                builder.append(Character.toLowerCase(c));
            }else if(Character.isUpperCase(c)){
                builder.append("-").append(Character.toLowerCase(c));
            }else{
                builder.append(c);
            }
        }
        return builder.toString();

    }

}
