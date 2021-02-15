package com.yandexindia.realcaller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yandexindia.realcaller.R;
import com.yandexindia.realcaller.models.ModelContacts;

import java.util.List;

public class ContactRvAdapter extends RecyclerView.Adapter<ContactRvAdapter.Viewholder> {
    private Context mcontext;
    private LayoutInflater layoutInflater;
    private List<ModelContacts> mlistContacts;

    public ContactRvAdapter(Context context, List<ModelContacts> listContacts) {
        mlistContacts = listContacts;
        mcontext = context;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.items_contacts, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        TextView Contact_name, Contact_number;

        Contact_number = holder.Contact_number;
        Contact_name = holder.Contact_name;

        Contact_name.setText(mlistContacts.get(position).getName());
        Contact_number.setText(mlistContacts.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return mlistContacts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView Contact_name, Contact_number;

        public Viewholder(View itemView) {
            super(itemView);
            Contact_name = itemView.findViewById(R.id.contact_name);
            Contact_number = itemView.findViewById(R.id.contact_number);
        }
    }
}
