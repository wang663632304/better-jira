package pl.edu.amu.wmi.betterjira.pages.timer;

import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.pages.Page;
import pl.edu.amu.wmi.betterjira.pages.issue.IssueFullInfoFragment;
import android.os.Bundle;
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

    @Override
    public String getTitle() {
	return "Timer";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	View inflate = inflater.inflate(R.layout.page_timer, null);

	buttonStartStop = (Button) inflate
		.findViewById(R.id.buttonStartWorking);
	textViewTitle = (TextView) inflate
		.findViewById(R.id.textViewWorkingTask);
	buttonLog = (Button) inflate.findViewById(R.id.buttonLogWork);

	if (getActivity().getIntent().getExtras() == null) {
	    throw new RuntimeException("Pass ISSUE KEY");
	}

	String issueKey = getActivity().getIntent().getExtras()
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

	return inflate;
    }

    @Override
    public void onClick(View v) {

	isWorking = !isWorking;

	if (isWorking) {
	    buttonStartStop.setText(R.string.stop_working);
	    // TODO count time
	} else {
	    buttonStartStop.setText(R.string.start_working);
	    // TODO count time
	    // TODO if there is any time counted pls show buttonLog now
	}
    }
}
