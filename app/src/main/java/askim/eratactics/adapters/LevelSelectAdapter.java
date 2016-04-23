package askim.eratactics.adapters;

/**
 * Created by addykim on 4/21/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import askim.eratactics.R;

/**
 * Created by addykim on 4/21/16.
 */
public class LevelSelectAdapter extends RecyclerView.Adapter<LevelSelectAdapter.LevelSelectViewHolder> {

    private static final String TAG = "LevelSelectAdapter";
//    private List<LevelSelectView> levelList;
    private List<String> levelList;

    private Context mContext;
    // How to add onclicklistener to adapters

//    public LevelSelectAdapter(Context context, List<LevelSelectView> levelSelectList) {
    public LevelSelectAdapter(Context context, List<String> levelSelectList) {
        this.levelList = levelSelectList;
        this.mContext = context;
    }

    @Override
    public LevelSelectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_member, null);

        LevelSelectViewHolder viewHolder = new LevelSelectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LevelSelectViewHolder customViewHolder, int i) {
//        LevelSelectView levelSelectView = levelList.get(i);
        String levelSelectView = levelList.get(i);
        customViewHolder.setItem(levelSelectView);

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

    public class LevelSelectViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public void setItem(String item) {
            textView.setText(item);
            imageView.setImageResource(R.drawable.civilian_dmged);
        }

        public LevelSelectViewHolder(View view) {
            super(view);
//            view.setOnClickListener();
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }

        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}