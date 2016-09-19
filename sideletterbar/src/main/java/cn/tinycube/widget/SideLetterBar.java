package cn.tinycube.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class SideLetterBar extends View {
    private String[] letters = {"定", "热", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;

    public void setLetters(String[] letters) {
        this.letters = letters;
        postInvalidate();
    }

    public void setLetters(List<String> letters) {
        this.letters = (String[]) letters.toArray();
        postInvalidate();
    }

    public static final float DEFAULT_LETTER_SIZE = 24;
    /**
     * 字体大小
     */
    private float letterSize = DEFAULT_LETTER_SIZE;

    public static final int DEFAULT_LETTER_COLOR = 0xff8c8c8c;
    /**
     * 字体颜色
     */
    private int letterColor = DEFAULT_LETTER_COLOR;

    public static final int DEFAULT_LETTER_COLOR_SELECTED = 0xff5c5c5c;
    /**
     * 选中的字体颜色
     */
    private int letterColorSelected = DEFAULT_LETTER_COLOR_SELECTED;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
        init(null);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.tinycube_SideLetterBar);
            int n = a.getIndexCount();
            for (int i = 0; i < n; i++) {
                if (i == R.styleable.tinycube_SideLetterBar_tinycube_letterSize) {
                    letterSize = a.getDimension(i, DEFAULT_LETTER_SIZE);
                } else if (i == R.styleable.tinycube_SideLetterBar_tinycube_letterColor) {
                    letterColor = a.getColor(i, DEFAULT_LETTER_COLOR);
                } else if (i == R.styleable.tinycube_SideLetterBar_tinycube_letterColorSelected) {
                    letterColorSelected = a.getColor(i, DEFAULT_LETTER_COLOR_SELECTED);
                }
            }
            a.recycle();
        }

    }

    /**
     * 设置悬浮的文本框
     */
    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showBg) {
            canvas.drawColor(Color.TRANSPARENT);
        }

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / letters.length;
        for (int i = 0; i < letters.length; i++) {
            paint.setTextSize(letterSize);
            paint.setColor(letterColor);
            paint.setAntiAlias(true);
            if (i == choose) {
                paint.setColor(letterColorSelected);
            }
            float xPos = width / 2 - paint.measureText(letters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(letters[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        final int c = (int) (y / getHeight() * letters.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < letters.length) {
                        listener.onLetterChanged(letters[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(letters[c]);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < letters.length) {
                        listener.onLetterChanged(letters[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(letters[c]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null) {
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }

}
