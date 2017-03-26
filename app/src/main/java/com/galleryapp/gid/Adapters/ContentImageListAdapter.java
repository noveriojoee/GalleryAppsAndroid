package com.galleryapp.gid.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.galleryapp.gid.gidgalleryapp.R;
import com.galleryapp.gid.models.ImageModel;
import java.util.ArrayList;

/**
 * Created by noverio.joe on 3/22/17.
 */


public class ContentImageListAdapter extends RecyclerView.Adapter<ContentImageListAdapter.ViewHolder> {
    private ArrayList<ImageModel> ListOfImage;
    private Context context;

    public ContentImageListAdapter(Context context, ArrayList<ImageModel> ListOfImage) {
        this.ListOfImage = ListOfImage;
        this.context = context;
    }

    @Override
    public ContentImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentImageListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(ListOfImage.get(i).getCaption());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.img.setImageBitmap((ListOfImage.get(i).getImage()));
    }

    @Override
    public int getItemCount() {
        return ListOfImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}