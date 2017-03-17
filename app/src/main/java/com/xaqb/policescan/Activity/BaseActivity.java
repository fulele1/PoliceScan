package com.xaqb.policescan.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;
import com.xaqb.policescan.Manager.SystemBarTintManager;
import com.xaqb.policescan.R;
import com.xaqb.policescan.Utils.AppManager;
import com.xaqb.policescan.Views.LoadingDialog;

/**
 * @author Jason_Chen on 2016/11/22.
 *         所有页面的基类
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    /**
     * 加载数据对话框
     */
    public LoadingDialog loadingDialog;
    protected Context context;
    private TextView tv_title;
    private ImageView iv_backward;
    private TextView tv_forward;
    private FrameLayout mContentLayout;
    private LinearLayout llRoot;
    private FrameLayout layout_titlebar;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReport.initCrashReport(getApplicationContext());


        /*沉浸式状态栏*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);//通知栏所需颜色
        }


        AppManager.getAppManager().addActivity(this);
        // setImmersionStatus();
        try {
            setupViews();
            context = this;
            loadingDialog = new LoadingDialog(context);
            initTitleBar();
            initViews();
            initData();
            addListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public TextView getmForwardButton() {
        return tv_forward;
    }

    public ImageView getmBackwardbButton() {
        return iv_backward;
    }

    /**
     * 初始化设置标题栏
     */
    public abstract void initTitleBar();

    /**
     * 初始化view控件
     */
    public abstract void initViews();

    /**
     * 初始化数据
     */
    public abstract void initData();

//

    /**
     * 给view添加事件监听
     */
    public abstract void addListener();

    /**
     * 加载 activity_title 布局 ，并获取标题及两侧按钮
     */
    private void setupViews() {
        super.setContentView(R.layout.ac_title);
        llRoot = (LinearLayout) findViewById(R.id.llRoot);
        layout_titlebar = (FrameLayout) findViewById(R.id.layout_titlebar);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_backward = (ImageView) findViewById(R.id.iv_backward);
        tv_forward = (TextView) findViewById(R.id.tv_forward);
        mContentLayout = (FrameLayout) findViewById(R.id.layout_content);

    }

    /**
     * 设置标题栏是否可见
     *
     * @param visibility
     */
    public void setTitleBarVisible(int visibility) {
        layout_titlebar.setVisibility(visibility);
    }

    /**
     * 设置标题栏的整体背景颜色（含沉浸式状态栏）
     *
     * @param color
     */
    public void setTitleBarBackground(int color) {
        setStatusBarBackground(color);
        layout_titlebar.setBackgroundColor(color);
    }

    /**
     * 设置返回按钮背景图片（含沉浸式状态栏）
     *
     * @param drawable
     */
    public void setBackwardButtonBackgroundDrawable(Drawable drawable, LinearLayout.LayoutParams layoutParams) {
        iv_backward.setImageDrawable(drawable);
        iv_backward.setLayoutParams(layoutParams);
    }

    /**
     * 设置返回按钮背景图片（含沉浸式状态栏）
     *
     * @param drawable
     */
    public void setBackwardButtonBackgroundDrawable(Drawable drawable) {
        iv_backward.setImageDrawable(drawable);
    }

    /**
     * 设置浸式状态栏的整体背景颜色
     *
     * @param color
     */
    public void setStatusBarBackground(int color) {
        llRoot.setBackgroundColor(color);
    }

    /**
     * 设置浸式状态栏的整体背景图片(慎用)
     *
     * @param drawable
     */
    public void setStatusBarBackgroundDrawable(Drawable drawable) {
        llRoot.setBackgroundDrawable(drawable);
    }

    /**
     * 是否显示返回按钮
     *
     * @param show true则显示
     */

    protected void showBackwardView(boolean show) {
        if (iv_backward != null) {
            if (show) {
                iv_backward.setVisibility(View.VISIBLE);
            } else {
                iv_backward.setVisibility(View.INVISIBLE);
            }
        }
    }

    protected void setBackwardViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (iv_backward != null) {
            iv_backward.setLayoutParams(layoutParams);
        }
    }

    protected void setForwardViewLayoutParams(LinearLayout.LayoutParams layoutParams) {
        if (iv_backward != null) {
            iv_backward.setLayoutParams(layoutParams);
        }
    }

    protected void showMess(String sMess, boolean bLong) {
        Toast.makeText(this, sMess, bLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    protected String readConfig(String sName) {

        SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
        return oConfig.getString(sName, "");

    }

    protected void readConfig(String[] aName, String[] aValue) {
        SharedPreferences oConfig = getSharedPreferences("config", Activity.MODE_PRIVATE);
        int i;
        for (i = 0; i < aName.length; i++)
            aValue[i] = oConfig.getString(aName[i], "");
    }


    protected void saveExtra() {
        String sName = this.getClass().getName();
        SharedPreferences oData = getSharedPreferences(sName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor oEdit = oData.edit();//获得编辑器
        saveExtraItem(oEdit);
        oEdit.putString(sName, "yes");
        oEdit.commit();//提交内容
    }


    protected void saveExtraItem(SharedPreferences.Editor oEdit) {

    }


    /**
     * 提供是否显示提交按钮
     *
     * @param show true则显示
     */
    protected void showForwardView(boolean show) {
        if (tv_forward != null) {
            if (show) {
                tv_forward.setVisibility(View.VISIBLE);
            } else {
                tv_forward.setVisibility(View.INVISIBLE);
            }
        } // else ignored
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param title 文字
     * @param show  true则显示
     */
    protected void showForwardView(CharSequence title, boolean show) {
        if (tv_forward != null) {
            if (show) {
                tv_forward.setText(title);
                tv_forward.setVisibility(View.VISIBLE);

            } else {
                tv_forward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    public void onBackward(View backwardView) {
//        Toast.makeText(this, "点击返回，可在此处调用finish()", Toast.LENGTH_LONG).show();
        finish();
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    public void onForward(View forwardView) {
        Toast.makeText(this, "点击了标题右上角按钮", Toast.LENGTH_LONG).show();
    }

    //设置标题内容
    @Override
    public void setTitle(int titleId) {
        tv_title.setText(titleId);
    }

    //设置标题内容
    @Override
    public void setTitle(CharSequence title) {
        tv_title.setText(title);
    }

    //设置标题文字颜色
    @Override
    public void setTitleColor(int textColor) {
        tv_title.setTextColor(textColor);
    }

    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        mContentLayout.removeAllViews();
        View.inflate(this, layoutResID, mContentLayout);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
        onContentChanged();
    }

    /* (non-Javadoc)
     * @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
        onContentChanged();
    }

    /**
     * 弹出Toast便捷方法
     *
     * @param charSequence
     */
    public void showToast(CharSequence charSequence) {
        if (null == toast) {
            toast = Toast.makeText(context, charSequence, Toast.LENGTH_SHORT);
        } else {
            toast.setText(charSequence);
        }
        toast.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != toast) {
            toast.cancel();
        }
//        MobclickAgent.onPause(this);
    }

    /* (non-Javadoc)
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             * 按钮点击调用的方法
             */
    @Override
    public void onClick(View v) {
    }


    protected void setEmptyView(ListView listView) {
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("暂无数据！");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContentLayout.removeAllViews();
        mContentLayout = null;
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    /**
     *
     * @param context
     * @param title
     * @return
     */
    AlertDialog alertDialog;
    public AlertDialog showAdialog(final Context context, String title, String message, String ok, String no){
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_main_info);
        TextView tvTitle = (TextView) window.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(title);
        TextView tvMessage = (TextView) window.findViewById(R.id.tv_dialog_message);
        tvMessage.setText(message);
        Button btOk = (Button) window.findViewById(R.id.btn_dia_ok);
        btOk.setText(ok);
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//结束当前Activity
               startActivity(new Intent(context,LoginActivity.class));

                //注销登录
//                Intent startMain = new Intent(Intent.ACTION_MAIN);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(startMain);
//                System.exit(0);// 退出程序
            }
        });
        Button btNo = (Button) window.findViewById(R.id.btn_dia_no);
        btNo.setText(no);
        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }

}

