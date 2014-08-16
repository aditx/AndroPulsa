package id.aditya.andropulsa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class HelpActivity extends Activity {
	
	private ProgressDialog progDailog;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
        progDailog = ProgressDialog.show(HelpActivity.this, "Loading","Mohon Tunggu...", true);
        progDailog.setCancelable(false);
        String pdf = "http://www.republicfurnitures.com/User_Guide.pdf";
        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
        	@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);
            
                return true;               
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });
        
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdf);
	}

}
