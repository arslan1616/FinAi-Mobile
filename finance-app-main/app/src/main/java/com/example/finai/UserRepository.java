package com.example.finai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private SQLiteDatabase database;
    final UserDatabaseHelper dbHelper;

    public UserRepository(Context context) {
        dbHelper = new UserDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COLUMN_FIRST_NAME, user.getFirstName());
        values.put(UserDatabaseHelper.COLUMN_LAST_NAME, user.getLastName());
        values.put(UserDatabaseHelper.COLUMN_PHONE, user.getPhone());
        values.put(UserDatabaseHelper.COLUMN_RISK_STATUS, user.getRiskStatus());
        values.put(UserDatabaseHelper.COLUMN_IS_ADMIN, user.isAdmin() ? 1 : 0);

        String whereClause = UserDatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(user.getId()) };

        database.update(UserDatabaseHelper.TABLE_USERS, values, whereClause, whereArgs);
    }

    public void deleteUser(long userId) {
        String whereClause = UserDatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(userId) };

        database.delete(UserDatabaseHelper.TABLE_USERS, whereClause, whereArgs);
    }

    public Cursor getUserByCredentials(String firstName, String lastName, String phone) {
        String selection = UserDatabaseHelper.COLUMN_FIRST_NAME + " = ? AND " +
                UserDatabaseHelper.COLUMN_LAST_NAME + " = ? AND " +
                UserDatabaseHelper.COLUMN_PHONE + " = ?";
        String[] selectionArgs = { firstName, lastName, phone };

        return database.query(UserDatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
    }

    public boolean isAdmin(int userId) {
        Cursor cursor = database.query(UserDatabaseHelper.TABLE_USERS,
                new String[]{UserDatabaseHelper.COLUMN_IS_ADMIN},
                UserDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            int isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_IS_ADMIN));
            cursor.close();
            return isAdmin == 1;
        }

        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = database.query(UserDatabaseHelper.TABLE_USERS,
                null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_LAST_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_PHONE));
                String riskStatus = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_RISK_STATUS));
                boolean isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_IS_ADMIN)) == 1;

                User user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhone(phone);
                user.setRiskStatus(riskStatus);
                user.setAdmin(isAdmin);

                userList.add(user);
            }
            while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        return userList;
    }

    public Cursor getUserById(int userId) {
        String selection = UserDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        return database.query(UserDatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
    }

    public boolean updateUserRiskStatus(int userId, String riskStatus) {
        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COLUMN_RISK_STATUS, riskStatus);

        String whereClause = UserDatabaseHelper.COLUMN_ID + " = ?";
        String[] whereArgs = { String.valueOf(userId) };

        int rowsAffected = database.update(UserDatabaseHelper.TABLE_USERS, values, whereClause, whereArgs);
        return rowsAffected > 0;
    }
}