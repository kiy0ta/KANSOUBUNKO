package com.kansoubunko.kiyota.kansoubunko.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.activity.ListActivity;
import com.kansoubunko.kiyota.kansoubunko.activity.MyApplication;
import com.kansoubunko.kiyota.kansoubunko.activity.RegistActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class BookListGridAdapter extends BaseAdapter {

    private static Context sContext;
    private static int sItemResourceId;
    public static List<Integer> sImageList = new ArrayList<>();
    private static String[] sImageMembers = null;
    private LayoutInflater inflater;


    //新規登録したあとにMainでよびだしている初期化処理
//    public void initList(List<ProteinEntity> mEntityList) {
//        this.mEntityList = mEntityList;
//        hashmap = new HashMap<Date, String>();
//    }

    //コンストラクタ
    public BookListGridAdapter(Context context, int itemLayoutId, List<Integer> integerList, String[] members) {
        sContext = context;
        this.inflater = (LayoutInflater)
                sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sItemResourceId = itemLayoutId;
        sImageList = integerList;
        sImageMembers = members;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(sItemResourceId, parent, false);
            // ViewHolder を生成
            holder = new ViewHolder();

            holder.bookImage = convertView.findViewById(R.id.item_book_image);
            holder.bookTitleText = convertView.findViewById(R.id.item_book_text);
            //画像を押下したとき、登録画面に遷移する
//            holder.bookImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bookImage.setImageResource(sImageList.get(position));
        holder.bookTitleText.setText(sImageMembers[position]);

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
