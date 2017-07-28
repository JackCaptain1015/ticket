package com.tqmall.ticket.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by wurenzhi on 2017/01/09.
 */
public class ChineseToSpellUtil {

    /**
     * 获取中文字符串第一个字的首字母
     * @param chineseWords
     * @return
     */
    public static char getFirstCahrOfFirstWord(String chineseWords){
        String spellString = ChineseToSpellUtil.getSpellStringOfChineseWords(chineseWords);
        return spellString.charAt(0);
    }

    /**
     * 获取中文字符串的拼音
     * @param chineseWords 中文
     * @return
     */
    public static String getSpellStringOfChineseWords(String chineseWords){
        HanyuPinyinOutputFormat pyFormat = new HanyuPinyinOutputFormat();
        //设置小写字母
        pyFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        //没有音标
        pyFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //设置v字母在中文拼音中的显示
        pyFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        String result = "";
        try {
            /**
             * 第三个入参为：拼接隔离字符串
             * 第四个入参为：是否需要记忆
             */
            result = PinyinHelper.toHanYuPinyinString(chineseWords,pyFormat,"",true);
        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }

        return result;
    }
}
