package pl.edu.amu.wmi.betterjira.pages.timer;

import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.pages.Page;
import pl.edu.amu.wmi.betterjira.pages.issue.IssueFullInfoFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TimerFragment extends Page implements OnClickListener {

    private Button buttonStartStop;
    private Button buttonLog;

    private boolean isWorking = false;

    private TextView textViewTitle;
    private TextView textViewWorkedTime;
    private SharedPreferences defaultSharedPreferences;
    private String issueKey;

    @Override
    public String getTitle() {
	return "Timer";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	View inflate = inflater.inflate(R.layout.page_timer, null);

	defaultSharedPreferences = android.preference.PreferenceManager
		.getDefaultSharedPreferences(getActivity()
			.getApplicationContext());

	buttonStartStop = (Button) inflate
		.findViewById(R.id.buttonStartWorking);
	textViewTitle = (TextView) inflate
		.findViewById(R.id.textViewWorkingTask);
	textViewWorkedTime = (TextView) inflate
		.findViewById(R.id.textViewWorkedTime);
	buttonLog = (Button) inflate.findViewById(R.id.buttonLogWork);

	if (getActivity().getIntent().getExtras() == null) {
	    throw new RuntimeException("Pass ISSUE KEY");
	}

	issueKey = getActivity().getIntent().getExtras()
		.getString(IssueFullInfoFragment.EXTRA_STRING_ISSUE_KEY);

	textViewTitle.setText(String.format(
		getActivity().getString(R.string.working_on_issue), issueKey));

	buttonStartStop.setOnClickListener(this);

	buttonLog.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO implement send worklog and read saved time
	    }
	});

	if(defaultSharedPreferences.contains(issueKey + "T"))
	{
	    buttonStartStop.setText(R.string.stop_working);
	    isWorking = true;
	}
	
	return inflate;
    }

    @Override
    public void onClick(View v) {

	isWorking = !isWorking;

	if (isWorking) {
	    buttonStartStop.setText(R.string.stop_working);

	    defaultSharedPreferences.edit()
		    .putLong(issueKey + "T", System.currentTimeMillis())
		    .commit();
	    // TODO count time
	} else {

	    long time = defaultSharedPreferences.getLong(issueKey + "T",
		    System.currentTimeMillis());
	    
	    defaultSharedPreferences.edit().remove(issueKey + "T").commit();

	    time = System.currentTimeMillis() - time;

	    time += defaultSharedPreferences.getLong(issueKey, 0);

	    defaultSharedPreferences.edit().putLong(issueKey, time).commit();

	    textViewWorkedTime.setText("Worked: " + Long.toString((time / 1000) / 60) + "m");
	  
	    buttonStartStop.setText(R.string.start_working);
	}
    }
}
