package com.example.deardoctor.constants;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {
    private int firstViewWidth = -1;
    private int lastViewWidth = -1;
    private Context ctx;
   private  int edgePadding,viewWidth;
    public OffsetItemDecoration(Context ctx, int edgePadding, int viewWidth) {

        this.ctx = ctx;
        this.edgePadding = edgePadding;
        this.viewWidth = viewWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        super.getItemOffsets(outRect, view, parent, state);


        int itemCount = state.getItemCount();

        int itemPosition = parent.getChildAdapterPosition(view);
        // no position, leave it alone
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }
//        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
//        int displayWidth = displayMetrics.widthPixels;
//        // viewWidth  = th * (displayMetrics.densityDpi / 160f);
//        int viewPixelWidth = (int) (viewWidth * (displayMetrics.densityDpi) / 160f);
//        int startEndPadding = (displayWidth - viewPixelWidth) / 4;

        // first item
        if (itemPosition == 0) {
             view.setVisibility(View.GONE);
        }
        // last item
        else if (itemCount > 0 && itemPosition == itemCount - 1) {
            view.setVisibility(View.GONE);
        }
        // every other item
        else {
            outRect.set(edgePadding, edgePadding, edgePadding, edgePadding);
        }
    }
    private void setupOutRect(Rect rect, int offset, boolean start) {
        if (start) {
            rect.left = offset;
        } else {
            rect.right = offset;
        }
    }
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}