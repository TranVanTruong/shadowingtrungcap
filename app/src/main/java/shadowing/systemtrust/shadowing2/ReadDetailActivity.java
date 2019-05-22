package shadowing.systemtrust.shadowing2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.jean.jcplayer.JcPlayerManagerListener;
import com.example.jean.jcplayer.general.JcStatus;
import com.example.jean.jcplayer.general.errors.OnInvalidPathListener;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReadDetailActivity extends BaseActivity {
    private TextView viewread;
    private Product mProduct;
    private JcPlayerView jcplayerView;
    private int unit;
    private List<Product> allProducts;
    private ArrayList<JcAudio> jcAudios = new ArrayList<>();
    private int mPostion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selection_activity);
        viewread = findViewById(R.id.viewread);
        String name = getIntent().getStringExtra("trantp");
        unit = getIntent().getIntExtra("unit", 0);
        mPostion = getIntent().getIntExtra("postion", 0);
        DownloadFilesTask asyncTask = new DownloadFilesTask();
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, name + ".mp3");
        Log.d("trantp", "trantp :" + name);
        jcplayerView = (JcPlayerView) findViewById(R.id.jcplayerView);
        MobileAds.initialize(this,
                getString(R.string.ads_app_id));

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.ADS_DEVICE_TEST).build();
        mAdView.loadAd(adRequest);
        MobileAds.initialize(ReadDetailActivity.this, getString(R.string.ads_app_id));
        final InterstitialAd interstitialAd = new InterstitialAd(ReadDetailActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.ads_interstitial));
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice(Constant.ADS_DEVICE_TEST).build());
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }

            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jcplayerView.kill();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jcplayerView.createNotification();

    }

    private class DownloadFilesTask extends AsyncTask<String, String, Product> implements JcPlayerManagerListener, OnInvalidPathListener {

        @Override
        protected Product doInBackground(String... strings) {
            mProduct = mDatabase.getProduct(strings[0]);
            allProducts = mDatabase.getFraccionInfo(unit);
            return mProduct;
        }

        @Override
        protected void onPostExecute(Product s) {
            super.onPostExecute(s);
            viewread.setText(s.getDetail());
            for (int i = 0; i < allProducts.size(); i++) {
                jcAudios.add(JcAudio.createFromAssets("audio/" + allProducts.get(i).getName().replace("Section ", "") + ".mp3"));
            }
            jcplayerView.initPlaylist(jcAudios, this);
            jcplayerView.playAudio(jcplayerView.getMyPlaylist().get(mPostion));
            jcplayerView.setOnInvalidPathListener(this);
        }

        @Override
        public void onCompletedAudio() {

        }

        @Override
        public void onContinueAudio(@NotNull JcStatus jcStatus) {

        }

        @Override
        public void onJcpError(@NotNull Throwable throwable) {

        }

        @Override
        public void onPaused(@NotNull JcStatus jcStatus) {

        }

        @Override
        public void onPlaying(@NotNull JcStatus jcStatus) {

        }

        @Override
        public void onPreparedAudio(@NotNull JcStatus jcStatus) {
            viewread.setText(mDatabase.getProduct(jcStatus.getJcAudio().getTitle().replace("audio/", "")).getDetail());
        }

        @Override
        public void onStopped(@NotNull JcStatus jcStatus) {

        }

        @Override
        public void onTimeChanged(@NotNull JcStatus jcStatus) {

        }

        @Override
        public void onPathError(@NotNull JcAudio jcAudio) {

        }
    }

}
