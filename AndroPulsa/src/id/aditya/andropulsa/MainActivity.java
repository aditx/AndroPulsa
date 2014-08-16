package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsLogin;
import id.aditya.andropulsa.kelas.ClassValidation;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

public class MainActivity extends Activity {
	
	private TextView txt_daftar, txt_reset;
	private Button btn_login;
	private EditText edUsername, edPassword;
	private DsLogin dslogin;
	private String nm_preference = "DataPreference";
	
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/MING____.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/BELLGOTHICSTD-BOLD.OTF");
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        Log.v("info", "Mode : "+ sp.getString("mode_view", ""));
        
        if(sp.contains("nm_agen") && sp.contains("id_user")){
        	Intent i = new Intent(MainActivity.this, HomeActivity.class);
    		startActivity(i);        	
        }
        
        edUsername = (EditText)findViewById(R.id.edittext_username);
        edPassword = (EditText)findViewById(R.id.edittext_password);
        
        txt_daftar = (TextView)findViewById(R.id.textView_daftar);
        txt_daftar.setTypeface(font2);
        txt_daftar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, RegistrasiActivity.class);
				startActivity(i);
			}
		});
        
        txt_reset = (TextView)findViewById(R.id.textView_reset);
        txt_reset.setTypeface(font2);
        txt_reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, ResetPasswordActivity.class);
				startActivity(i);
			}
		});
        
        btn_login = (Button)findViewById(R.id.button_login);
        btn_login.setTypeface(font);
        btn_login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
					ClassValidation.Is_Valid_username(edUsername);
					ClassValidation.Is_Valid_password(edPassword);
					if(ClassValidation.Is_Valid_username(edUsername).isEmpty() || 
							ClassValidation.Is_Valid_password(edPassword).isEmpty()){
						Toast.makeText(getBaseContext(), "Kosong", Toast.LENGTH_LONG).show();
					} else {
						login();
					}
				} catch(Exception e){
					//do nothing
				}
			}
		});
        
        dslogin = new DsLogin(this);
        dslogin.open();
    }
    
    private void login(){
    	String username = ClassValidation.Is_Valid_username(edUsername);
		String password = ClassValidation.Is_Valid_password(edPassword);
		Cursor cr = dslogin.fetchLogin(username, password);
		
		if(cr.getCount() != 0){
			String nm_agen = cr.getString(2);
			Integer id_user = cr.getInt(3);
			spe.putString("nm_agen", nm_agen);
			spe.putInt("id_user", id_user);
			spe.commit();
			if(sp.getString("mode_view", "").equals("grid")){
        		Intent i = new Intent(MainActivity.this, HomeActivity.class);
    			startActivity(i);
        	} else if(sp.getString("mode_view", "").equals("list")){
        		Intent i = new Intent(MainActivity.this, HomeListActivity.class);
    			startActivity(i);
        	} else {
        		Intent i = new Intent(MainActivity.this, HomeActivity.class);
    			startActivity(i);
        	}
			bersih();
		} else {
			Toast.makeText(getBaseContext(), "Gagal Login", Toast.LENGTH_SHORT).show();
			bersih();
		}
    }
    
    public void bersih(){
    	edUsername.setText("");
    	edPassword.setText("");
    	edUsername.requestFocus();
    }
    
}
