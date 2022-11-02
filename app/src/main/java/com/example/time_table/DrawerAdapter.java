package com.example.time_table;
import android.util.*;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.*;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

     private List<DrawerItem> items;
     private Map<Class<? extends DrawerItem>, Integer> viewTypes;
     private SparseArray<DrawerItem> holderfactories;
     private OnItemSelectedListener listener;

     public DrawerAdapter(List<DrawerItem> items){
         this.items=items;
         this.viewTypes=new HashMap<>();
         this.holderfactories=new SparseArray<>();
         processViewTypes();
     }

    private void processViewTypes() {
         int type = 0;
         for(DrawerItem item : items){
             if(!viewTypes.containsKey(item.getClass())){
                 viewTypes.put(item.getClass(),type);
                 holderfactories.put(type,item);
                 type++;
             }
         }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder=holderfactories.get(viewType).createViewHolder(parent);
        holder.drawerAdapter=this;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         items.get(position).bindViewHolder(holder);

    }

    @Override
    public int getItemViewType(int position) {
        return viewTypes.get(items.get(position).getClass());
    }

    public void setSelected(int position){
         DrawerItem newChecked = items.get(position);
         if (!newChecked.isSelectable()){
             return;
         }
         DrawerItem item;
         for(int i=0;i<items.size();i++){
             item = items.get(i);
             if(item.isChecked()){
                 item.setChecked(false);
                 notifyItemChanged(i);
                 break;
             }
         }

         newChecked.setChecked(true);
         notifyItemChanged(position);

         if(listener!=null){
             listener.onItemSelected(position);
        }

    }

    public void setListener(OnItemSelectedListener listener){
         this.listener=listener;
    }

    public interface OnItemSelectedListener{
         void onItemSelected(int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private DrawerAdapter drawerAdapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            drawerAdapter.setSelected(getAdapterPosition());
        }
    }
}
