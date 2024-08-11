package com.prandini.smartwallet.common.utils;

import java.util.Map;

/*
 * @author prandini
 * created 8/11/24
 */
public class QueryUtils {

    private static void safeAddParams(Map<String, Object> params, String name, Object value, StringBuilder sb, String queryPart){
        if(value != null){
            params.put(name, value);
            sb.append(queryPart);
        }
    }
}
