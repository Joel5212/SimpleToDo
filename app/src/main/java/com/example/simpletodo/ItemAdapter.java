package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for displaying data from the model into a row in the recycler view
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

public interface OnClickListener{


    void onItemClicked(int adapterPosition);
}
public interface OnLongClickListener{
    void onItemLongClicked(int position);   //looks for what position is being long clicked

}
OnLongClickListener longClickListener;
List<String> items;
OnClickListener clickListener;
    public ItemAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
            this.items = items;
            this.longClickListener = longClickListener;
            this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //Responsible for creating each view
        //Use layout inflater to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        //wrap it inside a View Holder and return it
        return new ViewHolder(todoView);
    }
    //responsible for binding data to a particular view holder


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {          //taking data at a particular position and putting it in a view holder
            // Grab the item at the position
        String item = items.get(position);
        //Bind the item into the specified view
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {                                 //update the view inside of the view holder with this data
        tvItem.setText(item);
        tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClicked(getAdapterPosition());
            }
        });
        tvItem.setOnLongClickListener(new View.OnLongClickListener() {

            //Trying to long click an item from the viewholder to delete it

            @Override
            public boolean onLongClick(View v) {
                //Notify the listener which position was long pressed.
                longClickListener.onItemLongClicked(getAdapterPosition());
                return true;
            }
        });
    }
    }



}
