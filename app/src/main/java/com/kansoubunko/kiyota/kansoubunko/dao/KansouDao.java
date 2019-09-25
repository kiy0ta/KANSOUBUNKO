package com.kansoubunko.kiyota.kansoubunko.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.kansoubunko.kiyota.kansoubunko.constants.DataConstants;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.util.KansouHelper;

import java.util.ArrayList;
import java.util.List;

public class KansouDao {

    private Context mContext;
    private KansouHelper kansouHelper = new KansouHelper(mContext);

    public KansouDao(Context context) {
        mContext = context;
        kansouHelper = new KansouHelper(mContext);
    }

    /**
     * タイムライン画面：全ユーザーの名前を取得
     */
    public List<UserInfoEntity> selectAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        UserInfoEntity entity = null;
        List<UserInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection,
                    null, null, null, null, null, null);
            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    entity = new UserInfoEntity();
                    entity.setUserName(cursor.getString(0));
                    list.add(entity);
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                }
            }
        } catch (Exception ex) {
            Log.e("Kansou.db", "error", ex);
        } finally {
            // クローズ処理
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    /**
     * ログイン画面：ユーザーの新規登録
     */
    public void registUserInfo(String userName, String userPassword) {
        Log.i("Kansou.db", "start");

        // DB初期化
        SQLiteDatabase db = null;
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 新規データ登録
            value.put(kansouHelper.COLUMN_USER_NAME, userName);
            value.put(kansouHelper.COLUMN_USER_PASSWORD, userPassword);
            db.insert(kansouHelper.USER_TABLE_NAME, null, value);

        } catch (Exception ex) {
            Log.e("Kansou.db", "error:", ex);

        } finally {
            // クローズ処理
            if (db != null) {
                db.close();
                db = null;
            }
        }
    }

    /**
     * ログイン画面：ユーザー名を検索する
     */
    public boolean findUserNameInfo(String userName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection, kansouHelper.COLUMN_USER_NAME + " = ? ",
                    new String[]{userName}, null, null, null, null);
            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    //DBの中に入っている件数を取得する
                    return false;
                }
            }
            return true;

        } catch (SQLiteException e) {
            return false;
        }
    }

    /**
     * ログイン画面：ユーザー名と合致するユーザーIDを取得する
     */
    public String findUserIdInfo(String userName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection, kansouHelper.COLUMN_USER_NAME + " = ? ",
                    new String[]{userName}, null, null, null, null);
            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String userId = cursor.getString(0);
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                    return userId;
                }
            }
            return "";
        } catch (SQLiteException e) {
            return "";
        }
    }

    /**
     * 設定画面：すべてのユーザー情報を取得する
     */
    public List<UserInfoEntity> findAllUserInfo() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        UserInfoEntity entity = null;
        List<UserInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection, null,
                    null, null, null, null, null);
            // 取得できた際、インスタンスに保存
            entity = new UserInfoEntity();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    entity.setUserName(cursor.getString(0));
                    list.add(entity);
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                }
            }
        } catch (Exception ex) {
            Log.e("Kansou.db", "error", ex);
        } finally {
            // クローズ処理
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    /**
     * 設定画面：特定のユーザー情報を取得する
     */
    public List<UserInfoEntity> findUserInfo(String name) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        UserInfoEntity entity = null;
        List<UserInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_ID,
                kansouHelper.COLUMN_USER_NAME,
                kansouHelper.COLUMN_USER_PASSWORD,
                kansouHelper.COLUMN_USER_BIRTHDAY,
                kansouHelper.COLUMN_USER_FOLLOW,
                kansouHelper.COLUMN_USER_FOLLOWERS,
                kansouHelper.COLUMN_USER_IMAGE,
                kansouHelper.COLUMN_PROFILE
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection, kansouHelper.COLUMN_USER_NAME + " = ? ",
                    new String[]{name}, null, null, null, null);
            // 取得できた際、インスタンスに保存
            entity = new UserInfoEntity();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    entity.setUserId(cursor.getString(0));
                    entity.setUserName(cursor.getString(1));
                    entity.setUserPassword(cursor.getString(2));
                    if (cursor.getString(3) != null) {
                        entity.setUserBirthday(cursor.getString(3));
                    } else {
                        entity.setUserBirthday(DataConstants.DEFAULT_BIRTHDAY);
                    }
                    if (cursor.getString(4) != null) {
                        entity.setFollow(cursor.getString(4));
                    } else {
                        entity.setFollow(DataConstants.DEFAULT_FOLLOW);
                    }
                    if (cursor.getString(5) != null) {
                        entity.setFollowers(cursor.getString(5));
                    } else {
                        entity.setFollowers(DataConstants.DEFAULT_FOLLOWERS);
                    }
                    if (cursor.getString(6) != null) {
                        entity.setUserImage(cursor.getString(6));
                    } else {
                        entity.setUserImage("");
                    }
                    if (cursor.getString(7) != null) {
                        entity.setProfile(cursor.getString(7));
                    } else {
                        entity.setProfile(DataConstants.DEFAULT_PROFILE);
                    }
                    list.add(entity);
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                }
            }
        } catch (Exception ex) {
            Log.e("Kansou.db", "error", ex);
        } finally {
            // クローズ処理
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    /**
     * ログイン画面：ユーザー名とパスワードが正しいかどうか確認する
     */
    public Boolean findUserInfo(String userName, String userPassword) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_USER_NAME,
                kansouHelper.COLUMN_USER_PASSWORD
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.USER_TABLE_NAME, projection,
                    kansouHelper.COLUMN_USER_NAME + " = ? " + "AND "
                            + kansouHelper.COLUMN_USER_PASSWORD + " = ? ",
                    new String[]{userName, userPassword}, null, null, null, null);
            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                    return true;
                }
            }
            return false;
        } catch (SQLiteException e) {
            return false;
        }
    }

    /**
     * 一覧画面：特定のユーザーのすべての本の情報を取得するメソッド
     */
    public List<BookInfoEntity> selectBookInfo(String userName) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        BookInfoEntity entity = null;
        List<BookInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_BOOK_ID,
                kansouHelper.COLUMN_BOOK_USER_NAME,
                kansouHelper.COLUMN_BOOK_TITLE,
                kansouHelper.COLUMN_BOOK_IMAGE,
                kansouHelper.COLUMN_BOOK_REVIEW,
                kansouHelper.COLUMN_BOOK_DATE
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.BOOK_TABLE_NAME, projection,
                    kansouHelper.COLUMN_BOOK_USER_NAME + " = ? ", new String[]{userName}, null, null, null, null);
            // 取得できた際、インスタンスに保存
            entity = new BookInfoEntity();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    entity.setBookId(cursor.getString(0));
                    if (cursor.getString(1) != null) {
                        entity.setBookUserName(cursor.getString(1));
                    } else {
                        entity.setBookUserName(DataConstants.DEFAULT_BIRTHDAY);
                    }
                    if (cursor.getString(2) != null) {
                        entity.setBookTitle(cursor.getString(2));
                    } else {
                        entity.setBookTitle("");
                    }
                    if (cursor.getString(3) != null) {
                        entity.setBookImage(cursor.getString(3));
                    } else {
                        entity.setBookImage("");
                    }
                    if (cursor.getString(4) != null) {
                        entity.setBookReview(cursor.getString(4));
                    } else {
                        entity.setBookReview("");
                    }
                    if (cursor.getString(5) != null) {
                        entity.setBookDate(cursor.getString(5));
                    } else {
                        entity.setBookDate("");
                    }
                    list.add(entity);
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                }
            }
        } catch (Exception ex) {
            Log.e("Kansou.db", "error", ex);
        } finally {
            // クローズ処理
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    /**
     * タイムライン画面：すべての本の情報を取得する
     */
    public List<BookInfoEntity> selectBookInfoAll() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        BookInfoEntity entity = null;
        List<BookInfoEntity> list = new ArrayList<>();
        Log.i("Kansou.db", "start");
        String[] projection = new String[]{
                kansouHelper.COLUMN_BOOK_ID,
                kansouHelper.COLUMN_BOOK_USER_NAME,
                kansouHelper.COLUMN_BOOK_TITLE,
                kansouHelper.COLUMN_BOOK_IMAGE,
                kansouHelper.COLUMN_BOOK_REVIEW,
                kansouHelper.COLUMN_BOOK_DATE
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            cursor = db.query(
                    kansouHelper.BOOK_TABLE_NAME, projection,
                    null, null, null, null, null, null);
            // 取得できた際、インスタンスに保存
            if (cursor.getCount() > 0) {
                entity = new BookInfoEntity();
                while (cursor.moveToNext()) {
                    entity.setBookId(cursor.getString(0));
                    entity.setBookUserName(cursor.getString(1));
                    entity.setBookTitle(cursor.getString(2));
                    entity.setBookImage(cursor.getString(3));
                    entity.setBookReview(cursor.getString(4));
                    entity.setBookDate(cursor.getString(5));
                    list.add(entity);
                    Log.i("Kansou.db", "sn" + cursor.getString(0));
                }
            }
        } catch (Exception ex) {
            Log.e("Kansou.db", "error", ex);
        } finally {
            // クローズ処理
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
                db = null;
            }
        }
        return list;
    }

    /**
     * 登録画面：本に関する情報を新規登録する
     */
    public void registBookInfo(String bookUserName, String bookTitle, String bookImage, String bookReview, String bookDate) {
        Log.i("Kansou.db", "start");

        // DB初期化
        SQLiteDatabase db = null;
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            ContentValues value = new ContentValues();

            // 新規データ登録
            value.put(kansouHelper.COLUMN_BOOK_USER_NAME, bookUserName);
            value.put(kansouHelper.COLUMN_BOOK_TITLE, bookTitle);
            value.put(kansouHelper.COLUMN_BOOK_IMAGE, bookImage);
            value.put(kansouHelper.COLUMN_BOOK_REVIEW, bookReview);
            value.put(kansouHelper.COLUMN_BOOK_DATE, bookDate);
            db.insert(kansouHelper.BOOK_TABLE_NAME, null, value);

        } catch (Exception ex) {
            Log.e("Kansou.db", "error:", ex);

        } finally {
            // クローズ処理
            if (db != null) {
                db.close();
                db = null;
            }
        }
    }

    /**
     * 登録画面：本に関する情報を更新する
     */
    public void updateBookInfo(String userName, String bookTitle, String bookImage, String bookReview, String bookDate) {
        Log.i("Kansou.db", "start");
        // DB初期化
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String[] projection = new String[]{
                kansouHelper.COLUMN_BOOK_USER_NAME
        };
        try {
            // DB取得
            db = this.kansouHelper.getWritableDatabase();
            ContentValues value = new ContentValues();
            // 新規データ登録
            value.put(kansouHelper.COLUMN_BOOK_USER_NAME, userName);
            value.put(kansouHelper.COLUMN_BOOK_TITLE, bookTitle);
            value.put(kansouHelper.COLUMN_BOOK_IMAGE, bookImage);
            value.put(kansouHelper.COLUMN_BOOK_REVIEW, bookReview);
            value.put(kansouHelper.COLUMN_BOOK_DATE, bookDate);
            db.update(kansouHelper.BOOK_TABLE_NAME, value, kansouHelper.COLUMN_BOOK_USER_NAME + " = ? ", new String[]{userName});

        } catch (Exception ex) {
            Log.e("Kansou.db", "error:", ex);

        } finally {
            // クローズ処理
            if (db != null) {
                db.close();
                db = null;
            }
        }
    }
}
