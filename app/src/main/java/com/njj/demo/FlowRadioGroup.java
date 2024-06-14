package com.njj.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;


public class FlowRadioGroup extends RadioGroup {
    public FlowRadioGroup(Context context) {
        super(context);
    }

    public FlowRadioGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void onMeasure(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != View.GONE) {
                childAt.measure(0, 0);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                i4 += measuredWidth;
                int i7 = (i5 * measuredHeight) + measuredHeight;
                if (i4 > size) {
                    i5++;
                    i4 = measuredWidth;
                    i3 = (i5 * measuredHeight) + measuredHeight;
                } else {
                    i3 = i7;
                }
            }
        }
        setMeasuredDimension(size, i3);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                i6 += measuredWidth;
                int i9 = (i7 * measuredHeight) + measuredHeight;
                if (i6 > i5) {
                    i7++;
                    i9 = (i7 * measuredHeight) + measuredHeight;
                    i6 = measuredWidth;
                }
                childAt.layout(i6 - measuredWidth, i9 - measuredHeight, i6, i9);
            }
        }
    }
}