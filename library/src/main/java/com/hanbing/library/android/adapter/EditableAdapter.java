package com.hanbing.library.android.adapter;

import java.util.List;

/**
 * Created by hanbing on 2016/8/3
 */
public interface EditableAdapter<T> {

    /**
     * 开始编辑模式
     */
    public void beginEdit();

    /**
     * 结束编辑模式
     */
    public void endEdit();

    /**
     * 是否是编辑模式
     * @return
     */
    public boolean isEditMode();

    /**
     * 选择或取消
     * @param object
     */
    public void select(T object);

    /**
     * 全选
     */
    public void selectAll();

    /**
     * 取消全选(清空)
     */
    public void unselectAll();

    /**
     * 反选
     */
    public void inverseSelect();

    /**
     * 返回选择的列表
     * @return
     */
    public List<T> getSelectedItems();

    /**
     * 删除指定的item
     * @param list
     */
    public void delete(List<T> list);

    /**
     * 删除选中的item
     */
    public void deleteSelectedItems();

    /**
     * 是否选中
     * @param object
     * @return
     */
    public boolean isSelected(T object);
}
