package com.ponlemas.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class MainActivityDialog extends DialogFragment {
    private String mensaje;
    private DialogListener dialogListener;

    public interface DialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.mensaje);
        builder.setPositiveButton(R.string.main_dialog_label_dialog_si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogListener.onDialogPositiveClick(MainActivityDialog.this);
            }
        });
        builder.setNegativeButton(R.string.main_dialog_label_dialog_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogListener.onDialogNegativeClick(MainActivityDialog.this);
            }
        });
        return builder.create();

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            dialogListener = (DialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }

    }
}
