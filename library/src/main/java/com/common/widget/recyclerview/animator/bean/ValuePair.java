package com.common.widget.recyclerview.animator.bean;

/**
 * Created by hanbing on 2016/3/14.
 */
public class ValuePair {

    /**
     * Value animate from
     */
    public float from = 0;
    /**
     * Value animate to
     */
    public float to = 0;

    /**
     * Value before animation.Always same as {@value from}.
     */
    public float before = 0;
    /**
     * Value after animation.Generally same as {@value to}.
     * If it used in remove animation, it is recommend set as {@value from}
     */
    public float after = 0;

    public ValuePair()
    {

    }

    public ValuePair(float from,float to)
    {
        this.from = from;
        this.to = to;
        this.before = from;
        this.after = to;
    }

    public ValuePair(float from, float to, float after)
    {
        this.from = from;
        this.to = to;
        this.before = from;
        this.after = after;
    }

    /**
     * Recommend use construction ValuePair(float,float,float) instead.
     * @param from
     * @param to
     * @param before
     * @param after
     */
    public ValuePair(float from, float to, float before, float after)
    {
        this.from = from;
        this.to = to;
        this.before = before;
        this.after = after;
    }
}
