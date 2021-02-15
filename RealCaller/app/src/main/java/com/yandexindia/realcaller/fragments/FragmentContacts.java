package com.yandexindia.realcaller.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yandexindia.realcaller.R;
import com.yandexindia.realcaller.adapters.ContactRvAdapter;
import com.yandexindia.realcaller.models.ModelContacts;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {
    private View v;
    private RecyclerView recyclerView;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_contacts, container, false);
        recyclerView = v.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        return v;

    }


    @Override
    public void onResume() {
        ContactRvAdapter contactRvAdapter = new ContactRvAdapter(getContext(), getContacts());
        recyclerView.setAdapter(contactRvAdapter);
        super.onResume();
    }


    private List<ModelContacts> getContacts() {
        List<ModelContacts> list = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null
                , ContactsContract.Contacts.DISPLAY_NAME + "ASC");
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            list.add(new ModelContacts(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));

        }
        return list;
    }
}
