package matachi.mapeditor.editor;


import org.json.simple.JSONAware;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by Kylar on 4/19/2015.
 */
public class JSONToString {


    private static String addSpaces(int depth){
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<depth; i++){
            sb.append(" ");
            sb.append(" ");
        }
        return sb.toString();
    }
    private static void addSpaces(StringBuffer sb, int depth){
        for (int i=0; i<depth; i++){
            sb.append(" ");
            sb.append(" ");
        }
    }

    public static String ObjectToJSONString(Map map, int depth) {
        String spaces = addSpaces(depth);
        if(map == null) {
            return spaces + "null";
        } else {
            StringBuffer sb = new StringBuffer();
            boolean first = true;
            Iterator iter = map.entrySet().iterator();
            sb.append('{');

            while(iter.hasNext()) {
                if(first) {
                    first = false;
                } else {
                    sb.append(',');
                    if (depth == 0) {
                        sb.append(System.getProperty("line.separator"));
                    }
                }

                Map.Entry entry = (Map.Entry)iter.next();
                ObjectToJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb, depth);
            }

            sb.append('}');
            return spaces + sb.toString();
        }
    }

    private static String ObjectToJSONString(String key, Object value, StringBuffer sb, int depth) {
        addSpaces(sb, depth);
        sb.append('\"');
        if(key == null) {
            sb.append("null");
        } else {
            valueEscape(key, sb);
        }

        sb.append('\"').append(':');
        sb.append(ValueToJSONString(value, depth));
        return sb.toString();
    }

    public static String ValueToJSONString(Object value, int depth) {
        if (value==null){
            return "null";
        }else if (value instanceof  String){
            return "\"" + valueEscape((String) value) + "\"";
        }else if (value instanceof Double){
            if (!((Double)value).isInfinite() && !((Double)value).isNaN()){
                return value.toString();
            }else{
                return "null";
            }
        }else if(value instanceof Float){
            if (!((Float)value).isInfinite() && !((Float)value).isNaN()){
                return value.toString();
            }else{
                return "null";
            }
        }else if (value instanceof Number){
            return value.toString();
        }else if (value instanceof Boolean) {
            return value.toString();
        }else if (value instanceof List){
            return ArrayToJSONString((List) value, depth+1);
        }else if (value instanceof Map){
            return ObjectToJSONString((Map) value, depth);
        }else if (value instanceof JSONAware) {
            return ((JSONAware)value).toJSONString();
        }else {
            return value.toString();
        }
    }

    public static String valueEscape(String s) {
        if(s == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();
            valueEscape(s, sb);
            return sb.toString();
        }
    }

    static void valueEscape(String s, StringBuffer sb) {
        for(int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            switch(ch) {
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                default:
                    if((ch < 0 || ch > 31) && (ch < 127 || ch > 159) && (ch < 8192 || ch > 8447)) {
                        sb.append(ch);
                    } else {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u");

                        for(int k = 0; k < 4 - ss.length(); ++k) {
                            sb.append('0');
                        }

                        sb.append(ss.toUpperCase());
                    }
            }
        }
    }

    public static String ArrayToJSONString(List list, int depth) {
        if(list == null) {
            return "null";
        } else {
            boolean first = true;
            StringBuffer sb = new StringBuffer();
            Iterator iter = list.iterator();
            sb.append('[');

            while(iter.hasNext()) {
                if(first) {
                    first = false;
                } else {
                    sb.append(',');
                    sb.append(System.getProperty("line.separator"));
                }

                Object value = iter.next();
                if(value == null) {
                    sb.append("null");
                } else {
                    sb.append(ValueToJSONString(value, depth));
                }
            }

            sb.append(']');
            return sb.toString();
        }
    }

}
