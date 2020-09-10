package com.example.dc_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.skt.Tmap.TMapPOIItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DialogFragmentExample extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_sub ,null);
        builder.setView(view);

        final TextView textView1=(TextView)view.findViewById(R.id.textView1);
        final TextView textView2=(TextView)view.findViewById(R.id.textView2);
        final TextView textView3=(TextView)view.findViewById(R.id.textView3);
        final TextView textView4=(TextView)view.findViewById(R.id.textView4);
        final TextView textView5=(TextView)view.findViewById(R.id.textView5);
        final TextView textView6=(TextView)view.findViewById(R.id.textView6);
        final TextView textView7=(TextView)view.findViewById(R.id.textView7);
        final TextView textView8=(TextView)view.findViewById(R.id.textView8);

        Bundle poi_item=getArguments();
        ArrayList POIItem=poi_item.getParcelableArrayList("poi_item");
        Log.e("poi_size",Integer.toString(POIItem.size()));

        if(POIItem.size()<8){
            for(int i=0;i<POIItem.size();i++){
                switch (i){
                    case 0:
                        textView1.setText(((TMapPOIItem) POIItem.get(0)).getPOIName());
                        break;
                    case 1:
                        textView2.setText(((TMapPOIItem) POIItem.get(1)).getPOIName());
                        break;
                    case 2:
                        textView3.setText(((TMapPOIItem) POIItem.get(2)).getPOIName());
                        break;
                    case 3:
                        textView4.setText(((TMapPOIItem) POIItem.get(3)).getPOIName());
                        break;
                    case 4:
                        textView5.setText(((TMapPOIItem) POIItem.get(4)).getPOIName());
                        break;
                    case 5:
                        textView6.setText(((TMapPOIItem) POIItem.get(5)).getPOIName());
                        break;
                    case 6:
                        textView7.setText(((TMapPOIItem) POIItem.get(6)).getPOIName());
                        break;
                    case 7:
                        textView8.setText(((TMapPOIItem) POIItem.get(7)).getPOIName());
                        break;
                }
            }

        }else{
            for(int i=0;i<=8;i++){
                switch (i){
                    case 0:
                        textView1.setText(((TMapPOIItem) POIItem.get(0)).getPOIName());
//                        Log.e("test","1번쨰");
                        break;
                    case 1:
                        textView2.setText(((TMapPOIItem) POIItem.get(1)).getPOIName());
//                        Log.e("test","2번쨰");
                        break;
                    case 2:
                        textView3.setText(((TMapPOIItem) POIItem.get(2)).getPOIName());
//                        Log.e("test","3번쨰");
                        break;
                    case 3:
                        textView4.setText(((TMapPOIItem) POIItem.get(3)).getPOIName());
//                        Log.e("test","4번쨰");
                        break;
                    case 4:
                        textView5.setText(((TMapPOIItem) POIItem.get(4)).getPOIName());
//                        Log.e("test","5번쨰");
                        break;
                    case 5:
                        textView6.setText(((TMapPOIItem) POIItem.get(5)).getPOIName());
                        break;
                    case 6:
                        textView7.setText(((TMapPOIItem) POIItem.get(6)).getPOIName());
                        break;
                    case 7:
                        textView8.setText(((TMapPOIItem) POIItem.get(7)).getPOIName());
                        break;

                }
            }
        }

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView1.getText().toString();
//                int value=1;
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",1);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView2.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",2);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView3.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",3);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView4.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",4);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView5.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",5);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView6.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",6);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView7.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",7);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });
        textView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrive=textView8.getText().toString();
                Intent data = new Intent();
                data.putExtra ("arrive_text", arrive);
                data.putExtra("item",8);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
                dismiss();
            }
        });

        return builder.create();
    }
}
