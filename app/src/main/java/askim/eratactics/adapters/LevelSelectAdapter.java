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
import askim.eratactics.activities.TacticsGame;
import askim.eratactics.gamelogic.LevelGenerator;

/**
 * Created by addykim on 4/21/16.
 */
public class LevelSelectAdapter extends RecyclerView.Adapter<LevelSelectAdapter.LevelSelectViewHolder> {

    private static final String TAG = "LevelSelectAdapter";
    private List<LevelGenerator> levelList;

    private static ClickListener clickListener;

    private Context mContext;
    // How to add onclicklistener to adapters

    public LevelSelectAdapter(Context context, List<LevelGenerator> levelSelectList) {
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
        LevelGenerator levelSelectView = levelList.get(i);
        customViewHolder.setItem(levelSelectView.getId(), levelSelectView);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        LevelSelectAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void updateList(List<LevelGenerator> levelList) {
        Log.d(TAG, "Updating adapter's list");
        this.levelList = levelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != levelList ? levelList.size() : 0);
    }

    public class LevelSelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected Long id;
        protected ImageView imageView;
        protected TextView textView;
        protected boolean clickable;

        public void setItem(Long id, LevelGenerator level) {
            this.id = id;
            textView.setText(level.getName());
            if (level.getLocked()) {
                imageView.setImageResource(R.drawable.lock);
                clickable = false;
            } else {
                if (level.getCleared()) {
                    imageView.setImageResource(R.drawable.cleared);
                } else {
                    imageView.setImageResource(R.drawable.new_level);
                }
                clickable = true;
            }
        }

        public LevelSelectViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = (ImageView) view.findViewById(R.id.levelCompletionStatus);
            this.textView = (TextView) view.findViewById(R.id.levelName);
        }

        public void onClick(View view) {
            Log.d(TAG, "Clicked on level " + id);
            if (clickable) {
                Intent intent = new Intent(mContext, TacticsGame.class);
                intent.putExtra("level", id);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, "This level is not available yet", Toast.LENGTH_SHORT).show();
            }
        }
    }
}