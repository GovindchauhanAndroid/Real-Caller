package com.yandexindia.realcaller.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public FragmentCalls() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_calls, container, false);
        recyclerView = v.findViewById(R.id.rv_calls);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        CallsRvAdapter adapter = new CallsRvAdapter(getContext(), null);
        return v;
    }

    @Override
    public void onResume() {
        CallsRvAdapter adapter = new CallsRvAdapter(getContext(), getCalllogs());
        recyclerView.setAdapter(adapter);
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
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Date date1 = new Date(Long.parseLong(cursor.getString(date)));
            list.add(new ModelCalls(cursor.getString(name), cursor.getString(number), cursor.getString(duration),
                    date1.toString()));
        }
        cursor.close();
        return list;
    }

}