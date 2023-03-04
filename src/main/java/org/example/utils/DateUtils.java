package org.example.utils;

import jdk.dynalink.linker.GuardedInvocationTransformer;

import java.util.Date;

public class DateUtils {
    public static Long getHours(Date start,Date end)
    {
        long diff=end.getTime()-start.getTime();
        return diff/(1000*60*60);
    }
}
