package com.hanbing.library.android.view;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hanbing.library.android.util.ReflectUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持多层的RadioGroup
 */
public final class HierarchyRadioGroup extends RadioGroup implements OnHierarchyChangeListener {
    public static final String TAG ="HierarchyRadioGroup";
    @Nullable
    private OnHierarchyChangeListener mOnHierarchyChangeListener;
    @Nullable
    private CompoundButton.OnCheckedChangeListener mSuperChildOnCheckedChangeListener;

    public HierarchyRadioGroup(@NotNull Context context) {
        super(context);
        init();
    }

    public HierarchyRadioGroup(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setOnHierarchyChangeListener(this);

    }

    @Nullable
    public final OnHierarchyChangeListener getOnHierarchyChangeListener() {
        return this.mOnHierarchyChangeListener;
    }

    public final void setOnHierarchyChangeListener(@Nullable OnHierarchyChangeListener listener) {
        this.mOnHierarchyChangeListener = listener;
    }

    @Nullable
    public final CompoundButton.OnCheckedChangeListener getSuperChildOnCheckedChangeListener() {
        return this.mSuperChildOnCheckedChangeListener;
    }

    public final void setSuperChildOnCheckedChangeListener(@Nullable CompoundButton.OnCheckedChangeListener listener) {
        this.mSuperChildOnCheckedChangeListener = listener;
    }

    public final void addHierarchyRadioButtons(@Nullable View view) {
        List radios = this.findHierarchyRadioButtons(view);
        if(radios != null && !radios.isEmpty()) {
            for (int i = 0; i < radios.size(); i++) {
                this.addRadioButton((RadioButton) radios.get(i));
            }
        }
    }


    public final void removeHierarchyRadioButtons(@Nullable View view) {
        List radios = this.findHierarchyRadioButtons(view);

        if(radios != null && !radios.isEmpty()) {
            for (int i = 0; i < radios.size(); i++) {
                this.removeRadioButton((RadioButton) radios.get(i));
            }
        }
    }

    public final void addRadioButton(@Nullable RadioButton radioBtn) {
        if(radioBtn != null) {

            setViewId(radioBtn);

            //如果是直接子RadioButton，已经添加过因此跳过
            if (radioBtn.getParent() != this) {
                if(this.mSuperChildOnCheckedChangeListener == null) {
                    this.mSuperChildOnCheckedChangeListener = (CompoundButton.OnCheckedChangeListener)ReflectUtils.getValue(this, "mChildOnCheckedChangeListener", (Object)null);
                }

                radioBtn.setOnCheckedChangeListener(mSuperChildOnCheckedChangeListener);
            }

            if (radioBtn.isChecked()) {
                check(radioBtn.getId());
            }
        }
    }

    public final void removeRadioButton(@Nullable RadioButton radioBtn) {
        if(radioBtn != null) {
            if (radioBtn.getId() == getCheckedRadioButtonId()) {
                clearCheck();
            }
            radioBtn.setOnCheckedChangeListener(null);
        }
    }

    private void setViewId(View view) {
        int id = view.getId();
        if(id == View.NO_ID) {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                id = View.generateViewId();
            } else {
                id = view.hashCode();
            }
            view.setId(id);
        }
    }
    public void onChildViewAdded(@Nullable View parent, @Nullable View child) {
        if (parent == this && child instanceof ViewGroup && !(child instanceof RadioGroup)) {
            //除了子RadioGroup中所有层级的RadioButton
            addHierarchyRadioButtons(child);
        } else if (parent == this && child instanceof RadioButton) {
            //确保选中
            if (((RadioButton)child).isChecked())
                check(child.getId());
        }
        if(this.mOnHierarchyChangeListener != null) {
            mOnHierarchyChangeListener.onChildViewAdded(parent, child);
        }

    }

    public void onChildViewRemoved(@Nullable View parent, @Nullable View child) {
        if (parent == this && child instanceof ViewGroup && !(child instanceof RadioGroup)) {
            //除了子RadioGroup中所有层级的RadioButton
            removeHierarchyRadioButtons(child);
        }
        if(this.mOnHierarchyChangeListener != null) {
            mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
        }

    }

    @Nullable
    public final List findHierarchyRadioButtons(@Nullable View view) {
        if (null == view)
            return null;
        List radios = new ArrayList();
        if ((view instanceof RadioButton) ) {
            radios.add(view);
            return radios;
        }

        if (!(view instanceof ViewGroup)) {
            return radios;
        }

        ViewGroup viewGroup = (ViewGroup)view;

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            if (child instanceof RadioButton) {
                radios.add(child);
            } else if (child instanceof ViewGroup) {
                if (!(child instanceof RadioGroup)) {
                    List list = findHierarchyRadioButtons(child);
                    if (null != list && !list.isEmpty()) {
                        radios.addAll(list);
                    }
                }
            }
        }

        return radios;
    }


}