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
import askim.eratactics.activities.EquipmentActivity;
import askim.eratactics.activities.TacticsGame;

/**
 * Created by addykim on 4/21/16.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private static final String TAG = "MemberAdapter";
    //    private List<memberView> memberList;
    private List<String> memberList;

    private static ClickListener clickListener;

    private Context mContext;
    // How to add onclicklistener to adapters

    //    public MemberAdapter(Context context, List<memberView> memberList) {
    public MemberAdapter(Context context, List<String> memberList) {
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
    public void onBindViewHolder(MemberViewHolder customViewHolder, int i) {
//        memberView memberView = memberList.get(i);
        String memberView = memberList.get(i);
        customViewHolder.setItem(memberView);
    }


//        //Download image using picasso library
//        Picasso.with(mContext).load(MemberView.getThumbnail())
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
//                .into(customViewHolder.imageView);

    //Setting text view title
//        customViewHolder.textView.setText(Html.fromHtml(MemberView.getTitle()));

    public void setOnItemClickListener(ClickListener clickListener) {
        MemberAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
//        void onItemLongClick(int position, View v);
    }


    @Override
    public int getItemCount() {
        return (null != memberList ? memberList.size() : 0);
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView imageView;
        protected TextView textView;

        public void setItem(String item) {
            textView.setText(item);
            imageView.setImageResource(R.drawable.civilian_dmged);
        }

        public MemberViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.imageView = (ImageView) view.findViewById(R.id.member_thumbnail);
            this.textView = (TextView) view.findViewById(R.id.member_name);
        }

        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position = " + getPosition(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, EquipmentActivity.class);
            // TODO grab some character detail
//            intent.putExtra("level", 2);
            mContext.startActivity(intent);
        }
    }

}