package com.example.deardoctor.doctor;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.example.deardoctor.R;
import com.example.deardoctor.base.FragmentBase;
import com.example.deardoctor.constants.CenterZoomLayoutManager;
import com.example.deardoctor.constants.CircleRecyclerView;
import com.example.deardoctor.constants.ItemViewMode;
import com.example.deardoctor.constants.OffsetItemDecoration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorAdd extends FragmentBase {
    @BindView(R.id.circleDoctorRecycler)
    CircleRecyclerView mCircleRecyclerView;
    @BindView(R.id.doxctorRecyclerChoose)
    RecyclerView doxctorRecyclerChoose;
    @BindView(R.id.scrollCountImage)
    TextView scrollCount;
    private ItemViewMode mItemViewMode;
    private List<Integer> mImgList;
    private int positioned,previous;
    A adapter = new A();
    private Integer[] mImgs = {
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
    };
    @Override
    protected void onViewBound(View view) {

        mImgList = Arrays.asList(mImgs);
        //   LinearLayoutManager centerZoomLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);



        CenterZoomLayoutManager centerZoomLayoutManager = new
                CenterZoomLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false,doxctorRecyclerChoose);
        doxctorRecyclerChoose.setLayoutManager(centerZoomLayoutManager);
        Collections.shuffle(mImgList);
        doxctorRecyclerChoose.addItemDecoration(new OffsetItemDecoration(getActivity(),0,0));
        doxctorRecyclerChoose.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        new Handler().postDelayed(new Runnable() {
            @Override


            public void run() {

                doxctorRecyclerChoose.invalidateItemDecorations();
                doxctorRecyclerChoose.invalidate();
                doxctorRecyclerChoose.scrollToPosition(1);

                snapHelper.attachToRecyclerView(doxctorRecyclerChoose);
            }
        }, 200);
        final View[] lastSnappedTime = {null};
        doxctorRecyclerChoose.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(doxctorRecyclerChoose.getLayoutManager());
                    positioned = Objects.requireNonNull(doxctorRecyclerChoose.getLayoutManager()).getPosition(centerView);
                    scrollCount.setText(positioned+"");
                    Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_in);
                    centerView.startAnimation(anim);
                    anim.setFillAfter(true);
                    lastSnappedTime[0] = centerView;
                } else if (lastSnappedTime[0] != null) {
                    lastSnappedTime[0] = null;
                }
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL && lastSnappedTime[0] != null) {
                    Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_out_tv);
                    lastSnappedTime[0].startAnimation(anim);
                    anim.setFillAfter(true);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    protected int layoutRes() {
        return R.layout.doctor_add;
    }

    class A extends RecyclerView.Adapter<VH> {

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(LayoutInflater.from(getContext()).inflate(R.layout.item_h, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
            Glide.with(Objects.requireNonNull(getContext()))
                    .load(mImgList.get(position % mImgList.size()))
                    .into(holder.iv);
//            if (position == 0 || position == mImgList.size() - 1){
//                holder.mainPhotoCard.setVisibility(View.GONE);
//            }

//            if (position == positioned){
//                previous = positioned;
//                holder.tv.setVisibility(View.VISIBLE);
//            }else {
//                holder.tv.setVisibility(View.GONE);
//            }



        }
        @Override
        public int getItemCount() {
            return mImgList.size();
        }
    }

    class VH extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        ImageView iv;

        @BindView(R.id.item_text)
        TextView tv;


        public VH(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


}
