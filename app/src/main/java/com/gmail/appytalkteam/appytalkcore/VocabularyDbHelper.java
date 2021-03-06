package com.gmail.appytalkteam.appytalkcore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Lewis on 24/06/15.
 */
public class VocabularyDbHelper extends SQLiteOpenHelper {
    private final Context myContext;
    private static final int DATABASE_VERSION = 2;
    private static String DB_PATH;

    private static final String DATABASE_NAME = "vocabulary.sqlite";

    private static VocabularyDbHelper mInstance = null;
    private SQLiteDatabase myDataBase;


    public VocabularyDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    }

    public void createDataBase() throws IOException{

        boolean dbExist = this.checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database doesn't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream

            InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }



    public void openDataBase() {

        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }
    public static VocabularyDbHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new VocabularyDbHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public ArrayList<Word> getWordsFromDataBase() {
        ArrayList<Word> wordList = new ArrayList<>();
        String language = myContext.getSharedPreferences("PrefsFile",Context.MODE_PRIVATE).getString("language","en");
        String category = myContext.getSharedPreferences("PrefsFile",Context.MODE_PRIVATE).getString("category","Food");
        this.openDataBase();
        String wordQuery = "SELECT IMGFILE, NAME, AUDIO, Category FROM 'Vocabulary' WHERE LANG = \"" + language.toUpperCase() + "\" AND Category = \"" + category + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(wordQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String imageResourceName = cursor.getString(0);
                int imageResourceId =  myContext.getResources().getIdentifier(imageResourceName.replaceFirst("[.][^.]+$", ""), "drawable", myContext.getPackageName());
                String wordText = cursor.getString(1);
                String audioResourceName = cursor.getString(2);
                int audioResourceId =  myContext.getResources().getIdentifier(audioResourceName.replaceFirst("[.][^.]+$", ""), "raw", myContext.getPackageName());
                String categoryName = cursor.getString(3);
                Word word = new Word(imageResourceId, wordText, audioResourceId, categoryName);                wordList.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        // return contact list
        return wordList;


    }
    // overloading the above method to include Category
    public ArrayList<Word> getWordsFromDataBase(String language) {
        ArrayList<Word> wordList = new ArrayList<>();
        String category = myContext.getSharedPreferences("PrefsFile",Context.MODE_PRIVATE).getString("category","Food");
        this.openDataBase();
        String wordQuery = "SELECT IMGFILE, NAME, AUDIO, Category FROM 'Vocabulary' WHERE LANG = \"" + language.toUpperCase() + "\" AND Category = \"" + category + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(wordQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String imageResourceName = cursor.getString(0);
                int imageResourceId =  myContext.getResources().getIdentifier(imageResourceName.replaceFirst("[.][^.]+$", ""), "drawable", myContext.getPackageName());
                String wordText = cursor.getString(1);
                String audioResourceName = cursor.getString(2);
                int audioResourceId =  myContext.getResources().getIdentifier(audioResourceName.replaceFirst("[.][^.]+$", ""), "drawable", myContext.getPackageName());
                String categoryName = cursor.getString(3);
                Word word = new Word(imageResourceId, wordText, audioResourceId, categoryName);
                wordList.add(word);
            } while (cursor.moveToNext());
        }
        cursor.close();
        this.close();
        // return contact list
        return wordList;


    }
    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //    String CREATE_VOCABULARY_LIST = "CREATE TABLE vocabulary ( id iNTEGER PRIMARY KEY AUTOINCREMENT, lang TEXT, name TEXT, audio TEXT";
      //  db.execSQL(CREATE_VOCABULARY_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     //   db.execSQL("DROP TABLE IF EXISTS vocabulary");

       // this.onCreate(db);
    }

    // If you change the database schema, you must increment the database version.



}