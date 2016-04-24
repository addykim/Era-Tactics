package askim.eratactics.adapters;

/**
 * Created by addykim on 4/21/16.
 */

import android.content.Context;
import android.content.Intent;
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
import askim.eratactics.activities.SettingsActivity;
import askim.eratactics.activities.TacticsGame;

/**
 * Created by addykim on 4/21/16.
 */
public class LevelSelectAdapter extends RecyclerView.Adapter<LevelSelectAdapter.LevelSelectViewHolder> {

    private static final String TAG = "LevelSelectAdapter";
//    private List<LevelSelectView> levelList;
    private List<String> levelList;

    private static ClickListener clickListener;

    private Context mContext;
    // How to add onclicklistener to adapters

//    public LevelSelectAdapter(Context context, List<LevelSelectView> levelSelectList) {
    public LevelSelectAdapter(Context context, List<String> levelSelectList) {
        this.levelList = levelSelectList;
        this.mContext = context;
    }

    @Override
    public LevelSelectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_level, null);

        LevelSelectViewHolder viewHolder = new LevelSelectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LevelSelectViewHolder customViewHolder, int i) {
//        LevelSelectView levelSelectView = levelList.get(i);
        String levelSelectView = levelList.get(i);
        customViewHolder.setItem(levelSelectView);
    }


//        //Download image using picasso library
//        Picasso.with(mContext).load(MemberView.getThumbnail())
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
//                .into(customViewHolder.imageView);

        //Setting text view title
//        customViewHolder.textView.setText(Html.fromHtml(MemberView.getTitle()));

    public void setOnItemClickListener(ClickListener clickListener) {
        LevelSelectAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
//        void onItemLongClick(int position, View v);
    }


    @Override
    public int getItemCount() {
        return (null != levelList ? levelList.size() : 0);
    }

    public class LevelSelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView imageView;
        protected TextView textView;

        public void setItem(String item) {
            textView.setText(item);
            // TODO set image based on status
//            imageView.setImageResource(R.drawable.civilian_dmged);
        }

        public LevelSelectViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = (ImageView) view.findViewById(R.id.levelCompletionStatus);
            this.textView = (TextView) view.findViewById(R.id.levelName);
        }

        public void onClick(View view) {
            Intent intent = new Intent(mContext, TacticsGame.class);
            Log.d(TAG, "Clicked on level " + getPosition());

            intent.putExtra("level", 2);
            // TODO select a position that isn't deprecated
//            intent.putExtra("level", getPosition());
            mContext.startActivity(intent);
        }
    }

}