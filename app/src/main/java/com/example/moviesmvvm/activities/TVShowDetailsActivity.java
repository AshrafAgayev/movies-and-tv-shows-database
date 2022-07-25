package com.example.moviesmvvm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.adapters.ImageSliderAdapter;

import com.example.moviesmvvm.viewmodels.TVShowDetailsViewModel;

public class TVShowDetailsActivity extends AppCompatActivity {

    private com.example.moviesmvvm.databinding.ActivityTvshowDetailsBinding activityTvshowDetailsBinding;
    private TVShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvshowDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_details);
        doInitialization();
    }

    private void doInitialization() {
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TVShowDetailsViewModel.class);
        activityTvshowDetailsBinding.imageBack.setOnClickListener(view -> onBackPressed());
        getDetails();

    }

    private void getDetails() {
        activityTvshowDetailsBinding.setIsLoading(true);
        String tvShowId = String.valueOf(getIntent().getIntExtra("id", -1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(
                this, tvShowDetailsResponse -> {
                    activityTvshowDetailsBinding.setIsLoading(false);
                    if (tvShowDetailsResponse.getTvShow() != null) {
                        if (tvShowDetailsResponse.getTvShow().getPictures() != null) {
                            loadImages(tvShowDetailsResponse.getTvShow().getPictures());
                        }
                        activityTvshowDetailsBinding.setTvShowImageURL(
                                tvShowDetailsResponse.getTvShow().getImagePath()
                        );
                        activityTvshowDetailsBinding.imageTvShow.setVisibility(View.VISIBLE);

                        activityTvshowDetailsBinding.setDescription(
                                String.valueOf(HtmlCompat.fromHtml(tvShowDetailsResponse.getTvShow().getDescription(),
                                        HtmlCompat.FROM_HTML_MODE_LEGACY))
                        );

                        // activating read more button
                        activityTvshowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.textReadMore.setVisibility(View.VISIBLE);
                        activityTvshowDetailsBinding.textReadMore.setOnClickListener(view -> {
                            if(activityTvshowDetailsBinding.textReadMore.getText().equals("Read More")){
                                activityTvshowDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                                activityTvshowDetailsBinding.textDescription.setEllipsize(null);
                                activityTvshowDetailsBinding.textReadMore.setText(R.string.read_less);
                            }else{
                                activityTvshowDetailsBinding.textDescription.setMaxLines(4);
                                activityTvshowDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                                activityTvshowDetailsBinding.textReadMore.setText(R.string.read_more);
                            }
                        });
                        //////


                        loadBasicTVShowDetails();
                    }

                    activityTvshowDetailsBinding.setIsLoading(false);
                });

    }

    private void loadImages(String[] sliderImages) {

        activityTvshowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTvshowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvshowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setupSliderIndicators(sliderImages.length);
        activityTvshowDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new ActionMenuView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.background_slicer_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            activityTvshowDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        activityTvshowDetailsBinding.layoutSliderIndicators.setVisibility(View.VISIBLE);
        setCurrentSliderIndicator(0);

    }

    private void setCurrentSliderIndicator(int position) {
        int childCount = activityTvshowDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) activityTvshowDetailsBinding.layoutSliderIndicators.getChildAt(i);

            if (i == position) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slicer_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_slicer_indicator_inactive)
                );
            }
        }
    }

    private void loadBasicTVShowDetails(){

        activityTvshowDetailsBinding.setTvShowName(getIntent().getStringExtra("name"));
        activityTvshowDetailsBinding.setStartDate(getIntent().getStringExtra("startDate"));
        activityTvshowDetailsBinding.setNetworkCountry(getIntent().getStringExtra("network") + " ("+
                getIntent().getStringExtra("country")+")");
        activityTvshowDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activityTvshowDetailsBinding.tvShowName.setVisibility(View.VISIBLE);
        activityTvshowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
    }

}