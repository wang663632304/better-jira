package pl.edu.amu.wmi.betterjira.pages.filter;

import java.util.ArrayList;

import pl.edu.amu.wmi.betterjira.BaseAdapterSnippet;
import pl.edu.amu.wmi.betterjira.R;
import pl.edu.amu.wmi.betterjira.R.id;
import pl.edu.amu.wmi.betterjira.R.layout;
import pl.edu.amu.wmi.betterjira.api.function.data.Filter;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class FiltersAdapter extends BaseAdapterSnippet {

    private ArrayList<Filter> filters = new ArrayList<Filter>();

    public FiltersAdapter(Context context) {
	super(context);
    }

    @Override
    public int getCount() {
	return filters.size();
    }

    @Override
    public Object getItem(int position) {
	return filters.get(position);
    }

    @Override
    public View getInflatedRow(int position) {
	return getLayoutInflater().inflate(R.layout.row_filter, null);
    }

    @Override
    protected Object getNewHolder() {
	return new Holder();
    }

    @Override
    protected void initializeHolder(Object yourHolder, View view, int position) {

	Holder holder = (Holder) yourHolder;
	holder.textViewName.setText(filters.get(position).getName());
	holder.textViewJQL.setText(filters.get(position).getJql().getCommand());

    }

    @Override
    protected void inflateHolder(Object yourHolder, View view) {

	Holder holder = (Holder) yourHolder;
	holder.textViewName = (TextView) view.findViewById(R.id.textViewName);
	holder.textViewJQL = (TextView) view.findViewById(R.id.textViewJQL);
    }

    private class Holder {
	TextView textViewName;
	TextView textViewJQL;
    }

    public boolean addAll(ArrayList<Filter> filters) {
	boolean addAll = this.filters.addAll(filters);
	notifyDataSetChanged();
	return addAll;
    }
}
