package org.taitasciore.android.paymettest.comics;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.taitasciore.android.busevents.ComicClickEvent;
import org.taitasciore.android.model.Comic;
import org.taitasciore.android.model.Image;
import org.taitasciore.android.paymettest.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by roberto on 10/05/17.
 * RecyclerView.Adapter for displaying comics
 * A thumbnail is shown for each comic in a grid using either 2 or 3 columns,
 * depending on available screen width
 */

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ComicViewHolder> {

    private final ArrayList<Comic> mComics;

    public ComicAdapter(ArrayList<Comic> comics) {
        mComics = comics;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comic_row_layout, parent, false);
        return new ComicViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        Comic comic = mComics.get(position);
        Image thumbnail = comic.getThumbnail();

        if (thumbnail != null) {
            String thumbnailUrl = thumbnail.getPath() + "." + thumbnail.getExtension();
            Uri uri = Uri.parse(thumbnailUrl);
            holder.img.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return mComics.size();
    }

    class ComicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img) SimpleDraweeView img;

        /*
        Here, an EventBus is used to communicate the click event to the hosting fragment,
        sending the Comic instance so the hosting fragment can extract the necessary
        information to send to the DetailsActivity
         */
        @OnClick(R.id.img) void onComicClicked() {
            Comic comic = mComics.get(getAdapterPosition());
            EventBus.getDefault().post(new ComicClickEvent(comic));
        }

        public ComicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
