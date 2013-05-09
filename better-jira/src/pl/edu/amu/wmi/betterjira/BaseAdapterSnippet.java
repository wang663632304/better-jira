package pl.edu.amu.wmi.betterjira;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Simple base Adapter that lets you use Holder pattern
 * 
 * @author Dawid Drozd
 * 
 */
public abstract class BaseAdapterSnippet extends BaseAdapter {

    @Override
    public long getItemId(int position) {
	return position;
    }

    /**
     * Probably you should override this and use it to retrieve inflated views
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	Object holder = null;
	if (convertView == null || convertView.getTag() == null) {
	    convertView = getInflatedRow(position);
	    holder = getNewHolder();
	    inflateHolder(holder, convertView);
	    convertView.setTag(holder);
	} else
	    holder = convertView.getTag();

	initializeHolder(holder, convertView, position);
	return convertView;
    }

    /**
     * Here you must only inflate new view of a row
     * 
     * @return new inflated view
     */
    public abstract View getInflatedRow(int position);

    /**
     * Only return your new Holder object
     * 
     * @return
     */
    protected abstract Object getNewHolder();

    /**
     * Initialize your fields with values for example textview.setText() and
     * other jobs here
     * 
     * @param holder
     * @param view
     * @param position
     */
    protected abstract void initializeHolder(Object yourHolder, View view,
	    int position);

    /**
     * Simply initiate you fields in holder by findingViewById in view object
     * 
     * @param holder
     * @param view
     */
    protected abstract void inflateHolder(Object yourHolder, View view);
}