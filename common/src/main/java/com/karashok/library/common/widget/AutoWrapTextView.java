package com.karashok.library.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KaraShokZ (张耀中)
 * DESCRIPTION 解决中英文混排，自动折行问题
 * @name AutoWrapTextView
 * @date 2020/04/09 17:04
 **/
public class AutoWrapTextView extends AppCompatTextView {

    public AutoWrapTextView(Context context) {
        super(context);
    }

    public AutoWrapTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoWrapTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 手动计算TextView每行能展示多少字符，并折行（需要控件绘制完）
     * @param charSequence 目标文本
     * @return
     */
    public String splitText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) return "";
        List<StringBuilder> splitTextList = new ArrayList<>();
        int singleTextWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight(); // TextView 每行的宽度
        int currentSingleTextWidth = 0; // 用来叠加字符的宽度
        StringBuilder lineStringBuffer = new StringBuilder();
        for (int i = 0, length = charSequence.length(); i < length; i++) {
            char textChar = charSequence.charAt(i);
            currentSingleTextWidth += getSingleCharWidth(textChar);
            if (currentSingleTextWidth > singleTextWidth) {// 当字符宽度大于 TextView 的宽度时，需要折行
                lineStringBuffer.append(" ");// 末尾加空字符，会强行分词
                splitTextList.add(lineStringBuffer);
                lineStringBuffer = new StringBuilder();
                currentSingleTextWidth = 0;
                i--;
            } else {
                lineStringBuffer.append(textChar);
                if (i == length - 1) splitTextList.add(lineStringBuffer);
            }
        }

        StringBuilder splitSb = new StringBuilder();
        int maxLines = getMaxLines();
        boolean hasMore = splitTextList.size() > maxLines;
        if (hasMore){// 当所需行数大于最大行数时，手动截取
            splitTextList = splitTextList.subList(0,maxLines);
        }
        for (StringBuilder stringBuilder : splitTextList){
            splitSb.append(stringBuilder);
        }
        if (hasMore){// 当所需行数大于最大行数时，最后一行手动省略末尾（这个只适用于 ellipsize=end）
            int length = splitSb.length();
            String three_dots = "...";
            splitSb.replace(length - three_dots.length() + 1,length - 1,three_dots);
        }
        return splitSb.toString();

    }

    /**
     * 计算每个字符的宽度
     * @param textChar 待计算的字符
     * @return
     */
    private float getSingleCharWidth(char textChar) {
        float[] width = new float[1];
        getPaint().getTextWidths(new char[] {textChar}, 0, 1, width);
        return width[0];
    }
}
