package com.yandexindia.realcaller.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yandexindia.realcaller.R;
import com.yandexindia.realcaller.adapters.CallsRvAdapter;
import com.yandexindia.realcaller.models.ModelCalls;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FragmentCalls extends Fragment {
    private RecyclerView recyclerView;
    private View v;
    private ProgressBar call_progress;
    private String durationFormatted = null;
    private Button Call_Btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_calls, container, false);
        recyclerView = v.findViewById(R.id.rv_calls);
        Call_Btn = v.findViewById(R.id.call_button);
        call_progress = v.findViewById(R.id.call_progress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        return v;

    }

    @Override
    public void onResume() {
        call_progress.setVisibility(View.VISIBLE);
        /* Create an Intent that will start the Menu-Activity. */
        CallsRvAdapter adapter = new CallsRvAdapter(getContext(), getCalllogs());
        recyclerView.setAdapter(adapter);
        call_progress.setVisibility(View.GONE);
        super.onResume();
    }


    private List<ModelCalls> getCalllogs() {
        List<ModelCalls> list = new ArrayList<>();


        Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);

        int date = cursor.getColumnIndex(CallLog.Calls.DATE);

        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

//        String durationFormatted = null;
//        if (duration < 60) {
//            durationFormatted = duration + "sec";
//        } else {
//            int min = duration / 60;
//            int sec = duration % 60;
//
//            if (sec == 0)
//                durationFormatted = min + " min";
//            else
//                durationFormatted = min + " min " + sec + " sec";
//        }


        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Date date1 = new Date(Long.parseLong(cursor.getString(date)));
            String Str_duration_call = cursor.getString(duration);
            String durationFormatted = null;
            if (Integer.parseInt(Str_duration_call) < 60) {
                durationFormatted = Str_duration_call + "sec";
            } else {
                int min = Integer.parseInt(Str_duration_call) / 60;
                int sec = Integer.parseInt(Str_duration_call) % 60;

                if (sec == 0)
                    durationFormatted = min + " min";
                else
                    durationFormatted = min + " min " + sec + " sec";
            }
            String str_name = cursor.getString(name);
            str_name = str_name == null || str_name.equals("") ? "Unknown" : str_name;
            list.add(new ModelCalls(str_name, cursor.getString(number), durationFormatted,
                    date1.toString()));
        }
        cursor.close();
        return list;


    }


}