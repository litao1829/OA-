package org.example.utils;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {

    //对源数据生成MD5摘要
    public static String md5Digest(String source)
    {
        return DigestUtils.md2Hex(source);
    }


    //对源数据加盐混淆后生成MD5摘要
    public static String md5Digest(String source,Integer salt)
    {
        char[] chars=source.toCharArray();
        for(int i=0;i<chars.length;i++)
        {
            chars[i]=(char)(chars[i]+salt);
        }

        String target=new String(chars);
        return DigestUtils.md2Hex(target);
    }
}
