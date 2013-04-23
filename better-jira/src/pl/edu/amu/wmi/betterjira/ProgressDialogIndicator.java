package pl.edu.amu.wmi.betterjira;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ProgressDialogIndicator extends Dialog {

    private ProgressBar progressBarLogin;

    public ProgressDialogIndicator(Context context) {
	super(context, android.R.style.Theme_Translucent_NoTitleBar);

	progressBarLogin = new ProgressBar(context);
	progressBarLogin.setLayoutParams(new LayoutParams(
		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	progressBarLogin
		.setBackgroundResource(android.R.drawable.progress_horizontal);
	progressBarLogin.setIndeterminate(true);

	LinearLayout linearLayout = new LinearLayout(context);
	linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.MATCH_PARENT,
		LinearLayout.LayoutParams.MATCH_PARENT));

	linearLayout.addView(progressBarLogin);
	linearLayout.setGravity(Gravity.CENTER);
	setContentView(linearLayout);
	getWindow().setLayout(LayoutParams.FILL_PARENT,
		LayoutParams.FILL_PARENT);

    }
}
