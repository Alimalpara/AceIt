package com.android.aceit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.aceit.Models.ChecklistItem;
import com.android.aceit.Models.QuestionModel;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2; // Increase the version number


    private static final String DATABASE_NAME = "aceit.db";
    private static final String TABLE_NAME = "favorite_questions";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION_ID = "question_id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_ANSWER = "answer";

    //for checklist
    private static final String TABLE_CHECKLIST = "checklist";
    private static final String COLUMN_ID_2 = "id";
    private static final String COLUMN_CHECKLIST_ID = "checklist_id";
    private static final String COLUMN_ITEM = "item";
    private static final String COLUMN_CHECKED = "checked";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_QUESTION_ID + " TEXT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_ANSWER + " TEXT)";
        db.execSQL(createTableQuery);

        //for checklist table queries
        String createChecklistTableQuery  = "CREATE TABLE " + TABLE_CHECKLIST + "(" +
                COLUMN_ID_2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CHECKLIST_ID + " TEXT, " +
                COLUMN_ITEM + " TEXT, " +
                COLUMN_CHECKED + " INTEGER" +
                ")";
        db.execSQL(createChecklistTableQuery );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKLIST);
        onCreate(db);
    }

    public boolean addFavoriteQuestion(String questionId, String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUESTION_ID, questionId);
        contentValues.put(COLUMN_QUESTION, question);
        contentValues.put(COLUMN_ANSWER, answer);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllFavoriteQuestions() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean removeFavoriteQuestion(String questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_QUESTION_ID + " = ?", new String[]{questionId});
        return result > 0;
    }

    public boolean isQuestionInFavorites(String questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_QUESTION_ID};
        String selection = COLUMN_QUESTION_ID + " = ?";
        String[] selectionArgs = {questionId};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean isInFavorites = cursor.getCount() > 0;
        cursor.close();
        return isInFavorites;
    }

    public boolean updateFavoriteQuestion(String questionId, String question, String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_QUESTION_ID, questionId);
        contentValues.put(COLUMN_QUESTION, question);
        contentValues.put(COLUMN_ANSWER, answer);
        String selection = COLUMN_QUESTION_ID + " = ?";
        String[] selectionArgs = {questionId};
        int result = db.update(TABLE_NAME, contentValues, selection, selectionArgs);
        return result > 0;
    }
    public QuestionModel getQuestionFromFavorites(String questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_QUESTION, COLUMN_ANSWER};
        String selection = COLUMN_QUESTION_ID + " = ?";
        String[] selectionArgs = {questionId};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int questionIndex = cursor.getColumnIndex(COLUMN_QUESTION);
        int answerIndex = cursor.getColumnIndex(COLUMN_ANSWER);
        QuestionModel favoriteQuestion = null;
        if (questionIndex != -1 && answerIndex != -1 ) {
            if (cursor.moveToFirst()) {
                do {
                    String question = cursor.getString(questionIndex);
                    String answer = cursor.getString(answerIndex);

                    favoriteQuestion = new QuestionModel(question, answer, questionId);

                } while (cursor.moveToNext());
            }
        }
        return favoriteQuestion;
    }

    ///checkliust methoids
    // Add a new checklist item
    // Add a new checklist item
    public boolean addChecklistItem(long id, String item, boolean isChecked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHECKLIST_ID, id);
        values.put(COLUMN_ITEM, item);
        values.put(COLUMN_CHECKED, isChecked ? 1 : 0); // Convert boolean to integer
        long result = db.insert(TABLE_CHECKLIST, null, values);
        db.close();
        return result != -1;
    }


    // Update a checklist item
    public boolean updateChecklistItem(long id, String newItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM, newItem);
        int rowsAffected = db.update(TABLE_CHECKLIST, values, COLUMN_CHECKLIST_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }


    // Update the checked status of a checklist item
    public boolean updateChecklistItemCheckedStatus(long id, boolean isChecked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHECKED, isChecked ? 1 : 0);
        int rowsAffected = db.update(TABLE_CHECKLIST, values, COLUMN_CHECKLIST_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }


    // Delete a checklist item
    public boolean deleteChecklistItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_CHECKLIST, COLUMN_CHECKLIST_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }
    public ArrayList<ChecklistItem> getAllChecklistItems() {
        ArrayList<ChecklistItem> checklistItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CHECKLIST, null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(COLUMN_CHECKLIST_ID));
                    String item = cursor.getString(cursor.getColumnIndex(COLUMN_ITEM));
                    int checkedValue = cursor.getInt(cursor.getColumnIndex(COLUMN_CHECKED));
                    boolean isChecked = checkedValue == 1;
                    ChecklistItem checklistItem = new ChecklistItem(id, item, isChecked);
                    checklistItems.add(checklistItem);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return checklistItems;
    }


}

