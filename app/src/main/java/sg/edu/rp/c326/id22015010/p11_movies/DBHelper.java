package sg.edu.rp.c326.id22015010.p11_movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";

    private static final String TABLE_NAME = "movies";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_GENRE = "genres";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_RATINGS = "ratings";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_GENRE + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_RATINGS + " TEXT)";

        db.execSQL(createTableSql);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    //CRUD: Create, Retrieve, Update, Delete

    //Create/Insert
    //The double must have lower case d
    public void insertMovie(String title, String genres, int year, String ratings) {
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the description as value
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_GENRE, genres);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_RATINGS, ratings);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
    }
    //Retrieve
    public ArrayList<movie> getMovie() {
        ArrayList<movie> MovieList = new ArrayList<movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_GENRE, COLUMN_YEAR, COLUMN_RATINGS};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String genre = cursor.getString(2);
                Integer year = cursor.getInt(3);
                String ratings = cursor.getString(4);
                //Creating a new object
                movie obj = new movie(id, title, genre, year, ratings);

                MovieList.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return MovieList;
    }
    //Update
    public void updateMovie(movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, movie.getTitle());
        values.put(COLUMN_GENRE, movie.getGenres());
        values.put(COLUMN_YEAR, movie.getYear());
        values.put(COLUMN_RATINGS, movie.getRatings());

        String[] whereArgs = {String.valueOf(movie.getId())};
        //Condition
        //What is ?: A placeholder, for the value to be taken in (can any id)
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", whereArgs);
        db.close();
    }
    //Delete
    public int deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_NAME, condition, args);
        db.close();
        return result;
    }
}