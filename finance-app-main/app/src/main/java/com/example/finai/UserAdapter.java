package com.example.finai;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private UserRepository userRepository;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        userRepository = new UserRepository(context);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.firstNameTextView.setText(user.getFirstName());
        holder.lastNameTextView.setText(user.getLastName());
        holder.phoneTextView.setText(user.getPhone());
        holder.riskStatusTextView.setText(user.getRiskStatus());
        holder.isAdminTextView.setText(user.isAdmin() ? "Admin" : "User");

        holder.editButton.setOnClickListener(v -> showEditUserDialog(user, position));
        holder.deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog(user, position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private void showEditUserDialog(User user, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit User");

        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_user, (ViewGroup) null, false);
        final EditText inputFirstName = viewInflated.findViewById(R.id.input_first_name);
        final EditText inputLastName = viewInflated.findViewById(R.id.input_last_name);
        final EditText inputPhone = viewInflated.findViewById(R.id.input_phone);
        final EditText inputRiskStatus = viewInflated.findViewById(R.id.input_risk_status);
        final EditText inputIsAdmin = viewInflated.findViewById(R.id.input_is_admin);

        inputFirstName.setText(user.getFirstName());
        inputLastName.setText(user.getLastName());
        inputPhone.setText(user.getPhone());
        inputRiskStatus.setText(user.getRiskStatus());
        inputIsAdmin.setText(user.isAdmin() ? "1" : "0");

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            dialog.dismiss();
            user.setFirstName(inputFirstName.getText().toString());
            user.setLastName(inputLastName.getText().toString());
            user.setPhone(inputPhone.getText().toString());
            user.setRiskStatus(inputRiskStatus.getText().toString());
            user.setAdmin(inputIsAdmin.getText().toString().equals("1"));

            userRepository.open();
            userRepository.updateUser(user);
            userRepository.close();

            userList.set(position, user);
            notifyItemChanged(position);
        });

        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void showDeleteConfirmationDialog(User user, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    userRepository.open();
                    userRepository.deleteUser(user.getId());
                    userRepository.close();

                    userList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, userList.size());
                    Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView firstNameTextView, lastNameTextView, phoneTextView, riskStatusTextView, isAdminTextView;
        Button editButton, deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_view_first_name);
            lastNameTextView = itemView.findViewById(R.id.text_view_last_name);
            phoneTextView = itemView.findViewById(R.id.text_view_phone);
            riskStatusTextView = itemView.findViewById(R.id.text_view_risk_status);
            isAdminTextView = itemView.findViewById(R.id.text_view_is_admin);
            editButton = itemView.findViewById(R.id.button_edit);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}