package com.example.deardoctor.doctor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.example.deardoctor.R;
import com.example.deardoctor.constants.CenterZoomLayoutManager;
import com.example.deardoctor.constants.OffsetItemDecoration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorSelectPop extends DialogFragment {
    @BindView(R.id.docSelect)
    RecyclerView doxctorRecyclerChoose;
    private List<Integer> mImgList;
    private Integer[] mImgs = {
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
            R.drawable.happydoctor,
    };
   private A adapter = new  A();
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert Dialog");
        builder.setMessage("Alert Dialog inside DialogFragment");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.select_doctor_pop, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initiateRecycler();
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onStart() {
        super.onStart(); Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("API123", "onCreate");
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
        boolean setFullScreen = false;
        if (getArguments() != null) {
            setFullScreen = getArguments().getBoolean("fullScreen");
        }

        if (setFullScreen)
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    void initiateRecycler(){
        mImgList = Arrays.asList(mImgs);
        //   LinearLayoutManager centerZoomLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);



        CenterZoomLayoutManager centerZoomLayoutManager = new
                CenterZoomLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false,doxctorRecyclerChoose);
        doxctorRecyclerChoose.setLayoutManager(centerZoomLayoutManager);
        Collections.shuffle(mImgList);
        doxctorRecyclerChoose.addItemDecoration(new OffsetItemDecoration(getActivity(),0,0));
        doxctorRecyclerChoose.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override


            public void run() {

                doxctorRecyclerChoose.invalidateItemDecorations();
                doxctorRecyclerChoose.invalidate();
                doxctorRecyclerChoose.scrollToPosition(1);
                SnapHelper snapHelper = new PagerSnapHelper();
                snapHelper.attachToRecyclerView(doxctorRecyclerChoose);
            }
        }, 200);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }
    class A extends RecyclerView.Adapter<VH> {

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new  VH(LayoutInflater.from(getContext()).inflate(R.layout.item_h, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder( VH holder, int position) {
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
