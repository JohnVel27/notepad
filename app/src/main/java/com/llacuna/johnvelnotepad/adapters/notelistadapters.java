package com.llacuna.johnvelnotepad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.llacuna.johnvelnotepad.R;
import com.llacuna.johnvelnotepad.models.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class notelistadapters extends RecyclerView.Adapter<notelistadapters.notesviewholder> {

    private Context context;
    private List<notes> list;
    private notesclicklistener listener;

    public notelistadapters(Context context, List<notes> list, notesclicklistener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public notesviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false);
        return new notesviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesviewholder holder, int position) {
        notes note = list.get(position);

        holder.title.setText(note.getTitle());
        holder.title.setSelected(true);

        holder.textview_notes.setText(note.getTextnotes());

        holder.textview_date.setText(note.getDate());
        holder.textview_date.setSelected(true);

        if (note.isPinned()) {
            holder.pin.setImageResource(R.drawable.pin);
        } else {
            holder.pin.setImageResource(0);
        }

        int color_code = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.notes_container);
                return true;
            }
        });
    }

    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterlist(List<notes> filteredlist){
        list = filteredlist;
        notifyDataSetChanged();
    }

    public static class notesviewholder extends RecyclerView.ViewHolder {

        CardView notes_container;
        TextView title, textview_notes, textview_date;
        ImageView pin;

        public notesviewholder(@NonNull View itemView) {
            super(itemView);

            notes_container = itemView.findViewById(R.id.notes_container);
            title = itemView.findViewById(R.id.title);
            textview_notes = itemView.findViewById(R.id.textview_notes);
            textview_date = itemView.findViewById(R.id.textview_date);
            pin = itemView.findViewById(R.id.pin);
        }
    }
}

