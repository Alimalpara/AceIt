package com.android.aceit;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView mainRecylerviewText, maintv;
    public CheckBox checklist_checkBox;
    public TextView  checklist_textview,expand_title,expand_content;
    public Button expand_button;
    public ConstraintLayout swipelayout;

    public CardView expandcontentcardview;
    public ImageView toggleForexpandandCollapseImage;




    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

         maintv = itemView.findViewById(R.id.tvForListqfc);
        checklist_checkBox = itemView.findViewById(R.id.cbCHecklistbtn);
        checklist_textview = itemView.findViewById(R.id.etChecklistrv);

        expand_title = itemView.findViewById(R.id.tvexpandLetterTitile);
        expand_content = itemView.findViewById(R.id.tvExpandLetterContent);
        expand_button = itemView.findViewById(R.id.btnexpandLetter);
        mainRecylerviewText = itemView.findViewById(R.id.tvforrvMain);

        swipelayout = (ConstraintLayout) itemView.findViewById(R.id.cvChecklistSwipe);


        //expand content
        expandcontentcardview = itemView.findViewById(R.id.cardViewExpandContent);

        toggleForexpandandCollapseImage = itemView.findViewById(R.id.ivExapndandCollapse);

       /* // Enable marquee effect for the TextView
        if (maintv != null) {
            //marquee testing
            maintv.setSelected(true);
            maintv.requestFocus();        }*/


    }


}
