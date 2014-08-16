package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsStatus;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.app.SherlockActivity;

public class StatusPembayaranActivity extends SherlockActivity {
	
	private Spinner spinner1;
	private TextView txtTrans, txtNominal;
	private DsStatus dsStatus;
	private Cursor cr;
	private TableLayout tl, th;
	private ImageView imgDel;
	private int _id;
	private String nama;
	ArrayList<String> data = new ArrayList<String>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pembayaran);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        txtTrans = (TextView)findViewById(R.id.textSts1);
        txtTrans.setTextColor(Color.LTGRAY);
        txtTrans.setText("    Data Kosong");
        txtNominal = (TextView)findViewById(R.id.textSts2);
        txtNominal.setTextColor(Color.LTGRAY);
        txtNominal.setText("    Data Kosong");
        tl = (TableLayout)findViewById(R.id.status_table);
        th = (TableLayout)findViewById(R.id.head_table);
        imgDel = (ImageView)findViewById(R.id.imgDelStats);
        imgDel.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(_id != 0){
					hapusStatus();
				} else {
					Toast.makeText(getBaseContext(), "Pilih data terlebih dahulu", Toast.LENGTH_SHORT).show();
				}
				
			}
        	
        });
        
        spinner1 = (Spinner)findViewById(R.id.spinner_status);
        data.add("Belum Lunas");
        data.add("Lunas");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,android.R.id.text1,data); 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        
        dsStatus = new DsStatus(this);
        dsStatus.open();
        
        headerTable();
        displayTable();
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				StatusPembayaranActivity.this.finish();
				break;
			case 3:
				if(_id != 0){
					updateStatus();
				} else {
					Toast.makeText(getBaseContext(), "Pilih data terlebih dahulu", Toast.LENGTH_SHORT).show();
				}
				
				break;
		}
		return true;
	}
	
	@SuppressLint("InlinedApi")
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		menu.add(0, 3, 0, null).setIcon(R.drawable.ic_refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}
	
	private void hapusStatus(){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(StatusPembayaranActivity.this);
		aBuilder.setMessage("Hapus transaksi " + nama + " ?")
				.setCancelable(false)
				.setTitle("Hapus Status")
				.setIcon(R.drawable.ic_warning)
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dsStatus.deleteStatus(_id);
						tl.removeAllViews();
						displayTable();
						txtTrans.setTextColor(Color.LTGRAY);
						txtTrans.setText("    Data Kosong");
						txtNominal.setTextColor(Color.LTGRAY);
						txtNominal.setText("    Data Kosong");
						_id = 0;
						nama = null;
						spinner1.setSelection(0);
						Toast.makeText(getBaseContext(), "Data berhasil di hapus", Toast.LENGTH_SHORT).show();
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
	
	private void updateStatus(){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(StatusPembayaranActivity.this);
		aBuilder.setMessage("Ubah status " + nama + " menjadi "+ spinner1.getSelectedItem() +" ?")
				.setCancelable(false)
				.setTitle("Ubah Status")
				.setIcon(R.drawable.ic_refresh_black)
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Cursor cr = dsStatus.updateStatus((int)spinner1.getSelectedItemId()+1, _id);
						if(cr != null){
							tl.removeAllViews();
							displayTable();
							Toast.makeText(getBaseContext(), "Status berhasil dirubah", Toast.LENGTH_SHORT).show();
						}
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
	
	private void headerTable(){
		TableRow tr_head = new TableRow(this);
		tr_head.setBackgroundResource(R.drawable.layout_table);
		//tr_head.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0E0E6")));
		
		TextView thead_nominal = new TextView(this);
		thead_nominal.setId(10);
		thead_nominal.setText("Pembeli");
		thead_nominal.setPadding(10, 10, 10, 10);
		thead_nominal.setTextColor(Color.BLACK);
		tr_head.addView(thead_nominal);
		
		TextView thead_tujuan = new TextView(this);
		thead_tujuan.setId(20);
		thead_tujuan.setText("Tujuan");
		thead_tujuan.setPadding(10, 10, 10, 10);
		thead_tujuan.setTextColor(Color.BLACK);
		tr_head.addView(thead_tujuan);
		
		TextView thead_tanggal = new TextView(this);
		thead_tanggal.setId(30);
		thead_tanggal.setText("Tanggal");
		thead_tanggal.setPadding(10, 10, 10, 10);
		thead_tanggal.setTextColor(Color.BLACK);
		tr_head.addView(thead_tanggal);
		
		TextView thead_aksi = new TextView(this);
		thead_aksi.setId(40);
		thead_aksi.setText("Aksi");
		thead_aksi.setPadding(10, 10, 10, 10);
		thead_aksi.setTextColor(Color.BLACK);
		tr_head.addView(thead_aksi);
		
		th.addView(tr_head, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	@SuppressWarnings("deprecation")
	private void displayTable(){
		
		cr = dsStatus.fetchStatus();
		
		int count = 0;
		while(cr.moveToNext()){
			final String hrg_jual = cr.getString(0);
			final String no_tujuan = cr.getString(1);
			final String tanggal = cr.getString(2);
			final String id_transaksi = cr.getString(3);
			final Integer status = cr.getInt(4);
			final String nm_pembeli = cr.getString(5);
			final Integer id = cr.getInt(6);
			
			String tgl = tanggal.substring(8, 10);
			String bln = tanggal.substring(5, 7);
			String thn = tanggal.substring(2, 4);
			
			//Create table row
			TableRow tr = new TableRow(this);
			//if(count%2!=0) tr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0E0E6")));
			tr.setId(100+count);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
					
			final TextView txtnm_pembeli = new TextView(this);
			txtnm_pembeli.setId(200+count);
			if(status == 2){
				txtnm_pembeli.setTextColor(Color.parseColor("#006400"));
			} else {
				txtnm_pembeli.setTextColor(Color.BLACK);
			}
			txtnm_pembeli.setText(nm_pembeli.toString());
			txtnm_pembeli.setPadding(10, 15, 10, 10);
			tr.addView(txtnm_pembeli);
			
			TextView txt_tujuan = new TextView(this);
			txt_tujuan.setId(300+count);
			txt_tujuan.setText(no_tujuan.toString().substring(0, 6)+"xxx");
			txt_tujuan.setPadding(10, 15, 10, 10);
			txt_tujuan.setTextColor(Color.BLACK);
			tr.addView(txt_tujuan);
			
			TextView txthrg_tanggal = new TextView(this);
			txthrg_tanggal.setId(400+count);
			txthrg_tanggal.setText(tgl+"/"+bln+"/"+thn);
			txthrg_tanggal.setTextColor(Color.BLACK);
			tr.addView(txthrg_tanggal);
			
			ImageView imgEdit = new ImageView(this);
			imgEdit.setId(500+count);
			imgEdit.setPadding(10, 10, 10, 10);
			imgEdit.setImageResource(R.drawable.ic_edit_light);
			imgEdit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(status == 1){
						spinner1.setSelection(0);
					} else {
						spinner1.setSelection(1);
					}
					txtTrans.setTextColor(Color.BLACK);
					txtTrans.setText("    "+id_transaksi);
					txtNominal.setTextColor(Color.BLACK);
					txtNominal.setText("    Rp."+hrg_jual);
					_id = id;
					nama = nm_pembeli;
				}
			});
			tr.addView(imgEdit);
			
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
					count++;
		}
	}

}
