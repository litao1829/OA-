package org.example.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MD5UtilsTest {
    @Test
    public void Md5salt()
    {
        String test = MD5Utils.md5Digest("test", 193);
        System.out.println(test);
    }
}