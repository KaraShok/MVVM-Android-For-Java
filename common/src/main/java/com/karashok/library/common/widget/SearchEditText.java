package com.karashok.library.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.karashok.library.common.R;
import com.karashok.library.module_util.utilcode.utils.base_util.StringUtils;
import com.karashok.library.module_util.utilcode.utils.device_util.KeyboardUtils;


/**
 * @author KaraShokZ(zhangyaozhong)
 * @name SearchEditText
 * DESCRIPTION 搜索EditTextView
 * @date 2018/06/16/下午6:18
 */
public class SearchEditText extends AppCompatEditText implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {
    private static final String TAG = "SearchEditText";
    /**
     * 图标是否默认在左边
     */
    private boolean isIconLeft = false;
    /**
     * 是否点击软键盘搜索
     */
    private boolean pressSearch = false;
    /**
     * 是否点击软键盘删除
     */
    private boolean mPressDelete = false;
    /**
     * 软键盘搜索键监听
     */
    private OnSearchClickListener listener;

    /**
     * 控件的图片资源
     */
    private Drawable[] drawables;
    // 搜索图标和删除按钮图标
    private Drawable drawableLeft, drawableDel;
    // 记录点击坐标
    private int eventX, eventY;
    // 控件区域
    private Rect rect;
    private int offsetY;

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchClickListener {
        void onSearchClick(SearchEditText view) throws Exception;

        void editTextAfterTextChanged(SearchEditText view) throws Exception;
    }

    public SearchEditText(Context context) {
        this(context, null);
        init();
    }


    public SearchEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
        init();
    }


    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
        addTextChangedListener(this);

        clearFocus();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //获取在当前窗口内的绝对坐标
        int[] location1 = new int[2];
        getLocationInWindow(location1);
        int[] location2 = new int[2];

        //获取在整个屏幕内的绝对坐标
        getLocationOnScreen(location2);
        offsetY = location2[1] - location1[1];

        if (isIconLeft) { // 如果是默认样式，直接绘制
            if (length() < 1) {
                drawableDel = null;
            }
            if (drawableLeft == null) {
                drawableLeft = ContextCompat.getDrawable(getContext(), R.drawable.list_icon_search2x);
            }
            this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableDel, null);
            super.onDraw(canvas);
        } else {

            // 如果不是默认样式，需要将图标绘制在中间
            if (drawables == null) {
                drawables = getCompoundDrawables();
            }
            if (drawableLeft == null) {
                drawableLeft = drawables[0];
            }
            float textWidth = 0.0f;
            if (getHint() != null && !StringUtils.isEmpty(getHint().toString())) {
                textWidth = getPaint().measureText(getHint().toString());
            }
            int drawablePadding = getCompoundDrawablePadding();
            int drawableWidth = drawableLeft.getIntrinsicWidth();
            float bodyWidth = textWidth + drawableWidth + drawablePadding;
            canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
            super.onDraw(canvas);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        // 被点击时，恢复默认样式
        if (!pressSearch && StringUtils.isEmpty(getText().toString())) {
            isIconLeft = hasFocus;
        }

        if (!hasFocus) {
            setEnabled(false);
            setEnabled(true);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        // 点击搜索
        pressSearch = event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                && KeyEvent.ACTION_UP == event.getAction();

        // 点击删除
        mPressDelete = event.getKeyCode() == KeyEvent.KEYCODE_DEL
                && KeyEvent.ACTION_UP == event.getAction();

        if (listener != null) {

            if (mPressDelete) {

                try {
                    listener.onSearchClick(SearchEditText.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pressSearch) {

                /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                try {
                    listener.onSearchClick(SearchEditText.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pressSearch = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        changeFocusState(event);

        // 清空edit内容
        if (drawableDel != null && event.getAction() == MotionEvent.ACTION_UP) {
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            if (rect == null) {
                rect = new Rect();
            }
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            rect.top = rect.top + offsetY;
            rect.bottom = rect.bottom + offsetY;
            if (rect.contains(eventX, eventY)) {
                setText("");

                executeEditTextListener();
            }
        }
        // 删除按钮被按下时改变图标样式
        if (drawableDel != null && event.getAction() == MotionEvent.ACTION_DOWN) {
            eventX = (int) event.getRawX();
            eventY = (int) event.getRawY();
            if (rect == null) {
                rect = new Rect();
            }
            getGlobalVisibleRect(rect);
            rect.left = rect.right - drawableDel.getIntrinsicWidth();
            rect.top = rect.top + offsetY;
            rect.bottom = rect.bottom + offsetY;
            if (rect.contains(eventX, eventY)) {
                drawableDel = this.getResources().getDrawable(R.drawable.search_icon_cleanstr2x);
            }
            executeEditTextListener();
        } else {
            drawableDel = this.getResources().getDrawable(R.drawable.search_icon_cleanstr2x);
        }

        return super.onTouchEvent(event);
    }

    /**
     * 根据事件来处理该控件的状态和键盘
     *
     * @param event 事件
     */
    private void changeFocusState(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 只在没有获取焦点的时候进行处理
            if (!this.isFocused()) {

                setFocusable(true);
                setFocusableInTouchMode(true);

                // 隐藏软键盘
                InputMethodManager imm = (InputMethodManager) this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!imm.isActive()) {

                    KeyboardUtils.showSoftInput(this);
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable arg0) {

        if (this.length() < 1) {
            drawableDel = null;
            isIconLeft = false;
            clearFocus();
        } else {
            drawableDel = ContextCompat.getDrawable(getContext(), R.drawable.search_icon_cleanstr2x);
            isIconLeft = true;
        }
        executeEditTextListener();

    }

    private void executeEditTextListener() {

        if (listener != null) {
            try {
                listener.editTextAfterTextChanged(SearchEditText.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                              int arg3) {
        executeEditTextListener();
    }

}
