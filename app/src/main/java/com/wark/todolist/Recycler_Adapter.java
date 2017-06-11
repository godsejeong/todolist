    package com.wark.todolist;

    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.support.v7.widget.RecyclerView;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.google.gson.Gson;

    import java.util.ArrayList;
    import java.util.Collections;

    import static android.content.Context.MODE_PRIVATE;

/**
 * Created by choi on 2017. 5. 18..
 */

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder> implements ItemTouchHelperAdapter {
    ArrayList<Data> items = new ArrayList<>();
    int itemLayout;
    Context context;
    public Recycler_Adapter(ArrayList<Data> items, int itemLayout, Context context){
        this.items = items;
        this.itemLayout = itemLayout;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lest_item,viewGroup,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Data item = items.get(position);
        viewHolder.title.setText(item.getTitle());
        if(items.get(position).getTime()!=null)
            viewHolder.time.setText("" + item.getDate().toString() + " / " + items.get(position).getTime().toString() + "");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Set_todo.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
                itemClick.onClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemDismiss(int position) {
        items.get(position).setDate(null);
        items.get(position).setTime(null);
        items.get(position).setTitle(null);
        items.remove(position);
        saveNowData();
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        saveNowData();
        return true;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_img;
        public TextView title;
        public  TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            item_img = (ImageView) itemView.findViewById(R.id.item_imageView);
            title = (TextView) itemView.findViewById(R.id.textView);
            time = (TextView) itemView.findViewById(R.id.textView_2);
        }
    }

    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(items);
        editor.putString("save", json);
        Log.d("asdf",json);
        editor.commit();
    }

    private ItemClick itemClick;
    public interface ItemClick {
        void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

}
