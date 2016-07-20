package com.hanbing.mytest.activity.action;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hanbing.library.android.bind.ObjectBinder;
import com.hanbing.library.android.bind.annotation.BindView;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.TimeUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.model.Customer;
import com.hanbing.model.CustomerDao;
import com.hanbing.model.DaoMaster;
import com.hanbing.model.DaoSession;
import com.hanbing.mytest.R;
import com.hanbing.mytest.common.ConstantValues;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDb extends AppCompatActivity {


    @BindView(R.id.rg_db)
    RadioGroup mRadioGroup;

    @BindView(R.id.tv_log)
    TextView mTvLog;

    @BindView(R.id.et_count)
    EditText mEtCount;

    String root = "";

    CustomerDao mCustomerDao;
    DbManager mDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);

        ObjectBinder.bind(this);


        root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + ConstantValues.FOLDER_NAME
                + "/db";

        new File(root).mkdirs();

        DaoMaster.OpenHelper openHelper = new DaoMaster.DevOpenHelper(this, root + "/greenDao1.db", null) {
        };

        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());

        mCustomerDao = daoMaster.newSession().getCustomerDao();


        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbDir(new File(root));
        daoConfig.setDbName("xutils.db");
        daoConfig.setAllowTransaction(true);
        mDbManager = x.getDb(daoConfig);

        reset(null);

    }
    boolean is() {
        return mRadioGroup.getCheckedRadioButtonId() == R.id.rb_xutils;
    }

    int getCount() {
        String count = mEtCount.getText().toString();

        try {
            return Integer.valueOf(count);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 1;
        }
    }


    long startTime = 0;
    void startTime() {
        startTime = SystemClock.elapsedRealtime();
    }

    long endTime() {
        return SystemClock.elapsedRealtime() - startTime;
    }

    public void log(String action, long cost) {

        int count = getCount();
        String tag = "";
        if (is()) {
            tag = "xUtils";
        } else {
            tag = "greenDao";
        }

        String msg = tag + " " + action + ", cost = " + cost + ", average = " + (cost * 1.0f / count) +  "\n";

        LogUtils.e(msg);
        mTvLog.append(msg);
    }

    public void queryAll(View view) {

        int count = getCount();

        startTime();
        if (is()) {

            for (int i = 0; i < count; i++) {
                try {
                    List<Customer> all = mDbManager.findAll(Customer.class);
//                    LogUtils.e("count = " + (null == all ? 0 : all.size()));
//                    if (null != all && all.size() > 0) {
//                        Customer customer = all.get(0);
//                        LogUtils.e("id = " + customer.getId() + ", name = " + customer.getName());
//                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

        } else {

            for (int i = 0; i < count; i++) {
                    List<Customer> all = mCustomerDao.loadAll();
//                LogUtils.e("count = " + (null == all ? 0 : all.size()));
//                    if (null != all && all.size() > 0) {
//                        Customer customer = all.get(0);
//                        LogUtils.e("id = " + customer.getId() + ", name = " + customer.getName());
//                    }
            }

        }

        log("queryAll", endTime());
    }

    public void mod(View view) {
        int count = getCount();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("mod " + TimeUtils.getTime());
        if (is()) {


            try {
                customer = mDbManager.findFirst(Customer.class);

            } catch (DbException e) {
                e.printStackTrace();
            }


            if (null == customer) {
                customer = new Customer();
                customer.setId(1L);
            }
            customer.setName("mod " + TimeUtils.getTime());

            startTime();

            for (int i = 0; i < count; i++) {
                try {
                    mDbManager.update(customer, "name");
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            log("mod", endTime());
        } else {

            customer = mCustomerDao.loadByRowId(1);
            if (null == customer) {
                customer = new Customer();
                customer.setId(1L);
            }
            customer.setName("mod " + TimeUtils.getTime());

            startTime();

            for (int i = 0; i < count; i++) {
                mCustomerDao.update(customer);
            }


            log("mod", endTime());

        }


    }

    public void delete(View view) {
        int count = getCount();
        startTime();
        if (is()) {

            for (int i = 0; i < count; i++) {
                try {
                    mDbManager.delete(Customer.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }


        } else {
            for (int i = 0; i < count; i++) {
                mCustomerDao.deleteAll();
            }
        }

        log("delete", endTime());
    }

    public void insert(View view) {
        Customer customer = new Customer();
        customer.setName("insert " + TimeUtils.getTime());
        int count = getCount();

        if (is()) {
            startTime();
            for (int i = 0; i < count; i++) {
                try {
                    mDbManager.save(customer);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            log("insert", endTime());
        } else {
            long total = mCustomerDao.count();
            startTime();
            for (int i = 0; i < count; i++) {
                customer.setId(i + total);
                mCustomerDao.insert(customer);
            }
            log("insert", endTime());
        }


    }

    public void query(View view) {
        int count = getCount();
        startTime();
        if (is()) {

            for (int i = 0; i < count; i++) {
                try {
                    mDbManager.findById(Customer.class, 10);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }


        } else {
            for (int i = 0; i < count; i++) {
                mCustomerDao.loadByRowId(10);
            }
        }

        log("query", endTime());
    }

    public void reset(View view) {

        mCustomerDao.deleteAll();

        try {
            mDbManager.delete(Customer.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mDbManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCustomerDao.getDatabase().close();
    }


}
