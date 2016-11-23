package com.hanbing.library.android.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/11/4
 */

public class ListItemHelper {

    public String key;
    public String title;
    public String content;

    public ListItemHelper(String title, String content) {
        this.key = title;
        this.title = title;
        this.content = content;
    }

    public ListItemHelper(String key, String title, String content) {
        this.key = key;
        this.title = title;
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<ListItemHelper> create(String[] keys, String[] titles, String[] contents) {
        if (null == titles || null == contents)
            return null;

        if (titles.length != contents.length)
            throw new IllegalArgumentException("Title's size must == Content's size.");

        int size = titles.length;

        List<ListItemHelper> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            if (null == keys)
                list.add(new ListItemHelper(titles[i], contents[i]));
            else
                list.add(new ListItemHelper(keys[i], titles[i], contents[i]));

        }

        return list;
    }

    public static List<ListItemHelper> create(String[] titles, String[] contents) {
        return create(null, titles, contents);
    }
}
