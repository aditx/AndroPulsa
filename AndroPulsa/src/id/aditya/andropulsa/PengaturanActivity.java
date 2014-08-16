package id.aditya.andropulsa;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class PengaturanActivity extends SherlockPreferenceActivity implements OnPreferenceChangeListener {
	
	private String nm_preference = "DataPreference";
	private String status;
	private int multiple;
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pengaturan);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_settings));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
        	this.addPreferencesFromResource(R.xml.pengaturan);
        } else if (Build.VERSION.SDK_INT>Build.VERSION_CODES.HONEYCOMB) {
        	this.addPreferencesFromResource(R.xml.pengaturan);
        }
        
        Preference prefBonus = (Preference) findPreference("prefBonus");
        String pref = prefBonus.getSharedPreferences().getString("prefBonus", "");
		spe.putString("bonus", pref);
		spe.commit();
        
        Preference prefMultiple = (Preference) findPreference("prefSyncMultiple");
        prefMultiple.setOnPreferenceChangeListener(this);
        
        Preference prefCheckbox = (Preference) findPreference("prefCheckBox");
        prefCheckbox.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				// TODO Auto-generated method stub
				if(newValue.toString().equals("true")){
					status = "Aktif";
				} else {
					status = "Mati";
					multiple = 1;
				}
				spe.putString("valCheck", status);
				spe.putInt("valMultiple", multiple);
				spe.commit();
				return true;
			}
		});
        
        onChangeShared();
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				//NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
				PengaturanActivity.this.finish();
			break;
		}
		return true;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		if(newValue instanceof String){
			String set = (String)newValue;
			if(set.equals("2")){
				spe.putInt("valMultiple", 2);
				preference.setSummary("2x Transaksi");
			} else if(set.equals("3")){
				spe.putInt("valMultiple", 3);
				preference.setSummary("3x Transaksi");
			}
			spe.commit();
		}
		return true;
	}
	
	@SuppressWarnings("deprecation")
	private void onChangeShared(){
		int val = sp.getInt("valMultiple", 0);
		Preference prefMultiple = (Preference) findPreference("prefSyncMultiple");
		if(val == 2){
	        prefMultiple.setSummary("2x Transaksi");
		} else if(val == 3){
	        prefMultiple.setSummary("3x Transaksi");
		}
	}

}
