package com.example.officeroom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.myViewHolder>{

    public List<AnnouncementsModelClass> announcementList;

    public DataAdapter (List<AnnouncementsModelClass> announcementList){
        this.announcementList = announcementList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcements,parent,false);

        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.announcementTitle.setText(announcementList.get(position).getTitle());
        holder.announcementMessage.setText(announcementList.get(position).getAnnouncements());
        holder.announcementFrom.setText(announcementList.get(position).getFrom());
    }

    @Override
    public int getItemCount() {
        return announcementList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView announcementTitle,announcementMessage,announcementFrom;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            announcementTitle = itemView.findViewById(R.id.announcement_title);
            announcementMessage = itemView.findViewById(R.id.announcement_message);
            announcementFrom = itemView.findViewById(R.id.announcement_from);
        }
    }
}
