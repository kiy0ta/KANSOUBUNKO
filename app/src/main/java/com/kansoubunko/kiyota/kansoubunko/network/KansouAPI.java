package com.kansoubunko.kiyota.kansoubunko.network;

import com.kansoubunko.kiyota.kansoubunko.util.ConfigPropUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class KansouAPI {
    /**
     * クラス変数としてインスタンスを定義
     */
    private static KansouAPI instance;
    /**
     * MIMEタイプと文字コードの指定
     */
    private static final String MIME_AND_CHARACTER_TYPE = "application/json; charset=utf-8";
    /**
     * User-Agentを保持する変数
     */
    private static final String USER_AGENT = "requests";
    /**
     * クエリパラメータの変数名部分
     */
    private static final String USER_QUERY_VARIABLE = "?user_name=";
    /**
     * クエリパラメータの変数名部分
     */
    private static final String BOOK_QUERY_VARIABLE = "?book_user_name=";
    /**
     * ユーザー個人情報のAPIのURL末尾
     */
    private static final String USER_INFO = "kansou/userinfo";
    /**
     * 全ユーザー個人情報のAPIのURL末尾
     */
    private static final String ALL_USER_INFO = "kansou/alluserinfo";
    /**
     * 特定のユーザーの本の情報のAPIのURL末尾
     */
    private static final String ALL_BOOK_INFO = "kansou/allbookinfo";
    /**
     * タイムアウト時間(秒
     */
    private static final int TIMEOUT_SECONDS = 60;

    /**
     * インスタンスを呼ぶメソッド
     *
     * @return instance インスタンス
     */
    public static KansouAPI getInstance() {
        if (instance == null) {
            instance = new KansouAPI();
        }
        return instance;
    }

    /**
     * APIのBaseURLとURL末尾を結合するメソッド
     *
     * @param urlEnd URL末尾
     * @return 整形されたAPIのURL
     */
    public static String joinBaseUrl(String urlEnd) {
        // ベースのURLをプロパティファイルから取得
        String baseUrl = ConfigPropUtil.getInstance().get("kansoubunko_api_base_url");
        return baseUrl + urlEnd;
    }

    /**
     * getメソッド
     *
     * @param url APIのURL
     * @param cb  コールバック関数
     */
    public void get(String url, Callback cb) {
        Request request = new Request.Builder()
                .header("User-Agent", USER_AGENT)
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(cb);
    }

    /**
     * postメソッド
     *
     * @param url    APIのURL
     * @param params map化されたリクエストのボディ
     * @param cb     コールバック関数
     */
    public void post(String url, Map<String, Object> params, Callback cb) {
        Request.Builder builder = new Request.Builder();
        RequestBody body = RequestBody.create(MediaType.parse(MIME_AND_CHARACTER_TYPE), JSON.toJSONString(params));
        builder.post(body);
        Request request = builder
                .header("User-Agent", USER_AGENT)
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(cb);
    }

    /**
     * ユーザー個人情報取得
     *
     * @param userName ユーザー名
     * @param cb       コールバック関数
     */
    public void getUserInfo(final String userName, Callback cb) {

        // クエリパラメータの作成
        String accessTokenQuery = USER_QUERY_VARIABLE + userName;

        // get処理実行
        get(joinBaseUrl(USER_INFO) + accessTokenQuery, cb);
    }

    /**
     * ユーザー個人情報登録・更新
     *
     * @param userName      ユーザー名
     * @param userPassword  パスワード
     * @param userBirthday  誕生日
     * @param userFollow    フォロー数
     * @param userFollowers フォロワー数
     * @param userImage     ユーザーアイコン画像
     * @param profile       自己紹介
     * @param cb            コールバック関数
     */
    public void updateUserInfo(
            final String userName,
            final String userPassword,
            final String userBirthday,
            final String userFollow,
            final String userFollowers,
            final String userImage,
            final String profile,
            Callback cb
    ) {
        // ボディのプロパティを保持するMapの生成
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put("user_name", userName);
                put("user_name", userPassword);
                put("user_birthday", userBirthday);
                put("user_follow", userFollow);
                put("user_followers", userFollowers);
                put("user_image", userImage);
                put("profile", profile);
            }
        };
        // post処理実行
        post(joinBaseUrl(USER_INFO), map, cb);
    }

    /**
     * 全ユーザー個人情報取得
     *
     * @param cb コールバック関数
     */
    public void getAllUserInfo(Callback cb) {

        // クエリパラメータの作成
        String accessTokenQuery = USER_QUERY_VARIABLE;

        // get処理実行
        get(joinBaseUrl(ALL_USER_INFO) + accessTokenQuery, cb);
    }

    /**
     * 特定のユーザーの本の情報取得
     *
     * @param bookUserName ユーザー名
     * @param cb           コールバック関数
     */
    public void getBookInfo(final String bookUserName, Callback cb) {

        // クエリパラメータの作成
        String accessTokenQuery = USER_QUERY_VARIABLE + bookUserName;

        // get処理実行
        get(joinBaseUrl(ALL_BOOK_INFO) + accessTokenQuery, cb);
    }

    /**
     * 本の情報登録・更新
     *
     * @param bookUserName 本の感想を書いたユーザー名
     * @param bookTitle    本のタイトル
     * @param bookImage    本の画像
     * @param bookReview   感想文
     * @param bookDate     本の登録日(=更新日)
     * @param favorite     お気に入り
     * @param cb           コールバック関数
     */
    public void updateBookInfo(
            final String bookUserName,
            final String bookTitle,
            final String bookImage,
            final String bookReview,
            final String bookDate,
            final String favorite,
            Callback cb
    ) {
        // ボディのプロパティを保持するMapの生成
        Map<String, Object> map = new HashMap<String, Object>() {
            {
                put("book_user_name", bookUserName);
                put("book_title", bookTitle);
                put("book_image", bookImage);
                put("book_review", bookReview);
                put("book_date", bookDate);
                put("favorite", favorite);
            }
        };
        // post処理実行
        post(joinBaseUrl(USER_INFO), map, cb);
    }
}

/**
 * ユーザー個人情報削除
 *
 * @param accessToken アクセストークン
 * @param cb          コールバック関数
 */
//    public void deleteUserInfo(final String accessToken, Callback cb) {
//
//        // ボディのプロパティを保持するMapの生成
//        Map<String, Object> map = new HashMap<String, Object>() {
//            {
//                put("access_token", accessToken);
//            }
//        };
//
//        // delete処理実行
//        delete(joinBaseUrl(USER_INFO), map, cb);
//    }
