package cn.guoyka.simplermybatis.util;


import java.util.Collection;
import java.util.Iterator;

public class StringUtil{
    public static String join(Collection collection, String separator){
        return join(collection.iterator(), separator);
    }
    public static String join(Iterator iterator, String separator) {
        if (iterator == null) {
            return null;
        } else if (!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if (!iterator.hasNext()) {
                return toString(first);
            } else {
                StringBuilder buf = new StringBuilder(256);
                if (first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    if (separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if (obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}