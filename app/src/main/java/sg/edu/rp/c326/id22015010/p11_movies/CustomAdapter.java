package sg.edu.rp.c326.id22015010.p11_movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<movie> {
    private Context context;
    private int resource;
    private ArrayList<movie> items;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList<movie> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, null);
        }

        movie currentItem = items.get(position);

        if (currentItem != null) {
            TextView tvTitle = view.findViewById(R.id.textViewTitle);
            TextView tvGenre = view.findViewById(R.id.textViewGenre);
            TextView tvYear = view.findViewById(R.id.textViewYear);
            ImageView ivRating = view.findViewById(R.id.imageViewRating); // Correct ID for the ImageView

            // Set title and genre
            tvTitle.setText(currentItem.getTitle());
            tvGenre.setText(currentItem.getGenres());

            // Set year
            tvYear.setText(String.valueOf(currentItem.getYear()));

            // Set rating icon based on the rating value
            int ratingImageResourceId = getRatingImageResourceId(currentItem.getRatings());
            if (ratingImageResourceId != 0) {
                ivRating.setImageResource(ratingImageResourceId);
                ivRating.setVisibility(View.VISIBLE);
            } else {
                // Set a default image for unknown rating
                ivRating.setVisibility(View.GONE);
            }
        }

        return view;
    }

    private int getRatingImageResourceId(String rating) {
        switch (rating) {
            case "G":
                return R.drawable.rating_g;
            case "M18":
                return R.drawable.rating_m18;
            case "NC16":
                return R.drawable.rating_nc16;
            case "PG":
                return R.drawable.rating_pg;
            case "PG13":
                return R.drawable.rating_pg13;
            case "R21":
                return R.drawable.rating_r21;
            default:
                return 0; // Return 0 for unknown rating
        }
    }
}
