package id.aditya.andropulsa;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import id.aditya.andropulsa.database.DsRegistrasi;
import id.aditya.andropulsa.kelas.ClassRegistrasi;
import id.aditya.andropulsa.kelas.ClassValidation;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class RegistrasiActivity extends SherlockActivity implements OnClickListener {
	
	private EditText edNama, edUsername, edPassword, edTelpon;
	private Button btnDaftar;
	private DsRegistrasi dataRegistrasi;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_add_person));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edNama = (EditText) findViewById(R.id.edittext_nama);
        edUsername = (EditText) findViewById(R.id.edittext_username);
        edPassword = (EditText) findViewById(R.id.edittext_password);
        edTelpon = (EditText) findViewById(R.id.edittext_telpon);
        btnDaftar = (Button) findViewById(R.id.button_daftar);
        btnDaftar.setOnClickListener(this);
        
        dataRegistrasi = new DsRegistrasi(this);
        dataRegistrasi.open();
    }
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
		}
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
			case R.id.button_daftar:
				
				try {
					ClassValidation.Is_Valid_nama(edNama);
					ClassValidation.Is_Valid_username(edUsername);
					ClassValidation.Is_Valid_password(edPassword);
					ClassValidation.Is_Valid_telpon(edTelpon);
					if(ClassValidation.Is_Valid_nama(edNama).isEmpty() || 
							ClassValidation.Is_Valid_username(edUsername).isEmpty() || 
							ClassValidation.Is_Valid_password(edPassword).isEmpty() || 
							ClassValidation.Is_Valid_telpon(edTelpon).isEmpty()){
						Toast.makeText(getBaseContext(), "Kosong", Toast.LENGTH_LONG).show();
					} else {
						registrasi();
					}
				} catch(Exception e){
					//do nothing
				}
				
			break;
		}
	}
	
	private void registrasi(){
		String nama = null;
		String username = null;
		String password = null;
		String telpon = null;
		ClassRegistrasi registrasi = null;
		
		nama = ClassValidation.Is_Valid_nama(edNama);
		username = ClassValidation.Is_Valid_username(edUsername);
		password = ClassValidation.Is_Valid_password(edPassword);
		telpon = ClassValidation.Is_Valid_telpon(edTelpon);
		
		registrasi = dataRegistrasi.insert_tb_agen(nama, username, password, telpon);
		int idagen = registrasi.getId_agen();
		//registrasi = dataRegistrasi.insert_tb_user(idagen);
		
		Toast.makeText(getBaseContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
		
		//Clear data
		bersih();
		
		//Back to activity before
		finish();
	}
	
	private void bersih(){
		edNama.setText("");
		edUsername.setText("");
		edPassword.setText("");
		edTelpon.setText("");
	}
	
}
