package com.android.aceit;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView maintv;
    public CheckBox checklist_checkBox;
    public TextView  checklist_textview,expand_title,expand_content;
    public Button expand_button;
    public ConstraintLayout swipelayout;

    public ImageButton checkList_imagebtn;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
         maintv = itemView.findViewById(R.id.tvMainRv);
        checklist_checkBox = itemView.findViewById(R.id.cbCHecklistbtn);
        checklist_textview = itemView.findViewById(R.id.etChecklistrv);
        checkList_imagebtn = itemView.findViewById(R.id.ibbtnRemoveChecklist);
        expand_title = itemView.findViewById(R.id.tvexpandLetterTitile);
        expand_content = itemView.findViewById(R.id.tvExpandLetterContent);
        expand_button = itemView.findViewById(R.id.btnexpandLetter);

        swipelayout = (ConstraintLayout) itemView.findViewById(R.id.cvChecklistSwipe);

    }


}
