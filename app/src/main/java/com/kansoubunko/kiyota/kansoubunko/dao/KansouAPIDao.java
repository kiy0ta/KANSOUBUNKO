package com.kansoubunko.kiyota.kansoubunko.dao;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.network.KansouAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//API用データ処理クラス
public class KansouAPIDao {
    /**
     * 進行ダイアログ
     */
    private ProgressDialog progressDialog;
    /**
     * アラートダイアログ
     */
    private AlertDialog.Builder builder;
    /**
     * ハンドラー
     */
    private Handler myHandler;
    /**
     * ユーザー情報保持リスト
     */
    private List<UserInfoEntity> userInfoList;
    /**
     * 本の情報保持リスト
     */
    private List<BookInfoEntity> bookInfoList;
    /**
     * API成功フラグ
     */
    private boolean isAPISuccess = false;

    /**
     * ユーザー個人情報取得API実行メソッド
     */
    public List<UserInfoEntity> getUserInfo(String name) {
        // ユーザー名取得
        final String userName = name;
        // ダイアログ表示
        this.progressDialog.show();
        //リスト作成
        userInfoList = new ArrayList<>();
        //dto作成
        final UserInfoEntity entity = new UserInfoEntity();
        //API実行
        KansouAPI.getInstance().getUserInfo(userName,
                new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        // API失敗時
                        myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // レスポンス取得
                        String restring = response.body().string();
                        JSONObject jso = null;
                        try {
                            jso = new JSONObject(restring);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //ユーザーの個人情報取得
                        try {
                            if (jso.getBoolean("status")) {
                                entity.setUserId(jso.getString("user_name"));
                                entity.setUserName(jso.getString("user_name"));
                                entity.setUserBirthday(jso.getString("user_name"));
                                entity.setFollow(jso.getString("user_name"));
                                entity.setFollowers(jso.getString("user_name"));
                                entity.setUserImage(jso.getString("user_name"));
                                entity.setProfile(jso.getString("user_name"));
                                userInfoList.add(entity);
                                // ダイアログ消去
                                KansouAPIDao.this.progressDialog.dismiss();
                            } else if (!jso.getBoolean("status") && jso.getInt("error_code") == 1) {
                                //TODO:エラーメッセージを表示
                                //TODO:処理を中断する
                                // ダイアログ消去
                                KansouAPIDao.this.progressDialog.dismiss();
                                return;
                            } else {
                                // APIエラー時処理
                                KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        return userInfoList;
    }

    /**
     * ユーザー個人情報登録・更新API実行メソッド
     */
    public void updateUserInfo(String userName, String userPassword, String userBirthday, String userFollow,
                               String userFollowers, String userImage, String profile) {
        // ダイアログ表示
        this.progressDialog.show();
        //リスト作成
        userInfoList = new ArrayList<>();
        //API実行
        KansouAPI.getInstance().updateUserInfo(userName, userPassword, userBirthday, userFollow,
                userFollowers, userImage, profile, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // API失敗ダイアログ
                        KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // レスポンス取得
                        String restring = response.body().string();
                        JSONObject jso = null;
                        try {
                            jso = new JSONObject(restring);
                        } catch (JSONException e) {
                            // Jsonエラー発生時はAPI失敗ダイアログ
                            KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            return;
                        }
                        // 成功判定を取得
                        try {
                            if (jso.getBoolean("status")) {
                                KansouAPIDao.this.isAPISuccess = true;
                            } else {
                                // API失敗ダイアログ
                                KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void updateBookInfo(String bookUserName, String bookTitle, String bookImage,
                               String bookReview, String bookDate, String favorite) {
        // ダイアログ表示
        this.progressDialog.show();
        //リスト作成
        userInfoList = new ArrayList<>();
        //API実行
        KansouAPI.getInstance().updateBookInfo(bookUserName, bookTitle, bookImage,
                bookReview, bookDate, favorite, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        // API失敗ダイアログ
                        KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // レスポンス取得
                        String restring = response.body().string();
                        JSONObject jso = null;
                        try {
                            jso = new JSONObject(restring);
                        } catch (JSONException e) {
                            // Jsonエラー発生時はAPI失敗ダイアログ
                            KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            return;
                        }
                        // 成功判定を取得
                        try {
                            if (jso.getBoolean("status")) {
                                KansouAPIDao.this.isAPISuccess = true;
                            } else {
                                // API失敗ダイアログ
                                KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 全ユーザーの個人情報取得API実行メソッド
     */
    public List<UserInfoEntity> getAllUserInfo() {
        // ダイアログ表示
        this.progressDialog.show();
        //リスト作成
        userInfoList = new ArrayList<>();
        //dto作成
        final UserInfoEntity entity = new UserInfoEntity();
        //API実行
        KansouAPI.getInstance().getAllUserInfo(
                new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        // API失敗時
                        myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // レスポンス取得
                        String restring = response.body().string();
                        JSONObject jso = null;
                        try {
                            jso = new JSONObject(restring);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //ユーザーの個人情報取得
                        try {
                            if (jso.getBoolean("status")) {
                                entity.setUserId(jso.getString("user_name"));
                                entity.setUserName(jso.getString("user_name"));
                                entity.setUserPassword(jso.getString("user_password"));
                                userInfoList.add(entity);
                                // ダイアログ消去
                                KansouAPIDao.this.progressDialog.dismiss();
                            } else if (!jso.getBoolean("status") && jso.getInt("error_code") == 1) {
                                //TODO:エラーメッセージを表示
                                //TODO:処理を中断する
                                // ダイアログ消去
                                KansouAPIDao.this.progressDialog.dismiss();
                                return;
                            } else {
                                // APIエラー時処理
                                KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        return userInfoList;
    }

    public List<BookInfoEntity> getBookInfo(String name) {
        // ユーザー名取得
        final String userName = name;
        // ダイアログ表示
        this.progressDialog.show();
        //リスト作成
        userInfoList = new ArrayList<>();
        //dto作成
        final BookInfoEntity entity = new BookInfoEntity();
        //API実行
        KansouAPI.getInstance().getBookInfo(userName,
                new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        // API失敗時
                        myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        // レスポンス取得
                        String restring = response.body().string();
                        JSONObject jso = null;
                        try {
                            jso = new JSONObject(restring);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //ユーザーの個人情報取得
                        try {
                            if (jso.getBoolean("status")) {
                                entity.setBookId(jso.getString("book_user_name"));
                                entity.setBookUserName(jso.getString("book_title"));
                                entity.setBookTitle(jso.getString("book_image"));
                                entity.setBookImage(jso.getString("book_image"));
                                entity.setBookReview(jso.getString("book_review"));
                                entity.setBookDate(jso.getString("book_date"));
                                entity.setFavorite(jso.getString("favorite"));
                                bookInfoList.add(entity);
                                // ダイアログ消去
                                KansouAPIDao.this.progressDialog.dismiss();
                            } else if (!jso.getBoolean("status") && jso.getInt("error_code") == 1) {
                                //TODO:エラーメッセージを表示
                                //TODO:処理を中断する
                                // ダイアログ消去
                                KansouAPIDao.this.progressDialog.dismiss();
                                return;
                            } else {
                                // APIエラー時処理
                                KansouAPIDao.this.myHandler.postDelayed(KansouAPIDao.this.apiFailure, 10);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        return bookInfoList;
    }


    /**
     * API失敗時処理<br>
     * アラートダイアログを表示
     */
    private Runnable apiFailure = new Runnable() {
        @Override
        public void run() {
            // プログレスダイアログを消去しアラートダイアログを表示
            KansouAPIDao.this.progressDialog.dismiss();
            KansouAPIDao.this.builder.show();
        }
    };

//    /**
//     * adapter更新処理
//     */
//    private Runnable adapterNotify = new Runnable() {
//        @Override
//        public void run() {
//            adapter.notifyDataSetChanged();
//        }
//    };
}
