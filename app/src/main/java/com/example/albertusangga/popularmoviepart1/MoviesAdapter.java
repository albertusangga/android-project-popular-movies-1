package com.example.albertusangga.popularmoviepart1;

/**
 * Created by Albertus Angga on 7/6/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.albertusangga.popularmoviepart1.utilities.MovieData;
import com.squareup.picasso.Picasso;

/**
 * {@link MoviesAdapter} exposes a list of weather forecasts to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private Context context;
    private MovieData[] mMoviesData;

    private final MoviesAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(Context context, MoviesAdapterOnClickHandler clickHandler) {
        this.context = context;
        mClickHandler = clickHandler;
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(MovieData movieData);
    }

    // COMPLETED (5) Implement OnClickListener in the ForecastAdapterViewHolder class
    /**
     * Cache of the children views for a forecast list item.
     */
    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviesImageView;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            mMoviesImageView = (ImageView) view.findViewById(R.id.iv_movie_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieData movieData = mMoviesData[adapterPosition];
            mClickHandler.onClick(movieData);
        }
    }


    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param moviesAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        MovieData movieData = mMoviesData[position];
        Picasso.with(context)
                .load(movieData.getPosterURL().toString())
                .into(moviesAdapterViewHolder.mMoviesImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mMoviesData) return 0;
        return mMoviesData.length;
    }

    public void setMovieDatas(MovieData[] movieDatas) {
        mMoviesData = movieDatas;
        notifyDataSetChanged();
    }
}
