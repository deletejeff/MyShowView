package com.waakka.myshowview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.waakka.myshowview.adapter.MyAdapter;
import com.waakka.myshowview.adapter.MyInvoiceAdapter;
import com.waakka.myshowview.been.InvoiceBeen;
import com.waakka.myshowview.been.JsonBeen;
import com.waakka.myshowview.been.Params;
import com.waakka.myshowview.jpush.GlideImageLoader;
import com.waakka.myshowview.util.JsonUtil;
import com.waakka.myshowview.util.ZXingUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

import static com.waakka.myshowview.TagAliasOperatorHelper.ACTION_SET;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llContent;
    private LinearLayout llBanner;
    private LinearLayout llInvoice;
    private Banner banner;



    private String alias = null;
    public static int sequence = 1;
    private String serialNumber;

    public static boolean isForeground = false;


    /**
     * 首页控件
     */
    private TextView tvName;
    private TextView tvCode;
    private TextView tvTime;
    private TextView tvChargeNum;
    private TextView tvChargeMoney;

    private TextView t1;
    private TextView t2;
    private TextView t3;
    private TextView t4;
    private TextView t5;
    private TextView t6;
    private TextView t7;
    private TextView t8;
    private ImageView ivQR;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;


    /**
     * 发票控件
     */
    private TextView tv_payTime;
    private TextView tv_username;
    private TextView tv_counterNum;

    private RecyclerView recyclerViewInvoice;
    private MyInvoiceAdapter myInvoiceAdapter;

    private TextView tv_4_1;
    private TextView tv_4_2;
    private TextView tv_4_3;
    private TextView tv_4_4;
    private TextView tv_5_1;
    private TextView tv_5_2;


    private SharedPreferences sp;
    private final String SP_ALIAS = "spAlias";
    private final String SP_IS_SET_ALIAS = "isSetAlias";
    private String spAlias = "";
    private boolean isSetAlias = false;
    private ProgressDialog pd;

    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/myfont.ttf");

        sp = getSharedPreferences("showViewSP", Activity.MODE_PRIVATE);
        pd = new ProgressDialog(MainActivity.this);
        pd.setCancelable(false);



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
        full(true);


        initView();
        setJPush();
        setAlias();
        initEvent();

    }

    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void setJPush() {
        JPushInterface.init(getApplicationContext());
        //推送收到的信息广播发送
        //注册收到的广播推送
        registerMessageReceiver();
    }


    private void initEvent() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("TAG", "llBanner clicked");
//                showContent();
                showInvoice();
            }
        });

    }


    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            showBanner();
        }
    };

    private void setAlias() {
        spAlias = sp.getString(SP_ALIAS,"");
        isSetAlias = sp.getBoolean(SP_IS_SET_ALIAS,false);
        serialNumber = android.os.Build.SERIAL;
        Log.e("TAG","serialNumber="+serialNumber);
        Log.e("TAG","spAlias="+spAlias);
        Log.e("TAG","isSetAlias="+isSetAlias);

        /**
         * 检查sp中sn是否与本次获取一致
         * 不一致或者未注册别名则再次注册
         */
        if (!isSetAlias || (!serialNumber.equals(spAlias))){
            pd.setMessage("正在设置推送别名，请稍后。。。");
            pd.show();

            new Thread(){
                @Override
                public void run() {
                    Message msg;
                    int count = 0;
                    while (!Info.isSetAlias){
                        count++;
                        if (count != 1){
                            try {
                                sleep(18000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        msg = Message.obtain();
                        msg.what = 333;
                        aliasHandler.sendMessage(msg);
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        msg = Message.obtain();
                        msg.what = 222;
                        aliasHandler.sendMessage(msg);
                    }
                    msg = Message.obtain();
                    msg.what = 111;
                    aliasHandler.sendMessage(msg);
                }
            }.start();
        }else{
            Toast.makeText(MainActivity.this,"推送别名已设置成功！",Toast.LENGTH_SHORT).show();
        }

    }


    private Handler aliasHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 111:
                    //设置成功，更新sp，停止pd
                    Log.e("TAG","设置成功，更新sp，停止pd");
                    if (pd.isShowing()){
                        pd.dismiss();
                        pd = null;
                    }
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean(SP_IS_SET_ALIAS,true);
                    editor.putString(SP_ALIAS,Info.alias);
                    editor.commit();
                    Toast.makeText(MainActivity.this,"设置成功！",Toast.LENGTH_SHORT).show();
                    break;
                case 222:
                    //设置失败，更新pd重新设置
                    Log.e("TAG","设置失败，更新pd重新设置");
                    pd.setMessage("设置推送别名失败，重新设置，请稍后。。。");
                    pd.show();
                    break;
                case 333:
                    //开始设置别名
                    Log.e("TAG","开始设置别名");
                    alias = serialNumber;
                    TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
                    tagAliasBean.action = ACTION_SET;
                    sequence++;
                    tagAliasBean.alias = alias;
                    tagAliasBean.isAliasAction = true;
                    TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
                    break;
            }
        }
    };

    private List<String> images = new ArrayList<>();

    private void initView() {
        tvName = findViewById(R.id.tv_name);
        tvCode = findViewById(R.id.tv_meterCode);
        tvTime = findViewById(R.id.tv_time);
        tvChargeNum = findViewById(R.id.tv_chargeNum);
        tvChargeMoney = findViewById(R.id.tv_chargeMoney);
        ivQR = findViewById(R.id.iv_code);
        t1 = findViewById(R.id.t_1);
        t2 = findViewById(R.id.t_2);
        t3 = findViewById(R.id.t_3);
        t4 = findViewById(R.id.t_4);
        t5 = findViewById(R.id.t_5);
        t6 = findViewById(R.id.t_6);
        t7 = findViewById(R.id.t_7);
        t8 = findViewById(R.id.t_8);
        recyclerViewInvoice = findViewById(R.id.recycler_invoice);
        tv_payTime = findViewById(R.id.tv_payTime);
        tv_username = findViewById(R.id.tv_username);
        tv_counterNum = findViewById(R.id.tv_counterNum);
        tv_4_1 = findViewById(R.id.tv_4_1);
        tv_4_2 = findViewById(R.id.tv_4_2);
        tv_4_3 = findViewById(R.id.tv_4_3);
        tv_4_4 = findViewById(R.id.tv_4_4);
        tv_5_1 = findViewById(R.id.tv_5_1);
        tv_5_2 = findViewById(R.id.tv_5_2);



//        tvName.setTypeface(typeface);
//        tvCode.setTypeface(typeface);
//        tvTime.setTypeface(typeface);
//        tvChargeNum.setTypeface(typeface);
//        tvChargeMoney.setTypeface(typeface);
//        t1.setTypeface(typeface);
//        t2.setTypeface(typeface);
//        t3.setTypeface(typeface);
//        t4.setTypeface(typeface);
//        t5.setTypeface(typeface);
//        t6.setTypeface(typeface);
//        t7.setTypeface(typeface);
//        t8.setTypeface(typeface);
        SpannableString m2 = new SpannableString("m3，合计金额");
        m2.setSpan(new RelativeSizeSpan(0.5f), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//一半大小
        m2.setSpan(new SuperscriptSpan(), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);   //上标
        t7.append(m2);

        recyclerView = findViewById(R.id.recycler);



//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562404176350&di=560eaf24c152062959649fee89c90b4d&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw700h466%2F20171209%2F7f65-fypnsip2468053.jpg");
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562404176349&di=a8468bf5f39c73c116176afc77157f9a&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn%2Fw800h940%2F20171212%2F3005-fypnsiq0521018.jpg");
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562404176347&di=4d081bb4131fb2163fa267871aae3bf0&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2Fe218d8953c1943f33acbe4000702b280b730740c.jpg");
//        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1562404271542&di=3204dfa88b89b9eb82fe443e7fe6d184&imgtype=0&src=http%3A%2F%2Fn.sinaimg.cn%2Fsinacn10104%2F173%2Fw593h380%2F20190526%2F0e7e-hxntqyy7218092.jpg");
        images.add("http://5b0988e595225.cdn.sohucs.com/images/20180713/6d509bb9224847c29208fbe90238ce3b.jpeg");
        images.add("http://5b0988e595225.cdn.sohucs.com/images/20180713/6d509bb9224847c29208fbe90238ce3b.jpeg");
        images.add("http://5b0988e595225.cdn.sohucs.com/images/20180713/6d509bb9224847c29208fbe90238ce3b.jpeg");
        llBanner = findViewById(R.id.ll_banner);
        llContent = findViewById(R.id.ll_content);
        llInvoice = findViewById(R.id.ll_2);
        banner = findViewById(R.id.banner);
        banner.setDelayTime(10000);
        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
    }

    private void showBanner(){
        Log.e("TAG","showBanner");
        llBanner.setVisibility(View.VISIBLE);
        llContent.setVisibility(View.GONE);
        llInvoice.setVisibility(View.GONE);
        banner.setClickable(true);
    }
    private void showContent(){
        banner.setClickable(false);
        Log.e("TAG","showContent");
        llBanner.setVisibility(View.GONE);
        llContent.setVisibility(View.VISIBLE);
        llInvoice.setVisibility(View.GONE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendMessage(myHandler.obtainMessage());
            }
        },60000);
    }
    private void showInvoice(){
        banner.setClickable(false);
        Log.e("TAG","showInvoice");
        llBanner.setVisibility(View.GONE);
        llContent.setVisibility(View.GONE);
        llInvoice.setVisibility(View.VISIBLE);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendMessage(myHandler.obtainMessage());
            }
        },60000);
    }


    private void setData(JsonBeen been){
        tvName.setText("用户名：" + been.getUsername());
        tvCode.setText("用户表号：" + been.getMeterCode());
        tvTime.setText("时间段：" + been.getFromTime() + "  ~  " + been.getToTime());
        tvChargeNum.setText(" " + doubleToString(been.getTotalChargeNum()) + " ");
        tvChargeMoney.setText(" " + doubleToString(been.getTotalChargeMoney()) + " ");


        if (been.getData() != null){
            myAdapter = new MyAdapter(MainActivity.this,been,typeface);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this );
            //设置布局管理器
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
        }

        showContent();

    }

    private void setInvoiceData(InvoiceBeen been){
        tv_payTime.setText(been.getPayTime());
        tv_username.setText(been.getUsername());
        tv_counterNum.setText(been.getMeterCode());
        tv_4_1.setText(been.getInvoiceExtractionCode());
        tv_4_2.setText(doubleToString(been.getTotalAccountReceivable()) + " 元");
        tv_4_3.setText(doubleToString(been.getTotalAccountPayable()) + " 元");
        tv_4_4.setText(been.getOpName());
        tv_5_1.setText(been.getInvoiceUr());
        tv_5_2.setText(been.getInvoiceDateExpire());
        ivQR.setImageBitmap(ZXingUtils.createQRImage(been.getQrcodeUrl(),190,190));

        myInvoiceAdapter = new MyInvoiceAdapter(MainActivity.this,been,typeface);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        //设置布局管理器
        recyclerViewInvoice.setLayoutManager(layoutManager);
        recyclerViewInvoice.setAdapter(myInvoiceAdapter);
        showInvoice();
    }

    public static String doubleToString(double num){
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        if (pd != null && pd.isShowing()){
            pd.dismiss();
            pd = null;
        }
        super.onDestroy();
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.waakka.myshowview.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    Log.e("TAG","收到推送消息===>" + messge);
                    if (messge.contains("sign")){
                        //发票界面推送
                        Log.e("TAG","发票界面推送,开始解析数据");
                        InvoiceBeen invoiceBeen = JsonUtil.getInvoiceBeen(messge);
                        Log.e("TAG","解析发票json==>" + invoiceBeen.toString());
                        setInvoiceData(invoiceBeen);
                    }else{
                        //收费界面推送
                        Log.e("TAG","收费界面推送,开始解析数据");
                        getData(messge);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    private void getData(String id){
        RequestParams params = new RequestParams("http://www.tdnforce.com:8080/data/data_receive/queryById");

        //MD5生成sign
        String sign = md5(id + "gasTransDetail");
        Params p = new Params(id,sign);
        String pStr = new Gson().toJson(p);
        Log.e("TAG","请求参数==>" + pStr);

        params.setAsJsonContent(true);
        params.setBodyContent(pStr);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG","onSuccess==>" + result);
                Log.e("TAG","开始解析");
                JsonBeen been = JsonUtil.getBeen(result);
                if (been != null){
                    Log.e("TAG","been===>" + been.toString());
                    Log.e("TAG","设置界面");
                    setData(been);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("TAG","onError===>" + ex.getLocalizedMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e("TAG","cancelled");
            }

            @Override
            public void onFinished() {
                Log.e("TAG","onFinished");
            }
        });

    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result.append(temp);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
