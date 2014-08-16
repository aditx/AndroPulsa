package id.aditya.andropulsa;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.SubMenu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class HomeListActivity extends SherlockActivity {
	ListView list;
	String[] teks = {
		"Data Operator",
		"Transaksi Penjualan",
		"Data Pembeli",
		"Status Pembayaran",
		"Laporan Penjualan",
		"Broadcast Pesan",
		"Cek Saldo Pulsa"
	};
	
	String[] teks2 = {
		"Kumpulan data operator yang digunakan untuk database harga",
		"Melakukan transaksi penjualan dengan sistem yang cepat",
		"Melihat data pembeli dari agen penjual pulsa",
		"Melihat status pembayaran berdasarkan status pembayaran",
		"Melihat laporan penjualan dan grafik pendapatan",
		"Lakukan broadcast pesan ke semua pelanggan dengan cepat",
		"Melihat kapasitas saldo yang ada"
	};
	
	Integer[] imageId = {
		R.drawable.ic_data_op,
		R.drawable.ic_trans,
		R.drawable.ic_user,
		R.drawable.ic_status,
		R.drawable.ic_laporan,
		R.drawable.ic_broadcast,
		R.drawable.ic_saldo
	};
	
	private SubMenu mItem;
	private static final int item_id = 1;
	private String nm_preference = "DataPreference";
	private String nama_agen;
	private Integer id_user, id_distributor;
	private TextView txt_nama;
	
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_list);
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        ActionBar ab = getSupportActionBar();
        //ab.setLogo(getResources().getDrawable(R.drawable.ic_git));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        ListHome adapter = new ListHome(HomeListActivity.this, teks, teks2, imageId);
        list = (ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				String value = list.getItemAtPosition(position).toString();
				if(value.equals("Data Operator")){
					Intent a = new Intent(HomeListActivity.this, ListOperatorActivity.class);
					startActivity(a);
				} else if(value.equals("Transaksi Penjualan")){
					Intent a = new Intent(HomeListActivity.this, TransaksiActivity.class);
					startActivity(a);
				}
			}
        	
		});
        
        nama_agen = sp.getString("nm_agen", "");
        id_user = sp.getInt("id_user", 0);
        id_distributor = sp.getInt("id_distributor", 0);
        Log.v("info", "Nama : "+ nama_agen + " Id User : "+ id_user);
        Log.v("info", "Id Distributor : "+ id_distributor);
        txt_nama.setText(nama_agen);
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
	    inflater.inflate(R.menu.inflater_grid, menu);
		
		mItem = menu.addSubMenu(0, item_id, 0, null).setIcon(R.drawable.ic_action_overflow);
		mItem.add(0, 2, 0, "Lihat Profil").setIcon(R.drawable.ic_person_white);
		mItem.add(0, 3, 0, "Pengaturan").setIcon(R.drawable.ic_settings);
		mItem.add(0, 4, 0, "Keluar").setIcon(R.drawable.ic_logout);
		
		mItem.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case 4:
				dialogBox();
				break;
			case 2:
				Intent i = new Intent(this, ProfileActivity.class);
				startActivity(i);
				break;
			case 3:
				Intent n = new Intent(this, PengaturanActivity.class);
				startActivity(n);
				break;
			case R.id.menu_item:
				Editor editor = sp.edit();
				editor.putString("mode_view", "grid");
				editor.commit();
				Intent o = new Intent(this, HomeActivity.class);
				startActivity(o);
				HomeListActivity.this.finish();
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;
		}
		return true;
	}
	
	private void dialogBox(){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
		aBuilder.setMessage("Apakah ingin keluar dari aplikasi ?")
				.setCancelable(false)
				.setTitle("Keluar Aplikasi")
				.setIcon(R.drawable.ic_warning)
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Editor editor = sp.edit();
						editor.remove("nama_agen");
						editor.remove("id_user");
						editor.remove("id_distributor");
						editor.commit();
						HomeListActivity.this.finish();
					}
				})
				
				.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				}).show();
	}
	
	@Override
	public void onBackPressed() {
	    // do nothing.
		moveTaskToBack(true);
	}
	
}
