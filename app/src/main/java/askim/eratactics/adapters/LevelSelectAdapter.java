package askim.eratactics.adapters;

/**
 * Created by addykim on 4/21/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import askim.eratactics.R;
import askim.eratactics.views.LevelSelectView;

/**
 * Created by addykim on 4/21/16.
 */
public class LevelSelectAdapter extends RecyclerView.Adapter<LevelSelectAdapter.CustomViewHolder> {
    private List<LevelSelectView> levelList;
    private Context mContext;

    public LevelSelectAdapter(Context context, List<LevelSelectView> levelSelectList) {
        this.levelList = levelSelectList;
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
        LevelSelectView levelSelectView = levelList.get(i);

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
        return (null != levelList ? levelList.size() : 0);
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