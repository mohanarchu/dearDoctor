package com.example.deardoctor.Weeks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.deardoctor.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeeKAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements   SwipeAndDragHelper.ActionCompletionContract{
    private ItemTouchHelper touchHelper;
    private Context context;
    private ArrayList<WeekArray> weekArrays;
    private ArrayList<String> times;
    ArrayList<Integer> myLine;
    int positions= 0;
    AdapterClicked adapterClicked;
    public WeeKAdapter(Context context, ArrayList<WeekArray> weekArrays, ArrayList<String> times, ArrayList<Integer> myLine,AdapterClicked adapterClicked) {
        this.weekArrays = weekArrays;
        this.context = context;
        this.times = times;
        this.myLine =myLine;
        this.adapterClicked = adapterClicked;
    }

    enum  TYPE {
        EMPTY,
        AVAILABLE,
        TIME
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType ==  TYPE.AVAILABLE.ordinal()) {
            view = LayoutInflater.from(context).inflate(R.layout.slot, parent, false);
            return new  WeekHolder(view);
        } else if(viewType == TYPE.EMPTY.ordinal()) {
            view = LayoutInflater.from(context).inflate(R.layout.empty_view, parent, false);
            return new  HOLDER(view);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.time_view, parent, false);
            return new  timeView(view);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE.AVAILABLE.ordinal()) {

            WeekHolder holder1 = (WeekHolder)holder;
            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    new int[] {0xFF616261,0xFF131313});
            gd.setCornerRadius(0f);
         //   holder1.imageSlot.setImageTintList(ColorStateList.valueOf(weekArrays.get(position).getColor()));

            holder1.slotToken.setText(weekArrays.get(position).getSlotNumber());
        } else if (holder.getItemViewType() ==  TYPE.TIME.ordinal()) {
            timeView timeView = (WeeKAdapter.timeView) holder;
            timeView.times.setText(weekArrays.get(position).getEnd());
            timeView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterClicked.showPos(timeView,position,weekArrays);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return weekArrays.size();
    }
    @Override
    public int getItemViewType(int position) {

        if (weekArrays.get(position).getSlopCount()  == -1) {
            return TYPE.TIME.ordinal();
        } else {
            switch (weekArrays.get(position).getSlopCount()){
                case 0:
                    return TYPE.EMPTY.ordinal();
                case 1:
                    return TYPE.AVAILABLE.ordinal();
                    default:
                        return TYPE.AVAILABLE.ordinal();
            }
        }
    }
    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        WeekArray targetUser = weekArrays.get(oldPosition);
        WeekArray user = new WeekArray(targetUser);
        weekArrays.remove(oldPosition);
        weekArrays.add(newPosition, user);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {
        weekArrays.remove(position);
        notifyItemRemoved(position);
    }

    void setTouchHelper(ItemTouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }

    static class HOLDER extends RecyclerView.ViewHolder {

        public HOLDER(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class WeekHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.slotToken)
        TextView slotToken;
        @BindView(R.id.imageSlot)
        ImageView imageSlot;
        public WeekHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public class timeView extends RecyclerView.ViewHolder {
        @BindView(R.id.times)
        TextView times;
        public timeView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
