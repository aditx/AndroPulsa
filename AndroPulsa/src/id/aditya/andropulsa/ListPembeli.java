package id.aditya.andropulsa;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListPembeli extends ArrayAdapter<String> {
	
	private final Activity context;
	//private final String[] teksNmPembeli, teksPembelian;
	private ArrayList<String> teksNmPembeli = new ArrayList<String>();
	private ArrayList<String> teksId = new ArrayList<String>();
	private ArrayList<String> teksPembelian = new ArrayList<String>();
	private ArrayList<String> teksUtang = new ArrayList<String>();
	private String preference;

	public ListPembeli(Activity context, ArrayList<String> data, ArrayList<String> data2,
			ArrayList<String> data3, ArrayList<String> dataUtang, String pref) {
		super(context, R.layout.list_pembeli, data);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.teksNmPembeli = data;
		this.teksId = data2;
		this.teksPembelian = data3;
		this.teksUtang = dataUtang;
		this.preference = pref;
	}
	
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_pembeli, null, true);
		TextView teks1 = (TextView)rowView.findViewById(R.id.txtNmPembeli);
		TextView teks2 = (TextView)rowView.findViewById(R.id.txtId);
		TextView teks3 = (TextView)rowView.findViewById(R.id.txtTotal);
		TextView teks4 = (TextView)rowView.findViewById(R.id.txtUtang);
		ImageView img = (ImageView)rowView.findViewById(R.id.imgAnonym);
		teks1.setText(teksNmPembeli.get(position));
		teks2.setText(teksId.get(position));
		teks3.setText("Total Pembelian : Rp."+teksPembelian.get(position));
		teks4.setText(teksUtang.get(position));
		if(preference.toString().equals("") == true){
			img.setImageResource(R.drawable.ic_anonymous);
		} else if(Integer.valueOf(teksPembelian.get(position).toString()) >= Integer.valueOf(preference.toString()) && Integer.valueOf(teksUtang.get(position)) == 0){
			img.setImageResource(R.drawable.ic_anonymous_bonus);
		} else {
			img.setImageResource(R.drawable.ic_anonymous);
		}
		return rowView;
	}

}
