package pl.edu.amu.wmi.betterjira.pages.issue;

import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.pages.Page;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IssueFullInfoFragment extends Page {

    public static final String EXTRA_ISSUE_KEY = "issue_key";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {

	return inflater.inflate(R.layout.fragment_task_full_info, null);
    }

    @Override
    public String getTitle() {
	return "Task";
    }
}
