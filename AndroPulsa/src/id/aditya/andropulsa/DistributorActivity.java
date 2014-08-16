package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsDistributor;
import id.aditya.andropulsa.kelas.ClassDistributor;
import id.aditya.andropulsa.kelas.ClassValidation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

@SuppressLint("InlinedApi")
public class DistributorActivity extends SherlockActivity implements OnClickListener {

	private Integer id_distributor, Vpin;
	private DsDistributor distributor;
	private String nm_preference = "DataPreference";
	private EditText edtNmDistributor, edtAlamat, edtTelp, edtSmsCenter, edtPin;
	private Button btn_update, btn_add;
	private Bundle b;
	private ClassDistributor Cdistributor;
	
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_settings));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        edtNmDistributor = (EditText)findViewById(R.id.edittxt_nama_distributor);
        edtAlamat = (EditText)findViewById(R.id.edittxt_almt_distributor);
        edtTelp = (EditText)findViewById(R.id.edittxt_telpon_distributor);
        edtSmsCenter = (EditText)findViewById(R.id.edittxt_sms_center);
        edtPin = (EditText)findViewById(R.id.edittxt_pin_distributor);
        
        b = getIntent().getExtras();
		Cdistributor = b.getParcelable("id.aditya.andropulsa.ClassDistributor");
		if(Cdistributor.getStatus().contains("tambah")) {
			ab.setTitle("Tambah Distributor");
			btn_add = (Button)findViewById(R.id.button_add_distributor);
			btn_add.setVisibility(View.VISIBLE);
	        btn_add.setOnClickListener(this);
		} else {
			ab.setTitle("Edit Distributor");
			//disable();
			btn_update = (Button)findViewById(R.id.button_update_distributor);
			btn_update.setVisibility(View.VISIBLE);
	        btn_update.setOnClickListener(this);
		}
        distributor = new DsDistributor(this);
        distributor.open();       
        
	}
	
	@SuppressLint("InlineApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		if(Cdistributor.getStatus().contains("edit")) {
			edtNmDistributor.setText(Cdistributor.getNm_distributor().toString());
			edtAlamat.setText(Cdistributor.getAlamat().toString());
			edtTelp.setText(Cdistributor.getTelp().toString());
			edtSmsCenter.setText(Cdistributor.getSms().toString());
			edtPin.setText(String.valueOf(Cdistributor.getPin()).toString());
			id_distributor = Cdistributor.getId_distributor();
		}
		return true;
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, DetailDistributorActivity.class));
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button_add_distributor:
				try{
					ClassValidation.Is_Valid_nama(edtNmDistributor);
					ClassValidation.Is_Valid_alamat(edtAlamat);
					ClassValidation.Is_Valid_telpon(edtTelp);
					ClassValidation.Is_Valid_no(edtSmsCenter);
					ClassValidation.Is_Valid_pin(edtPin);
					if(ClassValidation.Is_Valid_nama(edtNmDistributor).isEmpty() || 
							ClassValidation.Is_Valid_alamat(edtAlamat).isEmpty() ||
							ClassValidation.Is_Valid_telpon(edtTelp).isEmpty() ||
							ClassValidation.Is_Valid_no(edtSmsCenter).isEmpty() ||
							ClassValidation.Is_Valid_pin(edtPin).intValue()==0){
						Toast.makeText(getBaseContext(), "Kosong", Toast.LENGTH_LONG).show();
					} else {
						long cr = distributor.insert_tb_distributor_new(ClassValidation.Is_Valid_nama(edtNmDistributor), 
								ClassValidation.Is_Valid_alamat(edtAlamat), ClassValidation.Is_Valid_telpon(edtTelp), 
								ClassValidation.Is_Valid_no(edtSmsCenter), ClassValidation.Is_Valid_pin(edtPin));
						if(cr != 0){
							Toast.makeText(getBaseContext(), "Data ditambahkan", Toast.LENGTH_SHORT).show();
							NavUtils.navigateUpTo(this, new Intent(this, DetailDistributorActivity.class));
							DistributorActivity.this.finish();
						}
					}
				} catch(Exception e){
					//do something
				}
				break;
			case R.id.button_update_distributor:
				Vpin = Integer.parseInt(edtPin.getText().toString());
				distributor.updateDistributor(edtNmDistributor.getText().toString(), 
								edtAlamat.getText().toString(), 
								edtTelp.getText().toString(), 
								edtSmsCenter.getText().toString(), 
								id_distributor, Vpin);
				Toast.makeText(getBaseContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
				break;
		}
		
	}
	
}
