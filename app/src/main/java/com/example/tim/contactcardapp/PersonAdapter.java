package com.example.tim.contactcardapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tim.contactcardapp.model.Person;

import java.util.List;

/**
 * Created by tim on 5-9-2017.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    List<Person> data;

    public PersonAdapter(List<Person> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_person, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Person item = getItem(position);

        holder.firstName.setText(item.getName().getFirst());
        holder.lastName.setText(item.getName().getLast());
        holder.email.setText(item.getEmail());

        Glide.with(holder.itemView.getContext())
                .load(item.getPicture().getLarge())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profilePicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().
                        startActivity(ProfilePageActivity.getStartIntent(holder.itemView.getContext(), item));
            }
        });
    }

    public Person getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePicture;
        TextView firstName;
        TextView lastName;
        TextView email;

        public ViewHolder(View itemView) {
            super(itemView);

            profilePicture = (ImageView) itemView.findViewById(R.id.profile_picture);
            firstName = (TextView) itemView.findViewById(R.id.first_name);
            lastName = (TextView) itemView.findViewById(R.id.last_name);
            email = (TextView) itemView.findViewById(R.id.email);
        }
    }
}
