package id.aditya.andropulsa;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListDistributor extends ArrayAdapter<String> {
	
	private final Activity context;
	//private final String[] teksNmPembeli, teksPembelian;
	private ArrayList<String> teksNmDistributor = new ArrayList<String>();
	private ArrayList<String> teksIdDs = new ArrayList<String>();
	private ArrayList<String> teksJmlData = new ArrayList<String>();

	public ListDistributor(Activity context, ArrayList<String> data, ArrayList<String> data2,
			ArrayList<String> data3) {
		super(context, R.layout.list_pembeli, data);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.teksNmDistributor = data;
		this.teksIdDs = data2;
		this.teksJmlData = data3;
	}
	
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_distributor, null, true);
		TextView teks1 = (TextView)rowView.findViewById(R.id.txtNmDistributor);
		TextView teks2 = (TextView)rowView.findViewById(R.id.txtIdDs);
		TextView teks3 = (TextView)rowView.findViewById(R.id.txtJmlData);
		ImageView img = (ImageView)rowView.findViewById(R.id.imgAnonym);
		teks1.setText(teksNmDistributor.get(position));
		teks2.setText(teksIdDs.get(position));
		teks3.setText("Jumlah Data : "+teksJmlData.get(position));
		img.setImageResource(R.drawable.ic_dist);
		return rowView;
	}

}
