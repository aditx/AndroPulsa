package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
//import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
//import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.SubMenu;

@SuppressLint("InlinedApi")
public class ProfileActivity extends SherlockActivity implements OnClickListener {

	private EditText edtNama, edtUser, edtPass, edtTelp;
	private Button btn_update;
	private Integer id_user, id_agen;
	private String nm_preference = "DataPreference";
	private DsProfile profil;
	private SubMenu mItem;
	private String mode;
	
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_person_white));
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        ab.setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        mode = sp.getString("mode_view", "");
        edtNama = (EditText)findViewById(R.id.edittxt_nama);
        edtUser = (EditText)findViewById(R.id.edittxt_username);
        edtPass = (EditText)findViewById(R.id.edittxt_password);
        edtTelp = (EditText)findViewById(R.id.edittxt_telpon);
        btn_update = (Button)findViewById(R.id.button_update);
        btn_update.setOnClickListener(this);
        disable();
        profil = new DsProfile(this);
        profil.open();
        ambil_data();
	}
	
	@SuppressLint("InlineApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		
		mItem = menu.addSubMenu(0, R.style.Theme_Sherlock, 0, null);
		mItem.setIcon(R.drawable.ic_edit);
		
		mItem.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return true;
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
				ProfileActivity.this.finish();
				profil.close();
			break;
			case R.style.Theme_Sherlock:
				enable();
		    break;
		}
		return true;
	}
	
	private void disable(){
		edtNama.setEnabled(false);
        edtUser.setEnabled(false);
        edtPass.setEnabled(false);
        edtTelp.setEnabled(false);
        btn_update.setEnabled(false);
	}
	
	private void enable(){
		edtNama.setEnabled(true);
        edtUser.setEnabled(true);
        edtPass.setEnabled(true);
        edtTelp.setEnabled(true);
        btn_update.setEnabled(true);
	}
	
	public void reSession(){
		//spe.clear();
		//spe.commit();
		Editor editor = sp.edit();
		editor.putString("nm_agen", edtNama.getText().toString());
		editor.putInt("id_user", id_user);
		editor.commit();
		//spe.putString("nm_user", edtNama.getText().toString());
		//spe.putInt("id_member", id_member);
		//spe.commit();
	}
	
	public void ambil_data(){
		id_user = sp.getInt("id_user", 0);
		Cursor cr = profil.fetchProfile(id_user);
		if(cr.getCount() != 0){
			edtNama.setText(cr.getString(0));
	        edtUser.setText(cr.getString(1));
	        edtPass.setText(cr.getString(2));
	        edtTelp.setText(cr.getString(3));
	       	id_agen = cr.getInt(4);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		profil.updateProfile(edtNama.getText().toString(), 
				edtUser.getText().toString(), 
				edtPass.getText().toString(), 
				edtTelp.getText().toString(), 
				id_agen);
		ambil_data();
		Toast.makeText(getBaseContext(), "Data berhasil di ubah", Toast.LENGTH_SHORT).show();
		disable();
		reSession();
	}
	
	@Override
	public void onBackPressed() {
	    // do nothing.
		NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
		ProfileActivity.this.finish();
	}
	
}
