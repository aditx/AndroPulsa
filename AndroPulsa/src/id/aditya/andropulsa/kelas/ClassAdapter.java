package id.aditya.andropulsa.kelas;

import id.aditya.andropulsa.R;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ClassAdapter extends BaseAdapter {

	Context ctx = null;
	List<DataObject> list = null;
	private LayoutInflater mInflater = null;
	public ClassAdapter(Activity activity, List<DataObject> data) {
		// TODO Auto-generated constructor stub
		this.ctx = activity;
		mInflater = activity.getLayoutInflater();
		this.list = data;
	}
	
	@Override
	public int getCount(){
		return list.size();
	}
	
	@Override
	public Object getItem(int arg0){
		return null;
	}
	
	@Override
	public long getItemId(int arg0){
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2){
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.activity_alert, null);
			
			holder.titlename = (TextView) convertView.findViewById(R.id.textView_titllename);
			holder.idname = (TextView) convertView.findViewById(R.id.textView_alertnumber);
	        convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		DataObject datavalue=list.get(position);
		holder.titlename.setText(datavalue.getDtNmPembeli().toString());
		holder.idname.setText(datavalue.getDtIdPembeli().toString());
		
		return convertView;
	}
	
	private static class ViewHolder {

		public TextView idname;
		public TextView titlename;
        
    }

}
