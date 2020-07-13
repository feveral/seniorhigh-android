package com.feveral.seniorhigh;

import com.feveral.seniorhigh.utility.TextUtils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void TextUtils_addPlusToSubjectSet_1() throws Exception {
        String before = "英自";
        String after = "英+自";
        assertEquals(after, TextUtils.addPlusToSubjectSet(before));
    }
    @Test
    public void TextUtils_addPlusToSubjectSet_2() throws Exception {
        String before = "英";
        String after = "英";
        assertEquals(after, TextUtils.addPlusToSubjectSet(before));
    }
}