package com.survivor.calendarview;

import org.junit.Test;

/**
 * 农历测试
 */
public class LunarUtilTest {
    @Test
    public void solarToLunar() throws Exception {
        LunarUtil.solarToLunar(2017,6,24);
        LunarUtil.solarToLunar(2017,6,25);
        LunarUtil.solarToLunar(2017,7,23);
        LunarUtil.solarToLunar(2017,7,24);
    }

}