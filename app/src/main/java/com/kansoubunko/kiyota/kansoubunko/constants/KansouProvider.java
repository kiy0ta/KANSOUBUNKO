package com.kansoubunko.kiyota.kansoubunko.constants;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.kansoubunko.kiyota.kansoubunko.util.LogUtil;

import java.util.HashMap;


public class KansouProvider extends ContentProvider {

    private static final String TAG = "DbProteinProvider";
    private static final String DATABASE_NAME = "inputs.db";
    private static final String INPUT_TABLE = "input";
    private static final int INPUT_DATABASE_VERSION = 1;
    private static final int MATCH_INPUT = 1;
    private static final int MATCH_INPUT_ID = 2;

    private static HashMap<String, String> sInputProjectionMap;
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(KansouContract.AUTHORITY, KansouContract.PATH_INPUT,
                MATCH_INPUT);
        sUriMatcher.addURI(KansouContract.AUTHORITY, KansouContract.PATH_INPUT + "/#",
                MATCH_INPUT_ID);

        sInputProjectionMap = new HashMap<String, String>();
        sInputProjectionMap.put(KansouContract.Input._ID, KansouContract.Input._ID);
        sInputProjectionMap.put(KansouContract.Input.USER_NAME, KansouContract.Input.USER_NAME);
        sInputProjectionMap.put(KansouContract.Input.USER_PASSWORD, KansouContract.Input.USER_PASSWORD);
        sInputProjectionMap.put(KansouContract.Input.BOOK_ID, KansouContract.Input.BOOK_ID);
        sInputProjectionMap.put(KansouContract.Input.BOOK_TITLE, KansouContract.Input.BOOK_TITLE);
        sInputProjectionMap.put(KansouContract.Input.BOOK_IMAGE, KansouContract.Input.BOOK_IMAGE);
        sInputProjectionMap.put(KansouContract.Input.BOOK_TITLE, KansouContract.Input.BOOK_REVIEW);
    }

    private SQLiteDatabase mInputDB;
    private Context mContext;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        DbKansouDatabaseHelper dbHelper = new DbKansouDatabaseHelper(mContext);
        mInputDB = dbHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        int matchType = sUriMatcher.match(uri);
        switch (matchType) {
            case MATCH_INPUT:
            case MATCH_INPUT_ID:
                return queryInput(uri, projection, selection, selectionArgs, sortOrder,
                        matchType);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MATCH_INPUT:
                return KansouContract.Input.CONTENT_TYPE;
            case MATCH_INPUT_ID:
                return KansouContract.Input.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (sUriMatcher.match(uri)) {
            case MATCH_INPUT:
            case MATCH_INPUT_ID:
                return insertInput(uri, values);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int request = sUriMatcher.match(uri);
        switch (request) {
            case MATCH_INPUT:
            case MATCH_INPUT_ID:
                return deleteExternalInput(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int result = 0;
        int request = sUriMatcher.match(uri);
        switch (request) {
            case MATCH_INPUT:
            case MATCH_INPUT_ID:
                result = updateExternalInput(uri, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (result > 0) {
            notifyChange(uri);
        }
        return result;
    }

    private void notifyChange(Uri uri) {
        mContext.getContentResolver().notifyChange(uri, null);
    }


    private Cursor queryInput(Uri uri, String[] projection, String selection,
                              String[] selectionArgs, String sortOrder, int matchType) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        String orderBy;
        switch (matchType) {
            case MATCH_INPUT:
                queryBuilder.setTables(INPUT_TABLE);
                queryBuilder.setProjectionMap(sInputProjectionMap);
                orderBy = KansouContract.Input.DEFAULT_SORT_ORDER;
                break;
            case MATCH_INPUT_ID:
                queryBuilder.setTables(INPUT_TABLE);
                queryBuilder.setProjectionMap(sInputProjectionMap);
                queryBuilder.appendWhere(KansouContract.Input._ID + "=" + uri.getPathSegments().get(1));
                orderBy = KansouContract.Input.DEFAULT_SORT_ORDER;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        // Use the default sort order only If no sort order is specified.
        if (!TextUtils.isEmpty(sortOrder)) {
            orderBy = sortOrder;
        }
        Cursor cur = queryBuilder
                .query(mInputDB, projection, selection, selectionArgs, null, null, orderBy);
        // Tell the cursor what URI to watch, so it knows when its source data changes.
        cur.setNotificationUri(mContext.getContentResolver(), uri);
        return cur;
    }

    private Uri insertInput(Uri uri, ContentValues values) {
        long rowId = mInputDB.insert(INPUT_TABLE, null, values);
        if (rowId <= 0) {
            throw new SQLException("Failed to insert row into " + uri);
        }
        return ContentUris.withAppendedId(KansouContract.Input.CONTENT_URI, rowId);
    }

    private int deleteExternalInput(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String rowId;
        int request = sUriMatcher.match(uri);
        switch (request) {
            case MATCH_INPUT:
                count = mInputDB.delete(INPUT_TABLE, selection, selectionArgs);
                break;
            case MATCH_INPUT_ID:
                rowId = uri.getPathSegments().get(1);
                selection = KansouContract.Input._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND ("
                        + selection + ')' : "");
                count = mInputDB.delete(INPUT_TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    private int updateExternalInput(Uri uri, ContentValues values, String selection,
                                    String[] selectionArgs) {
        String rowId;
        int count = 0;
        int request = sUriMatcher.match(uri);
        switch (request) {
            case MATCH_INPUT:
                count = mInputDB
                        .update(INPUT_TABLE, values, selection, selectionArgs);
                break;
            case MATCH_INPUT_ID:
                rowId = uri.getPathSegments().get(1);
                selection = KansouContract.Input._ID + "=" + rowId + (!TextUtils.isEmpty(selection) ? " AND ("
                        + selection + ')' : "");
                count = mInputDB
                        .update(INPUT_TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    /**
     * helperクラス
     */
    private static class DbKansouDatabaseHelper extends SQLiteOpenHelper {

        private DbKansouDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, INPUT_DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + INPUT_TABLE + " (" + KansouContract.Input._ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + KansouContract.Input.USER_NAME
                    + " TEXT," + KansouContract.Input.USER_PASSWORD
                    + " TEXT," + KansouContract.Input.BOOK_ID + " TEXT,"
                    + KansouContract.Input.BOOK_TITLE + " TEXT,"
                    + KansouContract.Input.BOOK_IMAGE + " TEXT,"
                    + KansouContract.Input.BOOK_REVIEW + " TEXT " + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LogUtil.d(TAG, "Upgrading database from " + oldVersion + " to " + newVersion);
        }

    }
}
