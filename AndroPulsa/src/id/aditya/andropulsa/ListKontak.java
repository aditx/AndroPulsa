package id.aditya.andropulsa;

import id.aditya.andropulsa.kelas.ClassKontak;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ListKontak extends ArrayAdapter<ClassKontak> {
	
	private final Activity context;	
	private ArrayList<ClassKontak> objek;
	public ArrayList<String> dataNomor = new ArrayList<String>();
	private CheckBox cb;
	
	public ListKontak(Activity context, int listKontak, ArrayList<ClassKontak> dataKontak) {
		// TODO Auto-generated constructor stub
		super(context, listKontak, dataKontak);
		this.objek = dataKontak;
		this.context = context;
	}

	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_kontak, null, true);
		final ClassKontak data = objek.get(position);
		
		if(data != null){
			TextView teks1 = (TextView)rowView.findViewById(R.id.txtKontakPembeli);
			TextView teks2 = (TextView)rowView.findViewById(R.id.txtIdPembeli);
			TextView teks3 = (TextView)rowView.findViewById(R.id.txtTotalPembeli);
			TextView teks4 = (TextView)rowView.findViewById(R.id.txtUtangPembeli);
			ImageView img = (ImageView)rowView.findViewById(R.id.imgPembeli);
			teks1.setText(data.getDataNama());
			teks2.setText(String.valueOf(data.getDataId()));
			teks3.setText("Total Pembelian : Rp."+data.getDataTot());
			teks4.setText("Piutang                  : Rp."+data.getDataUtang());
			img.setImageResource(R.drawable.ic_anonymous);
		}
		
		cb = (CheckBox)rowView.findViewById(R.id.cb_pilih);
		cb.setId(data.getDataId());
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					dataNomor.add(data.getDataTelp().toString());
				} else if(!isChecked){
					dataNomor.remove(data.getDataTelp().toString());
				}
			}
			
		});
		
		return rowView;
	}

}
