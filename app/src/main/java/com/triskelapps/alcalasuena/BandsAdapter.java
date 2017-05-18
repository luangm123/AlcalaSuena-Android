package com.triskelapps.alcalasuena;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.triskelapps.alcalasuena.model.Band;

import java.util.List;

/**
 * Created by julio on 7/07/16.
 */
public class BandsAdapter extends RecyclerView.Adapter<BandsAdapter.ViewHolder> {


    private List<Band> bands;
    private Context context;
    private OnItemClickListener itemClickListener;

    private Integer selectedNumber = -1;


    public BandsAdapter(Context context, List<Band> bands) {
        this.context = context;
        this.bands = bands;
    }

    public void setSelectedNumber(Integer number) {
        this.selectedNumber = number;
        notifyDataSetChanged();
    }

    public Integer getSelectedNumber() {
        return selectedNumber;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(context).inflate(R.layout.row_band, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Band band = getItemAtPosition(position);

        holder.tvBandName.setText(band.getName());
        holder.tvBandGenre.setText(band.getGenre());
        Picasso.with(context)
                .load(band.getImageUrlFull())
                .into(holder.imgBand);

        int color = ContextCompat.getColor(context, band.getImageUrlFull() != null ? android.R.color.white : android.R.color.black);
        holder.tvBandName.setTextColor(color);
        holder.tvBandGenre.setTextColor(color);

//        addClickListener(holder.rootView, position);
    }

    private void addClickListener(View view, final int position) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v instanceof TextView) {
                    String textNumber = ((TextView) v).getText().toString();
                    Integer number = Integer.parseInt(textNumber);
                    setSelectedNumber(number);
                }

                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bands.size();
    }

    public Band getItemAtPosition(int position) {
        return bands.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvBandName;
        public TextView tvBandGenre;
        public ImageView imgBand;
        public View rootView;

        public ViewHolder(View itemView) {

            super(itemView);

            tvBandName = (TextView) itemView.findViewById(R.id.tv_band_name);
            tvBandGenre = (TextView) itemView.findViewById(R.id.tv_band_genre);
            imgBand = (ImageView) itemView.findViewById(R.id.img_band);

            rootView = itemView;
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
