package com.aograph.androidtest.testactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aograph.androidtest.R;

/**
 * Created by tangqipeng
 * 2019-09-24
 * email: tangqipeng@aograph.com
 */
public class WebActvity extends AppCompatActivity {
    private static final String TAG = WebActvity.class.getName();

    private WebView webView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = (WebView) findViewById(R.id.webview);

        String url = getIntent().getStringExtra("url");
        if (url != null && !url.equals("")) {
            webView.setWebChromeClient(webChromeClient);
            webView.setWebViewClient(webViewClient);

            setWebVew(webView);

            webView.addJavascriptInterface(this, "aograph");

            webView.loadUrl(url);
//            "widowns.android.a"

        }
    }

    private void setWebVew(WebView webView) {
        WebSettings settings = webView.getSettings();//获得浏览器设置
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);  //支持js

        settings.setBlockNetworkImage(false);
//        settings.setDatabaseEnabled(true);

        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小

        settings.setSupportZoom(true);  //支持缩放

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重      新布局

        settings.supportMultipleWindows();  //多窗口

        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存

        settings.setAllowFileAccess(true);  //设置可以访问文件

        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点

        settings.setBuiltInZoomControls(true); //设置支持缩放

        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口

        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        settings.setSavePassword(true);
        settings.setSaveFormData(true);
//        settings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");

    }


    @JavascriptInterface
    public void btnClick() {
//        Toast.makeText(WebActvity.this, "点击btnClick生效", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, TestActivity2.class);
        startActivity(intent);
    }

    @JavascriptInterface
    public void setEpMessage(String data) {
        Toast.makeText(WebActvity.this, "点击sendMessage生效", Toast.LENGTH_LONG).show();
        Log.i(TAG, "data="+data);
    }

    @JavascriptInterface
    public void startCollect() {
        Toast.makeText(WebActvity.this, "startCollect", Toast.LENGTH_LONG).show();
    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    };

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            return true;
        }

        //获取网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.i("ansen", "网页标题:" + title);
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("ansen", "是否有上一个页面:" + webView.canGoBack());
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //释放资源
        webView.destroy();
        webView = null;
    }
}
