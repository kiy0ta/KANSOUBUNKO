package com.kansoubunko.kiyota.kansoubunko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;

import java.util.ArrayList;
import java.util.List;

public class BookListGridAdapter extends BaseAdapter {

    private static Context sContext;
    private static int sItemResourceId;
    public static List<Integer> sImageList = new ArrayList<>();
    public static List<String> sTitleList = new ArrayList<>();
    private LayoutInflater inflater;

    //コンストラクタ
    public BookListGridAdapter(Context context, int itemLayoutId, List<Integer> imageList, List<String> titleList) {
        sContext = context;
        this.inflater = (LayoutInflater)
                sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sItemResourceId = itemLayoutId;
        sImageList = imageList;
        sTitleList = titleList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(sItemResourceId, parent, false);
            // ViewHolder を生成
            holder = new ViewHolder();
            //インスタンス化
            holder.bookImage = convertView.findViewById(R.id.item_book_image);
            holder.bookTitleText = convertView.findViewById(R.id.item_book_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //データをセットする
        holder.bookImage.setImageResource(sImageList.get(position));
        holder.bookTitleText.setText(sTitleList.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return sImageList.size();
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
        public TextView bookTitleText;
        public ImageView bookImage;
    }

}
