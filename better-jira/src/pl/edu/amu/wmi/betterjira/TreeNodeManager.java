package pl.edu.amu.wmi.betterjira;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.pages.DashboardFragment;
import pl.edu.amu.wmi.betterjira.pages.ErrorPageFragment;
import pl.edu.amu.wmi.betterjira.pages.Page;
import pl.edu.amu.wmi.betterjira.pages.filter.FiltersFragment;
import pl.edu.amu.wmi.betterjira.pages.issue.IssueFullInfoFragment;
import pl.edu.amu.wmi.betterjira.pages.issue.IssueListFragment;
import pl.edu.amu.wmi.betterjira.pages.project.ProjectsFragment;
import pl.edu.amu.wmi.betterjira.pages.timer.TimerFragment;
import android.util.Log;

public class TreeNodeManager {

    public static ArrayList<Page> readNode(String node) {

	ArrayList<Page> pagesInNode = new ArrayList<Page>();

	// TODO Change it for database/////////////////////////
	if (node == null) {
	    pagesInNode.add(new ProjectsFragment());
	    pagesInNode.add(new FiltersFragment());
	    pagesInNode.add(new DashboardFragment());
	} else if (node.equals(FiltersFragment.class.getSimpleName())) {
	    pagesInNode.add(new IssueListFragment());
	    pagesInNode.add(new FiltersFragment());
	} else if (node.equals(IssueListFragment.class.getSimpleName())) {
	    pagesInNode.add(new IssueFullInfoFragment());
	    pagesInNode.add(new TimerFragment());
	} else {
	    Log.e("TreeNodeManager", "I don't know any nodes for this node: "
		    + node);
	    pagesInNode.add(new ErrorPageFragment());
	}

	// /////////////////////////////////////////////////////

	if (pagesInNode.size() < 1) {
	    throw new RuntimeException("I shouldn't have 0 nodes");
	}
	return pagesInNode;
    }
}
