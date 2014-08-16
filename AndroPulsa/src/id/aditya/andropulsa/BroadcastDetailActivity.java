package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsBroadcast;
import id.aditya.andropulsa.kelas.ClassKontak;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class BroadcastDetailActivity extends SherlockActivity implements OnNavigationListener {
	
	private DsBroadcast dsBroadcast;
	private ListView listKontak;
	private ListKontak adapter1;
	private ArrayList<ClassKontak> dataKontak = new ArrayList<ClassKontak>();
	public ArrayList<String> dataNomorAct = new ArrayList<String>();
	private String data_preference = "id.aditya.andropulsa_preferences";
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	ActionBar ab;
	ArrayAdapter<String> adb;
	ArrayAdapter<String> adapter;
	Cursor cr;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_detail);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        ab.setDisplayHomeAsUpEnabled(true);
        sp = this.getSharedPreferences(data_preference, 0);
        spe = sp.edit();
        
        listKontak = (ListView)findViewById(R.id.listdaftarkontak);
        
        ArrayList<String> itemMenu = new ArrayList<String>();
        itemMenu.add(0, "Belum Lunas");
        itemMenu.add(1, "Dapat Bonus");
        adb = new ArrayAdapter<String>(ab.getThemedContext(), android.R.layout.simple_list_item_1, android.R.id.text1, itemMenu);
        
        ab.setListNavigationCallbacks(adb, this);
        
        dsBroadcast = new DsBroadcast(this);
        dsBroadcast.open();
        
        tampilDataPembeli();
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				BroadcastDetailActivity.this.finish();
				break;
			case 2:
				dataNomorAct = adapter1.dataNomor;
				if(dataNomorAct.size() == 0){
					Toast.makeText(this, "Pilih data pembeli !", Toast.LENGTH_SHORT).show();
				} else {
					Intent intent = new Intent(this, BroadcastActivity.class);
					intent.putStringArrayListExtra("datanomor", dataNomorAct);
					NavUtils.navigateUpTo(this, intent);
					BroadcastDetailActivity.this.finish();
				}
				break;
		}
		return true;
	}
	
	@SuppressLint("InlinedApi")
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		menu.add(0, 2, 0, null).setIcon(R.drawable.ic_accept).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		if(itemPosition == 0){
			refreshData();
			//txtUtang.setTextColor(Color.parseColor("#8B0000"));
			tampilDataPembeli();
		} else {
			refreshData();
			//txtTotal.setTextColor(Color.parseColor("#006400"));
			tampilDataPembeli();
		}
		return false;
	}
	
	private void refreshData(){
		ListKontak adapter1 = new ListKontak(this,R.layout.list_kontak, dataKontak);
		adapter1.clear();
		listKontak.setAdapter(adapter1);
	}
	
	public void tampilDataPembeli(){
		if(ab.getSelectedNavigationIndex() == 0){
			cr = dsBroadcast.fetchDataBelumLunas();
		} else {
			String pref = sp.getString("prefBonus", "");
			cr = dsBroadcast.fetchDataLunas(Integer.valueOf(pref.toString()));
		}
		
		if(cr != null){
			if(cr.moveToFirst()){
				do {
					int id = cr.getInt(0);
					String nama = cr.getString(1);
					int total = cr.getInt(3);
					int utang = cr.getInt(5);
					String notelp = cr.getString(2);
					dataKontak.add(new ClassKontak(nama, id, total, utang, notelp));
				} while(cr.moveToNext());
			}
		}
		adapter1 = new ListKontak(this,R.layout.list_kontak, dataKontak);
		listKontak.setTextFilterEnabled(true);
		listKontak.setAdapter(adapter1);
	}

}
