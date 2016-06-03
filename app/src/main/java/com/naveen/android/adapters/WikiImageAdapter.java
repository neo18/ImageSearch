package com.naveen.android.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.naveen.android.R;
import com.naveen.android.bean.PageDetail;
import com.naveen.android.bean.ThumbDetail;

import java.util.ArrayList;

/**
 * Created by Naveen Rawat on 01-06-2016.
 */
public class WikiImageAdapter extends RecyclerView.Adapter<WikiImageAdapter.WikiViewHolder> {

    private ArrayList<PageDetail> list;
    private LayoutInflater layoutInflater;

    public WikiImageAdapter(Context context, ArrayList<PageDetail> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public WikiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_row, parent, false);
        return new WikiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WikiViewHolder holder, int position) {
        Uri uri;
        PageDetail pageDetail = list.get(position);
        ThumbDetail thumb = pageDetail.getThumbnail();
        if (thumb != null) {
            holder.userImage.setAspectRatio((float) thumb.getWidth() / thumb.getHeight());
            uri = Uri.parse(thumb.getSource());
        } else {
            holder.userImage.setAspectRatio(1.33f);
            uri = Uri.parse("");
        }
        // Initialize a controller and attach the listener to it.
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();

        holder.userImage.setController(controller);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class WikiViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView userImage;

        public WikiViewHolder(View view) {
            super(view);

            userImage = (SimpleDraweeView) view.findViewById(R.id.image);
        }
    }
}
