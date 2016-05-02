package askim.eratactics.adapters;

/**
 * Created by addykim on 4/21/16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import askim.eratactics.R;
import askim.eratactics.activities.EquipmentActivity;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.EnumFile;
import askim.eratactics.views.Resources;

/**
 * Created by addykim on 4/21/16.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private static final String TAG = "MemberAdapter";
    private List<Adventurer> memberList;

    private static ClickListener clickListener;

    private Context mContext;
    // How to add onclicklistener to adapters

        public MemberAdapter(Context context, List<Adventurer> memberList) {
//    public MemberAdapter(Context context, List<String> memberList) {
        this.memberList = memberList;
        this.mContext = context;
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_member, null);

        MemberViewHolder viewHolder = new MemberViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder viewHolder, int i) {
        Adventurer adv = memberList.get(i);
        int imageId = Resources.getImageId(adv.getAdventureClassAsEnum());

        viewHolder.setItem(adv.getId(), adv.getAdventurerName(), imageId);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MemberAdapter.clickListener = clickListener;
    }

    public interface ClickListener { void onItemClick(int position, View v); }

    @Override
    public int getItemCount() {
        return (null != memberList ? memberList.size() : 0);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView imageView;
        protected TextView textView;
        protected Long id;

        public void setItem(Long id, String name, int image) {
            this.id = id;
            textView.setText(name);
            imageView.setImageResource(image);
        }

        public MemberViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = (ImageView) view.findViewById(R.id.member_thumbnail);
            this.textView = (TextView) view.findViewById(R.id.member_name);
        }

        public void onClick(View view) {
//            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, EquipmentActivity.class);
            intent.putExtra("advId", id);
            mContext.startActivity(intent);
        }
    }

}