package com.hanbing.mytest.utils.hanzi2pinyin;

import java.util.HashSet;
import java.util.Set;

/**
 * ��PinyinHelper.java��ʵ��������TODO ��ʵ������ 
 * @author Administrator 2014��2��21�� ����2:30:44
 */
public class PinyinHelper {

    static PinyinHelper mHelper;
    /**
     * 
     */
    public PinyinHelper() {
        // TODO Auto-generated constructor stub
        
        ChineseToPinyinHelper.setLegalCharactSet(getCharSet());
    }
    
    public static PinyinHelper getInstance()
    {
        if (null == mHelper)
        {
            mHelper = new PinyinHelper();
        }
        
        return mHelper;
    }

    Character[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
    };

    private Set getCharSet() {
        Set<Character> set = new HashSet<Character>();

        for (char ch : chars) {
            set.add(ch);
        }
        return set;
    }
    
    public String getPinyin(String input)
    {
        return ChineseToPinyinHelper.hanziToPinyin(input);
    }
    
   
    
}
