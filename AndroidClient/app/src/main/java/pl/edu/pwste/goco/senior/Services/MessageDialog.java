package pl.edu.pwste.goco.senior.Services;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by goco on 07.01.2018.
 */

public class MessageDialog {
    private String message;
    private Bundle bundle;

    public MessageDialog(String message, Bundle bundle) {
        this.message = message;
        this.bundle = bundle;
        MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
        messageDialogFragment.onCreateDialog(bundle);
    }

    @SuppressLint("ValidFragment")
    public class MessageDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(message)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
