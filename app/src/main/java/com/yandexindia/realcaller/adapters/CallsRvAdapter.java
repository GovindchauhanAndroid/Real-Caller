package com.yandexindia.realcaller.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yandexindia.realcaller.R;
import com.yandexindia.realcaller.models.ModelCalls;
import com.yandexindia.realcaller.ui.UserData;

import java.util.List;

public class CallsRvAdapter extends RecyclerView.Adapter<CallsRvAdapter.Viewholder> {
    private LayoutInflater layoutInflater;
    private Context mcontext;
    private List<ModelCalls> mlistcalls;
    private Button call_Btn;
    public String value;

    public CallsRvAdapter(Context context, List<ModelCalls> listcalls) {
        mlistcalls = listcalls;
        mcontext = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.items_calls, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TextView name, number, duration, date;
        name = holder.name;
        number = holder.number;
        duration = holder.duration;
        date = holder.date;
        name.setText(mlistcalls.get(position).getName());
        number.setText(mlistcalls.get(position).getNumber());
        duration.setText(mlistcalls.get(position).getDuration());
        date.setText(mlistcalls.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mlistcalls.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, number, duration, date;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.call_number);
            duration = itemView.findViewById(R.id.call_duration);
            date = itemView.findViewById(R.id.call_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            // gets item position
            if (position != RecyclerView.NO_POSITION) {
                // Check if an item was deleted, but the user clicked it before the UI removed it
                // We can access the data within the views
                value = number.getText().toString();

                Toast.makeText(mcontext, value, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
