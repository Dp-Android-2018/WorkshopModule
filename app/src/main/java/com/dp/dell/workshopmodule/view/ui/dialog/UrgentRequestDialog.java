package com.dp.dell.workshopmodule.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.dp.dell.workshopmodule.R;
/**
 * Created by DELL on 13/03/2018.
 */

public class UrgentRequestDialog extends Dialog {
    public UrgentRequestDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_urgent_request_layout);
    }
}
