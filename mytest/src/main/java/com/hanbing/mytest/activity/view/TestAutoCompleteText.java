/**
 * 
 */
package com.hanbing.mytest.activity.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-12-1
 */
public class TestAutoCompleteText extends BaseActivity {

    private static final String[] COUNTRIES ={  
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",  
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",  
        "Cote d'Ivoire", "Cambodia", "Cameroon", "Canada", "Cape Verde",  
        "Democratic Republic of the Congo", "Denmark", "Djibouti", "Dominica",  
        "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",  
   };  
    static final String[] array = {"item 0", "item 1", "item 2", "item 3", "item 4", "item 5", "item 0", "item 1", "item2", "item 3", "item 4", "item 5"};
    AutoCompleteTextView edit;
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        
        setContentView(R.layout.activity_autocompletetext);
        
        edit = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, array);
        
        MyAdapter adapter = new MyAdapter();
        edit.setThreshold(1);  
        edit.setAdapter(adapter);
        
        edit.setCompletionHint("最近的5条记录"); 
        
//        final AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);  
//        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,COUNTRIES);  
//        textView.setThreshold(1);
//        textView.setAdapter(adapter);  
        
//        edit = new AutoCompleteTextView(this);
//        edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
//        edit.setAdapter(adapter);
         
//        adapter.notifyDataSetChanged();
//        
//        LinearLayout layout = new LinearLayout(this);
//        
//        layout.addView(edit);
//        setContentView(layout);
        
    }
    
    class MyAdapter extends BaseAdapter implements Filterable {

	List<String> result;
	List<String> data;
	
	public MyAdapter()
	{
	    data = Arrays.asList(COUNTRIES);
	    result = new ArrayList<String>(data);
	}
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return result.size();
	}

	@Override
	public String getItem(int position) {
	    // TODO Auto-generated method stub
	    return result.get(position);
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    // TODO Auto-generated method stub
	    TextView text = new TextView(getApplicationContext());
	    
	    text.setText(getItem(position).toString());
	    return text;
	}

	@Override
	public Filter getFilter() {
	    // TODO Auto-generated method stub
	    return new Filter() {
	        
	        @Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	    	// TODO Auto-generated method stub
	    	
	            if (null != results && results.count > 0)
	            {
	        	notifyDataSetChanged();
	            } else {
	        	notifyDataSetInvalidated(); 
	            }
	        }
	        
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	    	// TODO Auto-generated method stub
	            result.clear();
	            if (null == constraint)
	        	return null;
	            FilterResults filterResults = new FilterResults();
	            
	            
	            for (String string : data)
	            {
	        	if (string.toLowerCase().contains(constraint.toString().toLowerCase()))
	        	{
	        	    result.add(string);
	        	}
	            }
	            
	            filterResults.count = result.size();
	            filterResults.values = result;
	    	return filterResults;
	        }
	    };
	}
	
    }

}
