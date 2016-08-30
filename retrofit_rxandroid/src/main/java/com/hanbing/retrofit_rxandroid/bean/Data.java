package com.hanbing.retrofit_rxandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanbing on 2016/8/30
 */
public class Data implements Serializable {
    int count;
    int start;
    int total;
    List<Subject> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
