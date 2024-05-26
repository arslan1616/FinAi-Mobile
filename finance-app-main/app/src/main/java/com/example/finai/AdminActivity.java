package com.example.finai;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private Button manageLayoutButton;
    private Button manageUsersButton;
    private Button logoutButton;

    private boolean isEditingLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        gridLayout = findViewById(R.id.grid_layout);
        manageLayoutButton = findViewById(R.id.btn_manage_layout);
        manageUsersButton = findViewById(R.id.btn_manage_users);
        logoutButton = findViewById(R.id.btn_logout);

        manageLayoutButton.setOnClickListener(v -> toggleLayoutEditing());
        manageUsersButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, ManageUsersActivity.class);
            startActivity(intent);
        });
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminActivity.this, LogintoContact.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        if (isEditingLayout) {
            enableDragAndDrop();
        }
    }

    private void toggleLayoutEditing() {
        isEditingLayout = !isEditingLayout;
        if (isEditingLayout) {
            enableDragAndDrop();
        } else {
            disableDragAndDrop();
        }
    }

    private void enableDragAndDrop() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            child.setOnTouchListener(new DragTouchListener());
        }
    }

    private void disableDragAndDrop() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            child.setOnTouchListener(null);
        }
    }

    private class DragTouchListener implements View.OnTouchListener {
        private float dX, dY;
        private int lastAction;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    view.setY(event.getRawY() + dY);
                    view.setX(event.getRawX() + dX);
                    lastAction = MotionEvent.ACTION_MOVE;
                    return true;

                case MotionEvent.ACTION_UP:
                    if (lastAction == MotionEvent.ACTION_DOWN) {
                    }
                    return true;

                default:
                    return false;
            }
        }
    }
}