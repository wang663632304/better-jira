package pl.edu.amu.wmi.betterjira;

import pl.edu.amu.wmi.betterjira.pages.PageFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.viewpagerindicator.TitlePageIndicator;

public class PageIndicatorActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_pages);

	ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
	viewPager.setAdapter(new MyFragmentPagerAdapter(
		getSupportFragmentManager()));

	TitlePageIndicator titlePageIndicator = (TitlePageIndicator) findViewById(R.id.pageIndicator);
	titlePageIndicator.setViewPager(viewPager);

    }

    private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	public MyFragmentPagerAdapter(FragmentManager fm) {
	    super(fm);
	}

	@Override
	public Fragment getItem(int index) {

	    return PageFragment.newInstance("My Message " + index);
	}

	@Override
	public CharSequence getPageTitle(int position) {
	    return ("Position " + position);
	}

	@Override
	public int getCount() {

	    return 10;
	}
    }

}
