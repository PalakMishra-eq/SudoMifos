package com.example.sudomifos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_SALT = "salt";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_SALT + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        String salt = generateSaltString();
        String hashedPassword = hashPassword(password, Base64.getDecoder().decode(salt));

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, hashedPassword);
        values.put(COLUMN_SALT, salt);

        db.insert(TABLE_USERS, null, values);
        db.close();
        return 0;
    }

    public Cursor getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PASSWORD, COLUMN_SALT},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean verifyUser(String email, String password) {
        Cursor cursor = getUser(email);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String storedHash = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            @SuppressLint("Range") String salt = cursor.getString(cursor.getColumnIndex(COLUMN_SALT));
            String hashedPassword = hashPassword(password, Base64.getDecoder().decode(salt));

            return storedHash.equals(hashedPassword);
        }
        return false;
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private String generateSaltString() {
        return Base64.getEncoder().encodeToString(generateSalt());
    }
}

