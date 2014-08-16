package id.aditya.andropulsa;

import id.aditya.andropulsa.kelas.ClassBroadcast;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

@SuppressLint("NewApi")
public class BroadcastActivity extends SherlockActivity implements OnClickListener {
	
	private EditText edtTujuan, edtPesan;
	private ImageView imgAdd, imgKirim;
	ActionBar ab;
	ArrayAdapter<String> adb;
	ArrayList<String> dataNomor = new ArrayList<String>();
	Drawable icon;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        ab.setDisplayHomeAsUpEnabled(true);
        edtTujuan = (EditText)findViewById(R.id.edtNo);
        edtPesan = (EditText)findViewById(R.id.edtPesan);
        if(getIntent().getExtras() != null) {
        	Intent intent = getIntent();
            ArrayList<String> dataNomor = intent.getStringArrayListExtra("datanomor");
            this.dataNomor = dataNomor;
            if(dataNomor.size() != 0){
            	StringBuilder builder = new StringBuilder();
            	String separator = ", ";
            	int total = dataNomor.size() * separator.length();
        		for(String s : dataNomor){
        			total += s.length();
        		}
            	for(String nilai : dataNomor){
            		builder.append(separator).append(nilai);
            	}
            	String hasil = builder.substring(separator.length());
            	edtTujuan.setText(hasil.toString());
            } else {
            	edtTujuan.setText("0");
            }
        }
        
        imgKirim = (ImageView)findViewById(R.id.imageKirim);
        imgKirim.setOnClickListener(this);
        icon = getResources().getDrawable(R.drawable.ic_send_now_black);
        if(edtTujuan.getText().toString().length() <= 0){
        	icon.setAlpha(90);
        	imgKirim.setImageDrawable(icon);
        	imgKirim.setEnabled(false);
        } else {
        	icon.setAlpha(255);
        	imgKirim.setImageDrawable(icon);
        }
        imgAdd = (ImageView)findViewById(R.id.imageAddNumber);
        imgAdd.setOnClickListener(this);
        
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				BroadcastActivity.this.finish();
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.imageKirim:
				ClassBroadcast cb = new ClassBroadcast(BroadcastActivity.this, edtPesan.getText().toString(), dataNomor);
            	cb.execute("");
				break;
			case R.id.imageAddNumber:
				Intent a = new Intent(BroadcastActivity.this, BroadcastDetailActivity.class);
				startActivity(a);
				break;
		}
	}

}
