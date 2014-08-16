package id.aditya.andropulsa;

import java.util.List;

import id.aditya.andropulsa.database.DsOperator;
import id.aditya.andropulsa.kelas.ClassOperator;
import id.aditya.andropulsa.kelas.ClassValidation;
import id.aditya.andropulsa.kelas.SpinnerObject;
//import id.aditya.andropulsa.kelas.ClassRegistrasi;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
//import android.support.v4.app.NavUtils;
//import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class DataOperatorActivity extends SherlockActivity {

	private static final int item_id = 1;
	private DsOperator dsoperator;
	private EditText edtKode, edtNominal, edtHrg_Beli, edtHrg_Jual;
	private AutoCompleteTextView edtNmOp2;
	private String nm_preference = "DataPreference";
	private int kodeop, iddistributor;
	private long rowid;
	private ArrayAdapter<String> adapter;
	ArrayAdapter<SpinnerObject> dataAdapter;
	private Spinner spinner1;
	
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_operator);
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_data_storage));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        //edtNmOp = (EditText)findViewById(R.id.edittext_nm_op);
        edtNmOp2 = (AutoCompleteTextView)findViewById(R.id.edittext_nm_op2);
        edtKode = (EditText)findViewById(R.id.edittext_kode_op);
        edtNominal = (EditText)findViewById(R.id.edittext_nominal);
        edtHrg_Beli = (EditText)findViewById(R.id.edittext_hrg_beli);
        edtHrg_Jual = (EditText)findViewById(R.id.edittext_hrg_jual);
        spinner1 = (Spinner)findViewById(R.id.spinner_distributor);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long arg3) {
				// TODO Auto-generated method stub
				//iddistributor = Integer.parseInt ( ( (SpinnerObject) spinner1.getSelectedItem ()).getDtIdNominal());
				Cursor cursor = (Cursor) spinner1.getSelectedItem();  
				rowid = cursor.getLong(cursor.getColumnIndex("_id"));
				iddistributor = (int) rowid;
		        //Toast.makeText(getBaseContext(), String.valueOf(iddistributor).toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        dsoperator = new DsOperator(this);
        dsoperator.open();
        
        //Load auto complete
        int id_user = sp.getInt("id_user", 0);
        List<String> data = dsoperator.fetchOperatorAuto(id_user);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        edtNmOp2.setAdapter(adapter);
        edtNmOp2.setThreshold(1);
        
        loadDataSpinner();
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		Bundle b = getIntent().getExtras();
		ClassOperator Coperator = b.getParcelable("id.aditya.andropulsa.ClassOperator");
		if(Coperator.getStatus().contains("tambah")) {
			menu.add(0, 2, 0, null).setIcon(R.drawable.ic_save).setShowAsAction(item_id);
		} else if(Coperator.getStatus().contains("edit")) {
			menu.add(0, 3, 0,null).setIcon(R.drawable.ic_edit).setShowAsAction(item_id);
			edtNmOp2.setText(Coperator.getNm_op().toString());
			edtKode.setText(Coperator.getKode().toString());
			edtNominal.setText(Coperator.getNominal().toString());
			edtHrg_Beli.setText(Integer.toString(Coperator.getHrg_beli()));
			edtHrg_Jual.setText(Integer.toString(Coperator.getHrg_jual()));
			kodeop = Coperator.getId_op();
			Cursor pos = dsoperator.fetchTemp(Coperator.getId_distributor());
			spinner1.setSelection(pos.getInt(0)-1);
		}
		
		return true;
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, ListOperatorActivity.class));
				DataOperatorActivity.this.finish();
				break;
			case 2:
				try{
					ClassValidation.Is_Valid_NmOp(edtNmOp2);
					ClassValidation.Is_Valid_Kode(edtKode);
					ClassValidation.Is_Valid_Nominal(edtNominal);
					ClassValidation.Is_Valid_hrg_beli(edtHrg_Beli);
					ClassValidation.Is_Valid_hrg_jual(edtHrg_Jual);
					if(ClassValidation.Is_Valid_NmOp(edtNmOp2).isEmpty() 
							|| ClassValidation.Is_Valid_Kode(edtKode).isEmpty() 
							|| ClassValidation.Is_Valid_Nominal(edtNominal).isEmpty()
							|| ClassValidation.Is_Valid_hrg_beli(edtHrg_Beli).toString().isEmpty() 
							|| ClassValidation.Is_Valid_hrg_jual(edtHrg_Jual).toString().isEmpty()) {
						Toast.makeText(getBaseContext(), "Kosong", Toast.LENGTH_LONG).show();
					} else {
						simpan();
					}
				} catch(Exception e) {
					//do nothing
				}
				break;
			case 3:
				update();
				break;
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		NavUtils.navigateUpTo(this, new Intent(this, ListOperatorActivity.class));
		DataOperatorActivity.this.finish();
	}
	
	private void simpan() {
		int id_user = sp.getInt("id_user", 0);
		String nm_op = ClassValidation.Is_Valid_NmOp(edtNmOp2);
		String kode_op = ClassValidation.Is_Valid_Kode(edtKode);
		String nominal = ClassValidation.Is_Valid_Nominal(edtNominal);
		Integer hrg_beli = ClassValidation.Is_Valid_hrg_beli(edtHrg_Beli);
		Integer hrg_jual = ClassValidation.Is_Valid_hrg_jual(edtHrg_Jual);
		dsoperator.insert_tb_data_op(id_user, nm_op, kode_op, nominal, hrg_beli, hrg_jual, iddistributor);
		Toast.makeText(getBaseContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
		bersih();
		//super.onBackPressed();
		back();
	}
	
	private void update() {
		dsoperator.updateOperator(edtNmOp2.getText().toString(), 
								edtKode.getText().toString(), edtNominal.getText().toString(), 
								Integer.parseInt(edtHrg_Beli.getText().toString()), 
								Integer.parseInt(edtHrg_Jual.getText().toString()), kodeop, iddistributor);
		Toast.makeText(getBaseContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
		back();
	}
	
	private void back() {
		NavUtils.navigateUpTo(this, new Intent(this, ListOperatorActivity.class));
		DataOperatorActivity.this.finish();
	}
	
	private void bersih(){
		edtNmOp2.setText("");
		edtKode.setText("");
		edtNominal.setText("");
		edtHrg_Beli.setText("");
		edtHrg_Jual.setText("");
	}
	
	@SuppressWarnings("deprecation")
	private void loadDataSpinner(){
		Cursor cr = dsoperator.fetchdistributor();
		String[] from = new String[]{"nm_distributor"};
		int[] to = new int[]{android.R.id.text1};
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cr, from, to);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(mAdapter);
	}
	
}
