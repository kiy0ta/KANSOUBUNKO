package com.kansoubunko.kiyota.kansoubunko.flagment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.kansoubunko.kiyota.kansoubunko.activity.RegistActivity;

public class BookReviewInputDialogFlagment extends DialogFragment {

    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // ダイアログ生成  AlertDialogのBuilderクラスを指定してインスタンス化します
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        // タイトル設定
        dialogBuilder.setTitle("ダイアログタイトル");
        // 表示する文章設定
        dialogBuilder.setMessage("元画面に返す値を入力してください");

        // 入力フィールド作成
        final EditText editText = new EditText(getActivity());
        dialogBuilder.setView(editText);

        // OKボタン作成
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // editTextの内容を元画面に反映する
                // editTextから値を取得
                String returnValue = editText.getText().toString();
                // MainActivityのインスタンスを取得
                RegistActivity registActivity = (RegistActivity) getActivity();
                registActivity.setTextView(returnValue);
            }
        });

        // NGボタン作成
        dialogBuilder.setNegativeButton("NG", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 何もしないで閉じる
            }
        });

        // dialogBulderを返す
        return dialogBuilder.create();
    }

}
