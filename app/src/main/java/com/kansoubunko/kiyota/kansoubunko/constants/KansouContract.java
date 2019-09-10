package com.kansoubunko.kiyota.kansoubunko.constants;

import android.net.Uri;
import android.provider.BaseColumns;

public class KansouContract {

    public static final String AUTHORITY = "com.kansoubunko.kiyota.kansoubunko.provider";
    public static final String PATH_INPUT = "spa_input";


    public interface BaseInputColumns extends BaseColumns {
    }

    public static final class Input implements BaseInputColumns {
        public static final Uri CONTENT_URI = Uri.parse(
                "content://" + AUTHORITY + "/" + PATH_INPUT);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/spa_input_v1";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/spa_input_v1";

        public static final String USER_NAME = "user_name";

        public static final String USER_PASSWORD = "user_password";

        public static final String BOOK_ID = "book_id";

        public static final String BOOK_TITLE = "book_title";

        public static final String BOOK_IMAGE = "book_image";

        public static final String BOOK_REVIEW = "book_review";

        public static final String DEFAULT_SORT_ORDER = _ID + " ASC";

        private Input() {
        }
    }
}
