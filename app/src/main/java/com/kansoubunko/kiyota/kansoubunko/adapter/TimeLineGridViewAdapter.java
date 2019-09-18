package com.kansoubunko.kiyota.kansoubunko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;

import java.util.ArrayList;
import java.util.List;

public class TimeLineGridViewAdapter extends BaseAdapter {


    private static Context sContext;
    private static int sItemResourceId;
    public static List<Integer> sImageList = new ArrayList<>();
    private LayoutInflater inflater;
    private static String[] sImageMembers = null;
    private static List<String> wowo = new ArrayList<>();


    //新規登録したあとにMainでよびだしている初期化処理
//    public void initList(List<ProteinEntity> mEntityList) {
//        this.mEntityList = mEntityList;
//        hashmap = new HashMap<Date, String>();
//    }

    //コンストラクタ
    public TimeLineGridViewAdapter(Context context, int itemLayoutId) {
        sContext = context;
        this.inflater = (LayoutInflater)
                sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sItemResourceId = itemLayoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimeLineGridViewAdapter.ViewHolder holder;

        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = View.inflate(sContext, R.layout.item_book_review, null);
            // ViewHolder を生成
            holder = new TimeLineGridViewAdapter.ViewHolder();
            holder.bookReviewText = convertView.findViewById(R.id.item_book_review_text);
            convertView.setTag(holder);
        } else {
            holder = (TimeLineGridViewAdapter.ViewHolder) convertView.getTag();
        }
        String word = "今日の天気は晴れです。明日の天気は曇りです。";
        sImageMembers = word.split("");
        holder.bookReviewText.setText(sImageMembers[position]);
        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return 23;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //Viewに渡すほんのタイトルと画像
    public class ViewHolder {
        public TextView bookReviewText;
    }
}
