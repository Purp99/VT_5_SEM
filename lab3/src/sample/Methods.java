package sample;

public class Methods {
    public static String getParam(String params, String paramName){
        StringBuilder paramsSB = new StringBuilder(params);
        int paramNameIndex = paramsSB.indexOf(paramName);
        if(paramNameIndex != -1){
            int temp = paramsSB.indexOf("&", paramNameIndex);
            if(temp == -1) {
                return paramsSB.substring(paramNameIndex+paramName.length()+1);
            }else{
                return paramsSB.substring(paramNameIndex+paramName.length()+1, temp);
            }
        }
        return null;
    }
}
