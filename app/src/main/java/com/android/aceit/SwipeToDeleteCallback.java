package com.android.aceit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aceit.Activities.Checklist;
import com.android.aceit.Adapters.ChecklistAdapter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private ChecklistAdapter adapter;

    public SwipeToDeleteCallback(ChecklistAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // Not needed for swipe gestures
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.removeItem(position);
    }
    @Override
    public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemViewWidth = itemView.getWidth();

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX > 0) {
                // Swiping from left to right (perform actions for right swipe)
                // Draw the red background on the left side of the item view
                Paint backgroundPaint = new Paint();
                backgroundPaint.setColor(Color.RED);
                canvas.drawRect(
                        itemView.getLeft(),
                        itemView.getTop(),
                        itemView.getLeft() + dX,
                        itemView.getBottom(),
                        backgroundPaint
                );

                // Draw the delete text on the left side of the item view
                Paint textPaint = new Paint();
                textPaint.setColor(Color.WHITE);
                textPaint.setTextSize(40);
                String deleteText = "Delete";
                float margin = 32; // Adjust the margin value as needed

                float textX = itemView.getLeft() + margin;
                float textY = itemView.getTop() + (itemView.getHeight() / 2.0f) + (textPaint.getTextSize() / 2);
                canvas.drawText(deleteText, textX, textY, textPaint);
            } else {
                // Swiping from right to left (perform actions for left swipe)
                // Draw the red background on the right side of the item view
                Paint backgroundPaint = new Paint();
                backgroundPaint.setColor(Color.RED);
                canvas.drawRect(
                        itemView.getRight() + dX,
                        itemView.getTop(),
                        itemView.getRight(),
                        itemView.getBottom(),
                        backgroundPaint
                );

                // Draw the delete text on the right side of the item view
                Paint textPaint = new Paint();
                textPaint.setColor(Color.WHITE);
                textPaint.setTextSize(40);
                String deleteText = "Delete";
                float margin = 32; // Adjust the margin value as needed
                float textX = itemView.getRight()  - textPaint.measureText(deleteText) - margin;
                float textY = itemView.getTop() + (itemView.getHeight() / 2) + (textPaint.getTextSize() / 2);
                canvas.drawText(deleteText, textX, textY, textPaint);
            }
        }

        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}
