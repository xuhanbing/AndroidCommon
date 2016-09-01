package com.hanbing.library.android.tool;


/**
 * 分页请求管理工具
 * Created by hanbing on 2016/3/23.
 */
public class PagingManager extends LoadingManager {

    static final int DEFAULT_PAGE_SIZE = 10;
    static final int DEFAULT_MAX_COUNT = Integer.MAX_VALUE;
    static final int DEFAULT_MAX_PAGE_INDEX = Integer.MAX_VALUE;

    byte[] mLock = new byte[0];


    /**
     * 分页请求每页数量
     */
    int mPageSize = DEFAULT_PAGE_SIZE;

    /**
     * 分页索引
     */
    int mPageIndex = 0;

    /**
     * 当前请求到的总数量
     */
    int mCurCount = 0;

    /**
     * 最大数量
     */
    int mMaxCount = 0;

    /**
     * 最大页数
     */
    int mMaxPageIndex = DEFAULT_MAX_PAGE_INDEX;

    /**
     * 页码是否从0开始
     * 默认从1开始
     */
    boolean mIndexStartFromZero = false;

    /**
     * 判断是加载更多还是刷新
     */
    boolean mLoadMore = true;

    /**
     * 是否没有数据
     */
    boolean mNoMore = false;

    public PagingManager() {
        this(DEFAULT_PAGE_SIZE);
    }

    public PagingManager(int pageSize) {
        this(pageSize, DEFAULT_MAX_COUNT);
    }

    public PagingManager(int pageSize, int maxCount) {
        this(pageSize, maxCount, false);
    }

    public PagingManager(int pageSize, boolean indexStartFromZero) {
        this(pageSize, DEFAULT_MAX_COUNT, indexStartFromZero);
    }

    public PagingManager(int pageSize, int maxCount, boolean indexStartFromZero) {
        this.mPageSize = pageSize;
        this.mIndexStartFromZero = indexStartFromZero;

        forceReset();

        setMaxCount(maxCount);
    }

    /**
     *
     */
    public void loadMore() {
        mLoadMore = true;
    }

    /**
     *
     */
    public void refresh() {
        mLoadMore = false;
    }


    /**
     * 索引加1
     */
    public void addPageIndex() {
        synchronized (mLock) {

            mPageIndex++;
        }
    }

    public void addCurCount(int count) {
        mCurCount += count;
    }

    /**
     * 索引加1，并且将请求到的数量将在总量上
     *
     * @param count
     */
    public void addIndexAndCount(int count) {
        addPageIndex();
        addCurCount(count);
    }


    /**
     * 加载更多成功后，索引+1，并且设置总数量
     *
     * @param count
     */
    public void addIndexAfterLoadMore(int count) {
        addIndexAndCount(count);
    }

    /**
     * 刷新后调用，设置索引和总数量
     *
     * @param count 数据总量
     */
    public void setIndexAfterRefresh(int count) {
        mPageIndex = getPageIndexByTotalCount(count);
        mCurCount = count;
    }

    /**
     * 第一次请求成功后设置
     * 设置分页和最大数量
     *
     * @param pageSize 每个分页数据量
     * @param maxCount 数据总量
     */
    public void setInitValues(int pageSize, int maxCount) {
//        mPageIndex = getFirstPageIndex();
        setMaxCount(pageSize, maxCount);
    }

    public void setMaxCount(int maxCount) {
        setMaxCount(mPageSize, maxCount);
    }

    /**
     * 设置最大数量
     *
     * @param pageSize 每页数量
     * @param maxCount 最大数量
     */
    public void setMaxCount(int pageSize, int maxCount) {
        this.mPageSize = pageSize;
        this.mMaxCount = maxCount;
        //计算最大页数索引
        if (0 != mPageSize && DEFAULT_MAX_COUNT != maxCount) {
            int index = maxCount / mPageSize;

            //如果不能整除+1
            index = (maxCount % pageSize == 0) ? index : (index + 1);

            //如果是从0开始，-1
            if (mIndexStartFromZero) {
                index = index - 1;
            }

            mMaxPageIndex = index;
        }
    }


    /**
     * 没有更多数据
     */
    public void setNoMore() {
        mNoMore = true;
    }

    /**
     * 判断是否是最后一页
     * 调用此方法前，最好设置 setMaxCount或者每次请求成功后，
     * 调用addPageIndexAndCount将分页索引和当前数量增加，否则判断会不准确
     *
     * @return
     */
    public boolean isLastPage() {
        return mNoMore
                || mCurCount >= mMaxCount
                || mPageIndex >= mMaxPageIndex;
    }


    /**
     * 根据总数量，计算出当前的索引
     * 如果不满一页，将忽略，下次请求的时候还是从该页开始
     * 以pageSize = 10为例
     * 如果count=5,结果为1
     * 如果count=15,结果为2
     * 如果count=20，结果为2
     *
     * @param totalCount
     */
    public int getPageIndexByTotalCount(int totalCount) {
        if (mPageSize == 0)
            return Integer.MAX_VALUE;

        int index = (totalCount + mPageSize - 1) / mPageSize - 1;

        if (mIndexStartFromZero) {
            return index;
        } else {
            return index + 1;
        }
    }

    /**
     * 获取下一页索引
     *
     * @return
     */
    public int getNextPageIndex() {
        synchronized (mLock) {
            return mPageIndex + 1;
        }
    }

    /**
     * 获取当前页索引
     *
     * @return
     */
    public int getPageIndex() {
        synchronized (mLock) {

            return mPageIndex;
        }
    }

    /**
     * 最大索引
     *
     * @return
     */
    public int getMaxPageIndex() {
        return mMaxPageIndex;
    }

    /**
     * 最大页数
     * 如果页码从0开始，页数为最大页码+1
     * @return
     */
    public int getMaxPageCount(){
        int index = mMaxPageIndex;
        if (mIndexStartFromZero)
            index ++;

        return index;
    }

    /**
     * 返回每页数量
     *
     * @return
     */
    public int getPageSize() {
        return mPageSize;
    }


    /**
     * 最大数量，即总数
     *
     * @return
     */
    public int getMaxCount() {
        return mMaxCount;
    }


    /**
     * 最大数量可用，即合法的最大数量
     * @return
     */
    public boolean isMaxCountAvaiable(){
        return DEFAULT_MAX_COUNT != mMaxCount;
    }

    /**
     * 返回当前可能页可能的最大数量
     *
     * @return
     */
    public int getCurMaxCount() {

        synchronized (mLock) {
            int index = mPageIndex;
            if (mIndexStartFromZero)
                index++;
            return index * mPageSize;
        }
    }

    /**
     * 获取总数
     *
     * @return
     */
    public int getCurCount() {
        return mCurCount;
    }

    /**
     * 设置当前总数量
     *
     * @param curCount
     */
    public void setCurCount(int curCount) {
        this.mCurCount = curCount;
    }

    /**
     * 判断是否是刷新
     *
     * @return
     */
    public boolean isRefresh() {
        return !mLoadMore;
    }

    public int getFirstPageIndex() {
        if (mIndexStartFromZero) {
            return 0;
        } else {
            return 1;
        }
    }

    private void resetPageIndex() {
        if (mIndexStartFromZero) {
            mPageIndex = -1;
        } else {
            mPageIndex = 0;
        }
    }

    /**
     *
     */
    public void forceReset() {

        super.forceReset();
        mNoMore = false;
        mCurCount = 0;
        mMaxPageIndex = DEFAULT_MAX_PAGE_INDEX;
        mMaxCount = DEFAULT_MAX_COUNT;
        resetPageIndex();
    }
}
