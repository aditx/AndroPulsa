package id.aditya.andropulsa;

import java.util.List;

import id.aditya.andropulsa.database.DsDistributor;
import id.aditya.andropulsa.database.DsProfile;
import id.aditya.andropulsa.kelas.ClassCekSaldo;
import id.aditya.andropulsa.kelas.ClassKirimSms;
import id.aditya.andropulsa.kelas.ClassSaldo;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.SubMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.InputType;
import android.util.Log;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

@SuppressLint("InlinedApi")
public class HomeActivity extends SherlockActivity implements OnClickListener {
	
	private SubMenu mItem;
	private static final int item_id = 1;
	private TextView txt_nama;
	private EditText edtJumlah;
	private ImageView imgAdd, imgTrans, imgPembeli, imgStatus, imgLaporan, imgBroadcast, imgCekSaldo, imgAbout;
	private String nama_agen;
	private Integer id_user;
	private LinearLayout layout;
	private String nm_preference = "DataPreference";
	private String set_preference = "id.aditya.andropulsa_preferences";
	private RadioButton rB;
	private RadioGroup rG;
	private DsDistributor dsDistributor;
	private DsProfile dsProfile;
	private AlertDialog myalertDialog = null;
	public static Context ctx;
	SharedPreferences sp, sd;
	SharedPreferences.Editor spe;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = this.getSharedPreferences(nm_preference, 0);
        sd = this.getSharedPreferences(set_preference, 0);
        spe = sp.edit();
        ActionBar ab = getSupportActionBar();
        //ab.setLogo(getResources().getDrawable(R.drawable.ic_git));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        
        ctx = this;
        txt_nama = (TextView) findViewById(R.id.txt_nama);
        nama_agen = sp.getString("nm_agen", "");
        id_user = sp.getInt("id_user", 0);
        Log.v("info", "Nama : "+ nama_agen + " Id User : "+ id_user);
        Log.v("info", "Status : "+ sp.getString("valCheck", ""));
        Log.v("info", "Multiple : "+ sp.getInt("valMultiple", 0));
        Log.v("info", "Mode : "+ sp.getString("mode_view", ""));
        Log.v("info", "Id Distributor : "+ sp.getInt("id_distributor", 0));
        txt_nama.setText(nama_agen);
        
        imgAdd = (ImageView)findViewById(R.id.imageView1);
        imgAdd.setOnClickListener(this);
        
        imgTrans = (ImageView)findViewById(R.id.imageView2);
        imgTrans.setOnClickListener(this);
        
        imgPembeli = (ImageView)findViewById(R.id.imageView3);
        imgPembeli.setOnClickListener(this);
        
        imgStatus = (ImageView)findViewById(R.id.imageView4);
        imgStatus.setOnClickListener(this);
        
        imgLaporan = (ImageView)findViewById(R.id.imageView5);
        imgLaporan.setOnClickListener(this);
        
        imgBroadcast = (ImageView)findViewById(R.id.imageView6);
        imgBroadcast.setOnClickListener(this);
        
        imgCekSaldo = (ImageView)findViewById(R.id.imageView7);
        imgCekSaldo.setOnClickListener(this);
        
        imgAbout = (ImageView)findViewById(R.id.imageView8);
        imgAbout.setOnClickListener(this);
        
        dsDistributor = new DsDistributor(this);
        dsDistributor.open();
        
        dsProfile = new DsProfile(this);
        dsProfile.open();
        
	}
	
	public void dialog(Context context, String nomor, String pesan){
		final AlertDialog aBuilder = new AlertDialog.Builder(context).create();
		layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView txtPesan = new TextView(this);
		txtPesan.setText(pesan);
		layout.addView(txtPesan);
		aBuilder.setView(layout);
		aBuilder.setTitle("Cek Saldo Pulsa");
		aBuilder.setIcon(R.drawable.ic_refresh_black);				
		aBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						aBuilder.dismiss();
					}
				});
		aBuilder.show();
	}
	
	@SuppressLint("InlineApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		
		//com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
	    //inflater.inflate(R.menu.inflater, menu);
		
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
				editor.putString("mode_view", "list");
				editor.commit();
				Intent o = new Intent(this, HomeListActivity.class);
				startActivity(o);
				HomeActivity.this.finish();
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
	    // do nothing.
		moveTaskToBack(true);
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
						editor.commit();
						//spe.clear();
						//spe.commit();
						HomeActivity.this.finish();
						//moveTaskToBack(true);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.imageView1:
				Intent a = new Intent(this, ListOperatorActivity.class);
				startActivity(a);
				break;
			case R.id.imageView2:
				Intent b = new Intent(this, TransaksiActivity.class);
				startActivity(b);
				break;
			case R.id.imageView3:
				Intent c = new Intent(this, ListPembeliActivity.class);
				startActivity(c);
				break;
			case R.id.imageView4:
				Intent d = new Intent(this, StatusPembayaranActivity.class);
				startActivity(d);
				break;
			case R.id.imageView5:
				Intent e = new Intent(this, LaporanActivity.class);
				startActivity(e);
				break;
			case R.id.imageView6:
				Intent f = new Intent(this, BroadcastActivity.class);
				startActivity(f);
				break;
			case R.id.imageView7:
				cekSaldo();
				break;
			case R.id.imageView8:
				Deposit();
				break;
		}
	}
	
	private void cekSaldo(){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
		layout = new LinearLayout(HomeActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		rG = new RadioGroup(this);
		final List<ClassSaldo> data = dsDistributor.fetchDataSaldo();
		for(int i=0; i<data.size(); i++){
			rB = new RadioButton(this);
			rB.setId(i);
			rB.setText(data.get(i).getNm_distributor());
			rG.addView(rB);
			rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					String kode = data.get(checkedId).getFormat_saldo().toString();
					String pin = String.valueOf(data.get(checkedId).getPin()).toString();
					String nomor = data.get(checkedId).getSms_center().toString();
					String format = kode + "." + pin;
					if(kode == ""){
						Toast.makeText(getBaseContext(),"Atur format saldo terlebih dahulu", Toast.LENGTH_SHORT).show();
					} else {
						myalertDialog.dismiss();
						spe.putInt("id_distributor", data.get(checkedId).getId());
						spe.commit();
						ClassCekSaldo cek = new ClassCekSaldo(HomeActivity.this, format, nomor);
						cek.execute("");
					}
					
				}
			});
		}
		
		TextView txtKosong = new TextView(this);
		txtKosong.setText("Data distributor masih kosong!");
		txtKosong.setPadding(10, 10, 10, 10);
		txtKosong.setTextSize(17);
		if(data.size() == 0){
			layout.addView(txtKosong);
		} else {
			layout.addView(rG);
		}
		
		aBuilder.setView(layout);
		aBuilder.setTitle("Cek Saldo Pulsa")
				.setIcon(R.drawable.ic_refresh_black)				
				.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		myalertDialog = aBuilder.show();
	}
	
	private void Deposit(){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
		layout = new LinearLayout(HomeActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		rG = new RadioGroup(this);
		final List<ClassSaldo> data = dsDistributor.fetchDataSaldo();
		for(int i=0; i<data.size(); i++){
			rB = new RadioButton(this);
			rB.setId(i);
			rB.setText(data.get(i).getNm_distributor());
			rG.addView(rB);
			rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					String kode = data.get(checkedId).getFormat_saldo().toString();
					String pin = String.valueOf(data.get(checkedId).getPin()).toString();
					String nomor = data.get(checkedId).getSms_center().toString();
					String format = kode + "." + pin;
					int id = data.get(checkedId).getId();
					myalertDialog.dismiss();
					isiDeposit(id);
				}
			});
		}
		
		TextView txtKosong = new TextView(this);
		txtKosong.setText("Data distributor masih kosong!");
		txtKosong.setPadding(10, 10, 10, 10);
		txtKosong.setTextSize(17);
		if(data.size() == 0){
			layout.addView(txtKosong);
		} else {
			layout.addView(rG);
		}
		
		aBuilder.setView(layout);
		aBuilder.setTitle("Isi Deposit Pulsa")
				.setIcon(R.drawable.ic_refresh_black)				
				.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		myalertDialog = aBuilder.show();
	}
	
	private void isiDeposit(final int id){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
		layout = new LinearLayout(HomeActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		edtJumlah = new EditText(this);
		edtJumlah.setPadding(10, 10, 10, 10);
		edtJumlah.setWidth(150);
		edtJumlah.setInputType(InputType.TYPE_CLASS_NUMBER);
		layout.addView(edtJumlah);
		
		aBuilder.setView(layout);
		aBuilder.setTitle("Masukkan jumlah deposit")
				.setIcon(R.drawable.ic_refresh_black)	
				.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String kondisi = edtJumlah.getText().toString();
						if(kondisi.matches("")){
							Toast.makeText(getBaseContext(),"Masukkan nominal terlebih dahulu !!", Toast.LENGTH_SHORT).show();
						} else {
							Cursor cr = dsDistributor.fetchDistributor(id);
							Cursor crz = dsProfile.fetchDataAgen();
							String pesan = "Saya telah melakukan pembayaran deposit atas nama " + crz.getString(1).toString() + " " +
										   "dengan jumlah pembayaran Rp."+ edtJumlah.getText().toString() +" " +
										   "mohon segera diproses. Terimakasih";
							ClassKirimSms cs = new ClassKirimSms();
							cs.kirimSMS(HomeActivity.this, cr.getString(3).toString(), pesan.toString(), null, 0);
							Toast.makeText(getBaseContext(),"Mohon tunggu ...", Toast.LENGTH_SHORT).show();
						}
						
					}
				})
				.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		myalertDialog = aBuilder.show();
	}
	
}