package pl.edu.amu.wmi.betterjira;

import java.util.ArrayList;
import java.util.Collection;

import pl.edu.amu.wmi.betterjira.pages.Page;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

public class PageIndicatorActivity extends SherlockFragmentActivity {

    public static final String EXTRA_TREE_NODE = "TREE_NODE";
    private BJFragmentPagerAdapter pagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_pages);

	pagesAdapter = new BJFragmentPagerAdapter(getSupportFragmentManager());

	ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	viewPager.setAdapter(pagesAdapter);

	TitlePageIndicator titlePageIndicator = (TitlePageIndicator) findViewById(R.id.pageIndicator);
	titlePageIndicator.setViewPager(viewPager);

	bindPages();
    }

    private void bindPages() {

	Bundle extras = getIntent().getExtras();
	if (extras == null || extras.containsKey(EXTRA_TREE_NODE) == false) {
	    throw new RuntimeException("You should pass tree node here!");
	}

	String treeNode = extras.getString(EXTRA_TREE_NODE);

	ArrayList<Page> nodes = TreeNodeManager.readNode(treeNode);

	pagesAdapter.addPages(nodes);
    }

    private static class BJFragmentPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Page> pages = new ArrayList<Page>();

	public BJFragmentPagerAdapter(FragmentManager fragmentManager) {
	    super(fragmentManager);
	}

	public void addPages(Collection<? extends Page> nodes) {
	    pages.addAll(nodes);
	}

	@Override
	public Fragment getItem(int index) {
	    return pages.get(index);
	}

	@Override
	public CharSequence getPageTitle(int position) {
	    return pages.get(position).getTitle();
	}

	@Override
	public int getCount() {
	    return pages.size();
	}

	public void addPage(Page page) {
	    pages.add(page);
	}
    }

}
