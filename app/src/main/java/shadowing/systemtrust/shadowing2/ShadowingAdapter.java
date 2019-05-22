package shadowing.systemtrust.shadowing2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShadowingAdapter extends RecyclerView.Adapter<ShadowingAdapter.MyViewHolder> {
    private List<Product> mShadowingList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        private CardView unitlist;
        private ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.nameUnit);
            unitlist = view.findViewById(R.id.unitlist);
//            image = view.findViewById(R.id.image);
        }
    }

    public ShadowingAdapter(List<Product> moviesList) {
        this.mShadowingList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_unit, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Product mShadowing = mShadowingList.get(position);
        holder.title.setText(mShadowing.getName());
//        holder.image.setImageBitmap(mShadowing.getDrawable());
        holder.unitlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(mShadowing, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShadowingList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(Product shadowing, int position);

    }

}
