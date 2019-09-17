package com.kansoubunko.kiyota.kansoubunko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;

import java.util.ArrayList;
import java.util.List;

public class TimeLineListViewAdapter extends BaseAdapter {
    private static Context sContext;
    private static List<BookInfoEntity> bookInfoEntityList;
    private static int sItemResourceId;
    public static List<Integer> sImageList = new ArrayList<>();
    private static String sample;
    private static List<String> wowo = new ArrayList<>();

    //コンストラクタ
    public TimeLineListViewAdapter(Context context, List<BookInfoEntity> bookInfoList) {
        sContext = context;
        bookInfoEntityList = bookInfoList;
    }

    @Override
    public int getCount() {
        return bookInfoEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimeLineListViewAdapter.ViewHolder holder;

        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = View.inflate(sContext, R.layout.item_timeline_list, null);
            // ViewHolder を生成
            holder = new TimeLineListViewAdapter.ViewHolder();
            holder.bookImageiew = convertView.findViewById(R.id.time_line_book_image);
            holder.userImageView = convertView.findViewById(R.id.timeline_user_image);
            holder.bookReviewText = convertView.findViewById(R.id.time_line_grid_view);
            convertView.setTag(holder);
        } else {
            holder = (TimeLineListViewAdapter.ViewHolder) convertView.getTag();
        }

        BookInfoEntity entity = new BookInfoEntity();
        entity.setBookImage(bookInfoEntityList.get(position).getBookImage());

        holder.bookImageiew.setImageResource(R.drawable.no_book_img);
        holder.userImageView.setImageResource(R.drawable.no_book_img);
//        holder.bookImageiew.setImageResource(Integer.parseInt(bookInfoEntityList.get(position).getBookImage()));
//        holder.userImageView.setImageResource(Integer.parseInt(bookInfoEntityList.get(position).getBookImage()));
//        Context context;
//        int itemLayoutId;
//        List<Integer> integerList;
//        String[] members;

        TimeLineGridViewAdapter bookListGridAdapter = new TimeLineGridViewAdapter(sContext, R.id.time_line_grid_view);
        holder.bookReviewText.setAdapter(bookListGridAdapter);
        return convertView;
    }

    //Viewに渡す本の画像と感想文
    public class ViewHolder {
        public ImageView bookImageiew;
        public ImageView userImageView;
        public GridView bookReviewText;
    }
}
