package sg.edu.rp.c326.id22015010.p11_movies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class moviesActivity extends AppCompatActivity {
    ListView lv;
    Button backButton, btnShowAll, showPG13Button;
    DBHelper dbHelper;
    ArrayList<movie> moviesList;
    CustomAdapter aa;
    Spinner yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        lv = findViewById(R.id.listview);
        backButton = findViewById(R.id.backbtn);
        btnShowAll = findViewById(R.id.buttonShowAll);
        showPG13Button = findViewById(R.id.showPG13Button);
        moviesList = new ArrayList<>();
        yearSpinner = findViewById(R.id.yearSpinner);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllMovies();
            }
        });

        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                filterMoviesByYear(Integer.parseInt(selectedYear));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        dbHelper = new DBHelper(this);
        ArrayList<movie> movies = dbHelper.getMovie();
        moviesList.clear();
        moviesList.addAll(movies);
        aa = new CustomAdapter(this, R.layout.row, moviesList);
        lv.setAdapter(aa);

        // Set click listener for the ListView items
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movie clickedMovie = moviesList.get(position);

                Intent intent = new Intent(moviesActivity.this, ThirdActivity.class);
                intent.putExtra("id", clickedMovie.getId());
                intent.putExtra("title", clickedMovie.getTitle());
                intent.putExtra("genres", clickedMovie.getGenres());
                intent.putExtra("year", clickedMovie.getYear());
                intent.putExtra("ratings", clickedMovie.getRatings());

                startActivity(intent);
            }
        });

        // Add button functionality for showing PG13 movies
        showPG13Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPG13Movies();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper = new DBHelper(this);
        ArrayList<movie> movies = dbHelper.getMovie();
        moviesList.clear();
        moviesList.addAll(movies);
        aa.notifyDataSetChanged();
    }

    private void showPG13Movies() {
        ArrayList<movie> filteredMovies = new ArrayList<>();
        for (movie movie : dbHelper.getMovie()) {
            if (movie.getRatings().equals("PG13")) {
                filteredMovies.add(movie);
            }
        }
        aa.clear();
        aa.addAll(filteredMovies);
        aa.notifyDataSetChanged();
    }

    private void filterMoviesByYear(int selectedYear) {
        ArrayList<movie> filteredMovies = new ArrayList<>();
        for (movie movie : dbHelper.getMovie()) {
            if (movie.getYear() == selectedYear) {
                filteredMovies.add(movie);
            }
        }
        aa.clear();
        aa.addAll(filteredMovies);
        aa.notifyDataSetChanged();
    }

    private void showAllMovies() {
        ArrayList<movie> allMovies = dbHelper.getMovie();
        aa.clear();
        aa.addAll(allMovies);
        aa.notifyDataSetChanged();
    }
}
