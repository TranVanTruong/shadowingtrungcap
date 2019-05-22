package shadowing.systemtrust.shadowing2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class MainActivity extends BaseActivity {
    private ShadowingAdapter shadowingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView productView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setItemAnimator(new DefaultItemAnimator());
        productView.setHasFixedSize(true);
        List<Product> allProducts = mDatabase.getEmployees();
        if (allProducts.size() > 0) {
            productView.setVisibility(View.VISIBLE);
            shadowingAdapter = new ShadowingAdapter(allProducts);
            productView.setAdapter(shadowingAdapter);
            shadowingAdapter.notifyDataSetChanged();
        } else {
            productView.setVisibility(View.GONE);
        }
        MobileAds.initialize(this,
                getString(R.string.ads_app_id));

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(Constant.ADS_DEVICE_TEST).build();
        mAdView.loadAd(adRequest);
        shadowingAdapter.setOnItemClickListener(new ShadowingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product shadowing, int position) {
                Intent intent = new Intent(MainActivity.this, ListSelectionActivity.class);
                intent.putExtra("trantp", position + 1);
                startActivity(intent);
            }
        });
    }
}
