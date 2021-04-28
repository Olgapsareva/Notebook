package ru.geekbrains.notebook.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.geekbrains.notebook.MainActivity;
import ru.geekbrains.notebook.R;

public class DialogConfirmationFragment extends DialogFragment {

    final String TAG = "[CONFIRMATION DIALOG]";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final View view = requireActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_confirmation, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(getResources().getString(R.string.confirmation_dialog_title))
                .setMessage(getResources().getString(R.string.confirmation_dialog_content))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.confirmation_dialog_positive_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG, String.format("результат %d", ((MainActivity) requireActivity()).onYesButtonClick()));
                                dismiss();
                            }
                        })

                .setNegativeButton(getResources().getString(R.string.confirmation_dialog_negative_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG, String.format("результат %d", ((MainActivity) requireActivity()).onCancelButtonClick()));
                                dismiss();
                            }
                        });

        return builder.create();
    }
}