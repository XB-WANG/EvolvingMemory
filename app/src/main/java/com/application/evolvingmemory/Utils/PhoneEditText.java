package com.application.evolvingmemory.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.application.evolvingmemory.R;


/*
 * 实现自定义手机号输入框，手机号码效果为344效果，例如111 1111 1111
 */
public class PhoneEditText extends androidx.appcompat.widget.AppCompatEditText implements View.OnFocusChangeListener,TextWatcher {

    // 特殊下标位置
    private static final int PHONE_INDEX_3 = 3;
    private static final int PHONE_INDEX_4 = 4;
    private static final int PHONE_INDEX_8 = 8;
    private static final int PHONE_INDEX_9 = 9;

    private String preCharSequence;

    private OnPhoneEditTextChangeListener onPhoneEditTextChangeListener;

    /**
     * 删除按钮的引用
     */
    private Drawable myClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocused;

    public PhoneEditText(Context context) {
        super(context);
        initView();
    }

    public PhoneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PhoneEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface OnPhoneEditTextChangeListener {
        /**
         * 对外提供接口监听
         * @param s 字符串
         * @param isEleven 现在是否是11位数字
         */
        void onTextChange(String s, boolean isEleven);
    }

    public void setOnPhoneEditTextChangeListener(OnPhoneEditTextChangeListener listener) {
        this.onPhoneEditTextChangeListener = listener;
    }

    private void initView() {
        //设置输入过滤器
        setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned spanned, int dstart, int dend) {
                        //在onTextChanged方法里执行setText(sb.toString());会到这里，内容一样直接返回
                        if (TextUtils.equals(source, preCharSequence)) {
                            return null;
                        }
                        //过滤掉空格和换行，dstart为13表示光标位置是11位数字+2个空格时，返回空字符
                        if (" ".equals(source.toString())
                                || source.toString().contentEquals("\n")
                                || dstart == 13) {
                            return "";
                        }
                        //这里是当光标移动到非末尾位置进行输入操作时，返回空字符
                        else if (getPhoneText().toString().length() == 11) {
                            return "";
                        } else {
                            return null;
                        }
                    }
                }, new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                                       int dend) {
                //过滤掉所有的特殊字符，这里的字母只过滤掉了wp，因为在众多机型测试时，只能输入这两个，如果不放心可以添加a-z所有字母
                String speChat = "[`~!@#$%^&*()+\\-=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？wp]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        }
        });

        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        myClearDrawable = getCompoundDrawables()[2];
        if (myClearDrawable == null) {
//        	throw new NullPointerException("You can add drawableRight attribute in XML");
            myClearDrawable = getResources().getDrawable(R.drawable.delete_selector);
        }

        myClearDrawable.setBounds(0, 0, myClearDrawable.getIntrinsicWidth(), myClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);


    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = (event.getX() > (getWidth() - getTotalPaddingRight()))
                        && (event.getX() < (getWidth() - getPaddingRight()))
                        && (event.getY() > getTotalPaddingBottom())
                        && (event.getY() < (getHeight() - getTotalPaddingTop()));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocused) {
        this.hasFocused = hasFocused;
        if (hasFocused) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? myClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);

        if(hasFocused){
            setClearIconVisible(s.length() > 0);
        }

        if (TextUtils.equals(preCharSequence, s)) {
            return;
        }

        if (null != onPhoneEditTextChangeListener) {
            onPhoneEditTextChangeListener.onTextChange(getPhoneText(),
                    getPhoneText().length() == 11);
        }

        if (s == null || s.length() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != PHONE_INDEX_3 && i != PHONE_INDEX_8 && s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == PHONE_INDEX_4 || sb.length() == PHONE_INDEX_9)
                        && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }

        //这里主要处理添加空格后的字符串，before=0为输入字符，before=1为删除字符，将光标移动到正确的位置
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }

            preCharSequence = sb.toString();
            setText(sb.toString());
            //对setSelection添加异常捕获，防止出现意外的IndexOutOfBoundsException异常
            try {
                setSelection(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    // 获得不包含空格的手机号
    public String getPhoneText() {
        String str = getText().toString();
        return replaceBlank(str);
    }

    private String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            if (m.find()) {
                dest = m.replaceAll("");
            }
        }
        return dest;
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
        this.setAnimation(shakeAnimation(3));
    }


    /**
     * 晃动动画
     * @param counts 0.5秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
        Animation translateAnimation = new TranslateAnimation(-3, 3, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

}
