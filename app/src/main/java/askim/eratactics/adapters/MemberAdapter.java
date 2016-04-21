package askim.eratactics.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import askim.eratactics.R;
import askim.eratactics.views.MemberView;

/**
 * Created by addykim on 4/21/16.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.CustomViewHolder> {
    private List<MemberView> MemberViewList;
    private Context mContext;

    public MemberAdapter(Context context, List<MemberView> MemberViewList) {
        this.MemberViewList = MemberViewList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_member, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        MemberView MemberView = MemberViewList.get(i);

//        //Download image using picasso library
//        Picasso.with(mContext).load(MemberView.getThumbnail())
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
//                .into(customViewHolder.imageView);

        //Setting text view title
//        customViewHolder.textView.setText(Html.fromHtml(MemberView.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != MemberViewList ? MemberViewList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }
}