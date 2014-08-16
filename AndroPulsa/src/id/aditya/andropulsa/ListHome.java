package id.aditya.andropulsa;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListHome extends ArrayAdapter<String> {
	
	private final Activity context;
	private final String[] teks, teks2;
	private final Integer[] imageId;
	public ListHome(Activity context, String[] teks, String[] teks2, Integer[] imageId) {
		super(context, R.layout.list_home, teks);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.teks = teks;
		this.teks2 = teks2;
		this.imageId = imageId;
	}
	
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_home, null, true);
		TextView txtTeks = (TextView)rowView.findViewById(R.id.txt);
		TextView txtTeks2 = (TextView)rowView.findViewById(R.id.txt2);
		ImageView imageView = (ImageView)rowView.findViewById(R.id.img);
		txtTeks.setText(teks[position]);
		txtTeks2.setText(teks2[position]);
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
	
}
