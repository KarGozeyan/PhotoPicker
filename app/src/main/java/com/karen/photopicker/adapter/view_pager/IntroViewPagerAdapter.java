package com.karen.photopicker.adapter.view_pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.karen.photopicker.R;
import com.karen.photopicker.models.intro_item.IntroItem;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {
    private Context context;

    public IntroViewPagerAdapter(Context context, List<IntroItem> items) {
        this.context = context;
        this.items = items;
    }

    private List<IntroItem> items;

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        IntroItem item = items.get(position);
        View layout = LayoutInflater.from(context).inflate(R.layout.intro_item, container, false);
        ImageView image = layout.findViewById(R.id.intro_item_image);
        TextView text = layout.findViewById(R.id.intro_item_text);

        image.setImageDrawable(context.getResources().getDrawable(item.getImage()));
        text.setText(item.getText());

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}