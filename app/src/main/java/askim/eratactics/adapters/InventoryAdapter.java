package askim.eratactics.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import askim.eratactics.R;

/**
 * Created by nunuloop on 4/28/16.
 */
public class InventoryAdapter extends BaseAdapter {

    private Context mContext;

    public InventoryAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // TODO move to resources file
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.boots, R.drawable.helmet,
            R.drawable.wizard_hat, R.drawable.melee,
            R.drawable.arrow, R.drawable.suits,
            R.drawable.wand, R.drawable.shield
    };

}
