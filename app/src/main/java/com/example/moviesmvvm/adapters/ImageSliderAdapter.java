package com.example.moviesmvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.databinding.ItemContainterSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private String[] sliderImage;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater ==null){
            layoutInflater = LayoutInflater.from(parent.getContext());

        }
        ItemContainterSliderImageBinding sliderImageBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_containter_slider_image,
                parent,
                false
        );
        return new ImageSliderViewHolder(sliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder{

        private ItemContainterSliderImageBinding itemContainterSliderImageBinding;

        public ImageSliderViewHolder(ItemContainterSliderImageBinding itemContainterSliderImageBinding) {
            super(itemContainterSliderImageBinding.getRoot());
            this.itemContainterSliderImageBinding = itemContainterSliderImageBinding;
        }

        private void bindSliderImage(String url){
            itemContainterSliderImageBinding.setImageUrl(url);


        }
    }
}
