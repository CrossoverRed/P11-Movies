package sg.edu.rp.c326.id22015010.p11_movies;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
public class ThirdActivity extends AppCompatActivity {
    EditText titleEditText, genreEditText, yearEditText;
    Spinner ratingSpinner;
    Button updateButton, deleteButton;
    movie movie;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        titleEditText = findViewById(R.id.titleEditText);
        genreEditText = findViewById(R.id.genreEditText);
        yearEditText = findViewById(R.id.yearEditText);
        ratingSpinner = findViewById(R.id.ratingSpinner); // Update to use Spinner
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Get the clicked movie from the intent
        movie clickedMovie = new movie(
                getIntent().getIntExtra("id", 0),
                getIntent().getStringExtra("title"),
                getIntent().getStringExtra("genres"),
                getIntent().getIntExtra("year", 0),
                getIntent().getStringExtra("ratings")
        );

        // Set the movie object
        movie = clickedMovie;

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Display the movie details
        titleEditText.setText(clickedMovie.getTitle());
        genreEditText.setText(clickedMovie.getGenres());
        yearEditText.setText(String.valueOf(clickedMovie.getYear()));

        // Set up the Spinner for ratings
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movie_ratings, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ratingSpinner.setAdapter(adapter);
        int position = adapter.getPosition(clickedMovie.getRatings());
        ratingSpinner.setSelection(position);

        // Set click listener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMovie();
            }
        });

        // Set click listener for the delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMovie();
            }
        });
    }

    private void updateMovie() {
        // Get updated values from EditTexts
        String updatedTitle = titleEditText.getText().toString();
        String updatedGenres = genreEditText.getText().toString();
        int updatedYear = Integer.parseInt(yearEditText.getText().toString());
        String updatedRatings = ratingSpinner.getSelectedItem().toString();

        // Update the movie object with the new values
        movie.setTitle(updatedTitle);
        movie.setGenres(updatedGenres);
        movie.setYear(updatedYear);
        movie.setRatings(updatedRatings);

        // Update the movie in the database
        dbHelper.updateMovie(movie);

        Toast.makeText(this, "Update successful", Toast.LENGTH_LONG).show();

        // Finish the activity
        finish();
    }

    private void deleteMovie() {
        // Delete the movie from the database
        dbHelper.deleteMovie(movie.getId());

        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show();

        // Finish the activity
        finish();
    }

    @Override
    public void onBackPressed() {
        // Finish the activity without making any changes
        finish();
    }
}
