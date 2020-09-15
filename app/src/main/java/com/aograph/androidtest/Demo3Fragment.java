package com.aograph.androidtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

/**
 * @author tangqipeng
 * @date 2020/8/26 6:54 PM
 * @email tangqipeng@aograph.com
 */
public class Demo3Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.demo_fragment, container, false);
        WebView gf = (WebView) view.findViewById(R.id.webview);
        Button btn_jump = view.findViewById(R.id.btn_jump);

        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent(Demo3Fragment.this.getActivity(), MainActivity.class);
                    Demo3Fragment.this.getActivity().startActivity(intent);
//                DemoFragment.this.getActivity().finish();
                } catch (Exception e) {
                }

            }
        });

//        int type = getArguments().getInt("type", 0);
//        TextView textView = (TextView) view.findViewById(R.id.textview);
//        textView.setText(type == 0 ? "1st Fragment" : type == 1 ? "2nd Fragment" : type == 2 ? "3rd Fragment" : "4th Fragment");
        String gifName = "kof3.gif";
//        switch (type) {
//            case 0:
//                gifName = "kof1.gif";
//                break;
//            case 1:
//                gifName = "kof2.gif";
//                break;
//            case 2:
//                gifName = "kof3.gif";
//                break;
//            case 3:
//                gifName = "kof4.gif";
//                break;
//        }
        String gifFilePath = "file:///android_asset/" + gifName;
        String data = "<HTML><Div align=\"center\" margin=\"0px\"><IMG src=\"" + gifFilePath + "\" margin=\"0px\"/></Div>";
        gf.loadDataWithBaseURL(gifFilePath, data, "text/html", "utf-8", null);
        return view;
    }

    private String getRandom() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9999));
    }
}
