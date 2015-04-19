package matachi.mapeditor.editor;

/**
 * Created by Kylar on 4/19/2015.
 */
public class JSONToString {

    public static String toJSONString(Map map) {
        if(map == null) {
            return "null";
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
                }

                Entry entry = (Entry)iter.next();
                toJSONString(String.valueOf(entry.getKey()), entry.getValue(), sb);
            }

            sb.append('}');
            return sb.toString();
        }
    }

    private static String toJSONString(String key, Object value, StringBuffer sb) {
        sb.append('\"');
        if(key == null) {
            sb.append("null");
        } else {
            JSONValue.escape(key, sb);
        }

        sb.append('\"').append(':');
        sb.append(JSONValue.toJSONString(value));
        return sb.toString();
    }

}
