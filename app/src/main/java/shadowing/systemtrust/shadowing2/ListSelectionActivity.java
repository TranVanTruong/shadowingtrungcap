package shadowing.systemtrust.shadowing2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class ListSelectionActivity extends BaseActivity {
    private ShadowingAdapter shadowingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_selection_activity);
        Intent intent = this.getIntent();
        String unit = intent.getStringExtra("trantp");

        RecyclerView productView = (RecyclerView) findViewById(R.id.list_selection);
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
//        shadowingAdapter.setOnItemClickListener(new ShadowingAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Product shadowing, int position) {
//
//            }
//        });
    }
}
