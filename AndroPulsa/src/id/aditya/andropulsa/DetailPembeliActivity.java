package id.aditya.andropulsa;

import java.util.List;

import id.aditya.andropulsa.database.DsPembeli;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class DetailPembeliActivity extends SherlockActivity {
	
	private TextView txtNama, txtAlamat, txtJudul, txtNo, txtTot, txtUtang;
	private DsPembeli dspembeli;
	private ImageView imgAnonym;
	private String id;
	private ListView dataNoList;
	private String data_preference = "id.aditya.andropulsa_preferences";
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_social_group));
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        sp = this.getSharedPreferences(data_preference, 0);
        spe = sp.edit();
        
        imgAnonym = (ImageView)findViewById(R.id.imgAnonymDetail);
        txtNama = (TextView)findViewById(R.id.txt_nama_pembeli);
        txtAlamat = (TextView)findViewById(R.id.txt_almt_pembeli);
        txtJudul = (TextView)findViewById(R.id.txt_data_pembeli);
        txtNo = (TextView)findViewById(R.id.txt_data_nomor_pembeli);
        txtTot = (TextView)findViewById(R.id.txt_total_pembelian);
        txtUtang = (TextView)findViewById(R.id.txt_total_piutang);
        dataNoList = (ListView)findViewById(R.id.list_nomor);
        txtJudul.setPaintFlags(txtJudul.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtJudul.setText("DATA PEMBELI");
        txtNo.setPaintFlags(txtNo.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtNo.setText("DATA NOMOR");
        txtTot.setText(String.valueOf(getIntent().getExtras().getString("tot")).substring(18));
        txtUtang.setText("Rp."+String.valueOf(getIntent().getExtras().getString("utang")));
        
        int total, utang;
        total = Integer.parseInt(String.valueOf(getIntent().getExtras().getString("tot")).substring(21));
        utang = Integer.parseInt(String.valueOf(getIntent().getExtras().getString("utang")));
        String pref = sp.getString("prefBonus", "");
        if(total != 0 && utang == 0 && total >= Integer.valueOf(pref.toString())){
        	txtTot.setTextColor(Color.parseColor("#006400"));
        	imgAnonym.setImageResource(R.drawable.ic_anonymous_bonus);
        } else if(total != 0 && utang == 0) {
        	imgAnonym.setImageResource(R.drawable.ic_anonymous);
        } else if(total != 0 && utang != 0) {
        	imgAnonym.setImageResource(R.drawable.ic_anonymous);
        	txtUtang.setTextColor(Color.parseColor("#8B0000"));
        } else if(total == 0 && utang == 0) {
        	imgAnonym.setImageResource(R.drawable.ic_anonymous);
        }
        
        id = getIntent().getExtras().getString("id_");
        dspembeli = new DsPembeli(this);
        dspembeli.open();
        loadData();
        loadDataNomor();
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, ListPembeliActivity.class));
				DetailPembeliActivity.this.finish();
				break;
		}
		return true;
	}
	
	public void loadData(){
		Cursor cr = dspembeli.fetchPembeliId(Integer.parseInt(id));
		if(cr.getCount() != 0){
			txtNama.setText(cr.getString(1).toString());
			txtAlamat.setText(cr.getString(2).toString());
		}
	}
	
	public void loadDataNomor(){
		List<String> cr = dspembeli.fetchNomPembeli(Integer.parseInt(id));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cr);
		dataNoList.setAdapter(adapter);
	}
	
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpTo(this, new Intent(this, ListPembeliActivity.class));
		DetailPembeliActivity.this.finish();
	}
}
