package id.aditya.andropulsa;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import id.aditya.andropulsa.database.DsReset;
import id.aditya.andropulsa.kelas.ClassKirimSms;
import id.aditya.andropulsa.kelas.ClassValidation;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class ResetPasswordActivity extends SherlockActivity implements OnClickListener {
	
	private EditText edReset;
	private Button btnReset;
	private DsReset dsReset;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_reset));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edReset = (EditText)findViewById(R.id.edittext_reset);
        btnReset = (Button)findViewById(R.id.btn_reset_pass);
        btnReset.setOnClickListener(this);
        dsReset = new DsReset(this);
        dsReset.open();
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
		try {
			ClassValidation.Is_Valid_username(edReset);
			if(ClassValidation.Is_Valid_username(edReset).isEmpty()){
				//do nothing
			} else {
				String username = ClassValidation.Is_Valid_username(edReset);
				Cursor cr = dsReset.fetchReset(username);
				if(cr.getCount()!=0){
					String password = cr.getString(1).toString();
					String pesan = "Automatic Reset :\nUsername : " + username + "\nPassword : " + password + "" ;
					ClassKirimSms cs = new ClassKirimSms();
					cs.kirimSMS(ResetPasswordActivity.this, cr.getString(2).toString(), pesan.toString(), null, 0);
				} else {
					Toast.makeText(getBaseContext(), "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
					edReset.setText("");
				}
			}
		} catch(Exception e) {}
	}
	
}