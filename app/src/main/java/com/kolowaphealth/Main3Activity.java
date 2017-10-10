package com.kolowaphealth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.kolowaphealth.fitnesshealth.HomeFragment;
import com.kolowaphealth.fitnesshealth.R;

import static android.R.attr.id;
import static android.R.attr.theme;

public class Main3Activity extends AppCompatActivity {
    WebView webview;
    AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3517746418699749/8367250510");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        webview = (WebView) findViewById(R.id.wv_test);


        MobileAds.initialize(getApplicationContext(),
                "ca-app-pub-3517746418699749~8191380917");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webview.loadUrl("javascript:window.location.reload( true )");

            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == R.id.imageButton3) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getSupportFragmentManager();

                    manager.beginTransaction().replace(R.id.book, homeFragment, homeFragment.getTag()).commit();
                } else {
                    finish();
                }

            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (webview.canGoForward()) {
                    webview.goForward();
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                } else {


                }
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (webview.canGoBack()) {
                    webview.goBack();
                    getSupportActionBar().hide();
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                } else {
                    finish();

                }
            }
        });

        webview.setWebViewClient
                (new WebViewClient());
        WebSettings webSettings = webview.getSettings();
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setEnableSmoothTransition(true);
        webview.getSettings().setJavaScriptEnabled(true);


        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://www.kolowaphealth.com");
    }

    public class WebViewClient extends
            android.webkit.WebViewClient {

        ProgressDialog pd = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pd = new ProgressDialog(Main3Activity.this);
            pd.setTitle("Please wait....");
            pd.setMessage("Page is Loading....");
            pd.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            pd.dismiss();
            super.onPageFinished(view, url);

        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("kolowaphealth.com")) {
                view.loadUrl(url);
                getSupportActionBar().hide();

            } else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
            }
            return true;
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            getSupportActionBar().show();
            view.loadUrl("file:///android_asset/Lost.html");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main3Activity.this);
            alertDialogBuilder.setTitle("You Do Not Have An Active Internet Connection!");
            alertDialogBuilder
                    .setMessage("Please Turn On Your Data Connection and Retry")
                    .setCancelable(false)
                    .setPositiveButton("Retry",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    webview.loadUrl("http://www.kolowaphealth.com");
                                }
                            })
.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (webview.canGoBack()) {
                        webview.goBack();

                    } else {
                        finish();
                    }

                }
});


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        {
            if (

                    isNetworkStatusAvailable(getApplicationContext()))

            {
                Toast.makeText(getApplicationContext(), "Internet available", Toast.LENGTH_SHORT).show();
            } else

            {
                Toast.makeText(getApplicationContext(), "Connection is not available, Please Turn On Your Data Network And Launch App Again", Toast.LENGTH_LONG).show();
            }

        }


        public boolean isNetworkStatusAvailable
                (Context context)

        {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
                if (netInfos != null)
                    if (netInfos.isConnected())
                        return true;
            }
            return false;
        }
    }


    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

                onBackPressed();
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-3517746418699749/8367250510");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }else {
                if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    mInterstitialAd.loadAd(adRequest);
                    mInterstitialAd.show();
                }
            }
        }
        return super.onOptionsItemSelected(item);


        // Inflate the menu; this adds items to the action bar if it is present.


    }



    }















