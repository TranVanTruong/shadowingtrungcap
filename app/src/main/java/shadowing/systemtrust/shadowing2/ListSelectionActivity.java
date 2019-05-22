package shadowing.systemtrust.shadowing2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

public class ListSelectionActivity extends BaseActivity {
    private ShadowingAdapter shadowingAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int unit = getIntent().getIntExtra("trantp", 0);

        RecyclerView productView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setItemAnimator(new DefaultItemAnimator());
        productView.setHasFixedSize(true);
        List<Product> allProducts = mDatabase.getFraccionInfo(unit);
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
                Intent intent = new Intent(ListSelectionActivity.this, ReadDetailActivity.class);
                intent.putExtra("trantp", shadowing.getName().replace("Section ", ""));
                intent.putExtra("unit", unit);
                intent.putExtra("postion", position);
                startActivity(intent);
            }
        });
    }
}
