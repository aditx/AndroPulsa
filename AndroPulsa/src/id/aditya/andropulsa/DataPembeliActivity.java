package id.aditya.andropulsa;

import java.util.List;

import id.aditya.andropulsa.database.DsPembeli;
import id.aditya.andropulsa.kelas.AutoObject;
import id.aditya.andropulsa.kelas.ClassPembeli;
import id.aditya.andropulsa.kelas.ClassValidation;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class DataPembeliActivity extends SherlockActivity implements OnClickListener {
	
	private static final int item_id = 1;
	private ViewGroup mContainerView;
	private ImageView imgDel, imgOk;
	private TextView txtAdd;
	private EditText ed, edtNama, edtAlamat;
	private DsPembeli dspembeli;
	private int idpembeli;
	private long cr;
	private ClassPembeli Cpembeli;
	private Bundle b;
	private int kondisi;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pembeli);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_social_group));
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        
        b = getIntent().getExtras();
		Cpembeli = b.getParcelable("id.aditya.andropulsa.ClassPembeli");
        
        edtNama = (EditText) findViewById(R.id.edittxt_nama_pembeli);
        edtAlamat = (EditText) findViewById(R.id.edittxt_almt_pembeli);
        
        mContainerView = (ViewGroup) findViewById(R.id.content);
        txtAdd = (TextView) findViewById(R.id.textViewAddPembeli);
        txtAdd.setOnClickListener(this);
        
        dspembeli = new DsPembeli(this);
        dspembeli.open();
        
        if(Cpembeli.getStatus().contains("tambah")) {
        	addItem();
        } else {
        	ab.setTitle("Edit Data Pembeli");
        	tampilDataNomor();
        	kondisi = mContainerView.getChildCount();
        	//Toast.makeText(getBaseContext(), String.valueOf(mContainerView.getChildCount()), Toast.LENGTH_SHORT).show();
        }
        
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, ListPembeliActivity.class));
				DataPembeliActivity.this.finish();
				break;
			case 2:
				try{
					ClassValidation.Is_Valid_nama(edtNama);
					ClassValidation.Is_Valid_alamat(edtAlamat);
					if(ClassValidation.Is_Valid_nama(edtNama).isEmpty() 
							|| ClassValidation.Is_Valid_alamat(edtAlamat).isEmpty()){
						Toast.makeText(getBaseContext(), "Kosong", Toast.LENGTH_LONG).show();
					} else {
						insertData();
					}
				} catch(Exception e){
					Toast.makeText(getBaseContext(), "Lengkapi semua data dengan benar", Toast.LENGTH_SHORT).show();
				}
				break;
			case 3:
				switchUpdate();
				break;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		//menu.add(0, 2, 0, null).setIcon(R.drawable.ic_save).setShowAsAction(item_id);
		if(Cpembeli.getStatus().contains("tambah")) {
			menu.add(0, 2, 0, null).setIcon(R.drawable.ic_save).setShowAsAction(item_id);
		} else if(Cpembeli.getStatus().contains("edit")) {
			menu.add(0, 3, 0,null).setIcon(R.drawable.ic_edit).setShowAsAction(item_id);
			edtNama.setText(Cpembeli.getNm_pembeli().toString());
			edtAlamat.setText(Cpembeli.getAlmt_pembeli().toString());
		}
		return true;
	}
	
	private void addItem() {
		int id = 1;
		final int cek = (mContainerView.getChildCount());
		
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.list_item, mContainerView, false);
		imgOk = (ImageView) newView.findViewById(R.id.imageOk);
		imgDel = (ImageView) newView.findViewById(R.id.imageDelNoPembeli);
		imgDel.setId(id+cek);
		imgDel.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(v.getId() == mContainerView.getChildCount()){
					mContainerView.removeView(newView);
				} else {
					Toast.makeText(getBaseContext(), "Hapus sesuai urutan", Toast.LENGTH_SHORT).show();
				}
				//Toast.makeText(getBaseContext(), "ID:"+String.valueOf(v.getId())+"Pos:"+String.valueOf(mContainerView.getChildCount()).toString().toString(), Toast.LENGTH_SHORT).show();
				//Toast.makeText(getBaseContext(), String.valueOf(cek+1).toString(), Toast.LENGTH_SHORT).show();
			}
		});
		TextView txt = (TextView) newView.findViewById(R.id.textViewNoPembeli);
		ed = (EditText) newView.findViewById(R.id.edittxt_nama);
		ed.setId(id+cek);
		/*TextWatcher textWatcher = new TextWatcher()
		{
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
			
		    public void beforeTextChanged(CharSequence s, int start, int count, int after)
		    {
		    	
		    }

		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
		    {		
		    	int len = ed.getText().length();
		    	
		    	if(len == 4 || len == 9){
		    		ed.append("-");
		        }
		    }
		};
		ed.addTextChangedListener(textWatcher);*/
		//ed.setText(String.valueOf(id+cek).toString());
		txt.setText("Nomor :");
		
		if(cek == 0){
			imgDel.setVisibility(View.GONE);
			imgOk.setVisibility(View.VISIBLE);
		} else {
			imgDel.setVisibility(View.VISIBLE);
			imgOk.setVisibility(View.GONE);
		}
		
		mContainerView.addView(newView, 0);
	}
	
	private String addItem2(String nopembeli, String id_nomor) {
		int id = 1;
		final int cek = (mContainerView.getChildCount());
		final String no_pembeli = nopembeli;
		final int idnomor = Integer.valueOf(id_nomor);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this).inflate(
				R.layout.list_item, mContainerView, false);
		imgOk = (ImageView) newView.findViewById(R.id.imageOk);
		imgDel = (ImageView) newView.findViewById(R.id.imageDelNoPembeli);
		imgDel.setId(Integer.valueOf(id_nomor));
		imgDel.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder aBuilder = new AlertDialog.Builder(DataPembeliActivity.this);
				aBuilder.setMessage("Apakah ingin menghapus data nomor " + no_pembeli.toString() + "?")
						.setCancelable(false)
						.setTitle("Hapus data")
						.setIcon(R.drawable.ic_warning)
						.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								int delete = dspembeli.deleteNoPembeli(idnomor);
								if(delete == 0){
									Toast.makeText(getBaseContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
								} else {
									mContainerView.removeView(newView);
									Toast.makeText(getBaseContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
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
		});
		TextView txt = (TextView) newView.findViewById(R.id.textViewNoPembeli);
		ed = (EditText) newView.findViewById(R.id.edittxt_nama);
		
		ed.setId(id+cek);
		ed.setText(nopembeli);
		txt.setText("Nomor :");
		
		mContainerView.addView(newView, 0);
		return nopembeli;
	}
	
	public void tampilDataNomor(){
		int i;		
		List<AutoObject> data = dspembeli.fetchNomPembeliId(Cpembeli.getId_pembeli());
		if(data.size() != 0){
			for(i=0; i<data.size(); i++){
				addItem2(data.get(i).getDtNomor(), data.get(i).getDtidNomor());
			}
		}
	}
	
	public void tambahDataNomor(){
		int x;
		String tes;
		for(x=1; x<=mContainerView.getChildCount(); x++){
			ed = (EditText) findViewById(x);
			if(ed.getText().toString().isEmpty() == true){
				tes = null;
			} else {
				tes = ed.getText().toString();
			}
			cr = dspembeli.insertNo(idpembeli, tes);			
		}
		if(cr == -1){
			Toast.makeText(getBaseContext(), "Data nomor kosong", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getBaseContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void switchUpdate(){
		int x;
		String tes;
		if((kondisi < mContainerView.getChildCount()) == true) {
			updateData();
			for(x=kondisi+1; x<=mContainerView.getChildCount(); x++){
				ed = (EditText) findViewById(x);
				if(ed.getText().toString().isEmpty() == true){
					tes = null;
				} else {
					tes = ed.getText().toString();
				}
				cr = dspembeli.insertNo(Cpembeli.getId_pembeli(), tes);
				//Toast.makeText(getBaseContext(), tes.toString(), Toast.LENGTH_SHORT).show();
			}
			if(cr == -1){
				Toast.makeText(getBaseContext(), "Isi data dengan benar", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getBaseContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
				NavUtils.navigateUpTo(this, new Intent(this, ListPembeliActivity.class));
				DataPembeliActivity.this.finish();
			}
		} else {
			updateData();
			Toast.makeText(getBaseContext(), "Update data berhasil", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void insertData() {
		ClassPembeli cp = null;
		cp = dspembeli.insertPembeli(ClassValidation.Is_Valid_nama(edtNama), ClassValidation.Is_Valid_alamat(edtAlamat));
		idpembeli = cp.getId_pembeli();
		tambahDataNomor();
		NavUtils.navigateUpTo(this, new Intent(this, ListPembeliActivity.class));
		DataPembeliActivity.this.finish();
	}
	
	public void updateData(){
		try{
			dspembeli.updateDataPembeli(edtNama.getText().toString(), edtAlamat.getText().toString(), Cpembeli.getId_pembeli());
			//Toast.makeText(getBaseContext(), "Update data berhasil", Toast.LENGTH_SHORT).show();
			updateDataNoTelp();
		} catch(Exception e){
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateDataNoTelp(){
		int y;
		String tes;
		List<AutoObject> data = dspembeli.fetchNomPembeliId(Cpembeli.getId_pembeli());
		try{
			for(y=0; y<mContainerView.getChildCount(); y++){
				ed = (EditText) findViewById(y+1);
				tes = ed.getText().toString();
				dspembeli.updateDataNoPembeli(tes, Integer.valueOf(data.get(y).getDtidNomor()));
			}
			//Toast.makeText(getBaseContext(), "Update data berhasil", Toast.LENGTH_SHORT).show();
		} catch(Exception e){
			//Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.textViewAddPembeli:
				addItem();
				break;
		}
	}
	
	@Override
	public void onBackPressed() {
	    // do nothing.
		NavUtils.navigateUpTo(this, new Intent(this, ListPembeliActivity.class));
		DataPembeliActivity.this.finish();
	}

}