package id.aditya.andropulsa;

import java.util.List;

import id.aditya.andropulsa.database.DsDistributor;
import id.aditya.andropulsa.database.DsOperator;
import id.aditya.andropulsa.database.DsSaldo;
import id.aditya.andropulsa.kelas.AutoObject;
import id.aditya.andropulsa.kelas.ClassValidation;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class SaldoActivity extends SherlockActivity implements OnItemSelectedListener {
	
	private DsOperator dsoperator;
	private DsDistributor dsdistributor;
	private DsSaldo dssaldo;
	private Spinner spinner1;
	private EditText edtFormat, edtSeparator;
	private int iddistributor;
	private long rowid, cr;
	private ViewGroup mContainerView;
	private ImageView imgDel, imgOk;
	private EditText ed;
	private TextView txtAdd;
	private int kondisi;
	private ViewGroup newView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        edtFormat = (EditText)findViewById(R.id.edtFormatSaldo);
        edtSeparator = (EditText)findViewById(R.id.edtSeparator);
        
        spinner1 = (Spinner)findViewById(R.id.spinner_cek_saldo);
        spinner1.setOnItemSelectedListener(this);
        
        mContainerView = (ViewGroup) findViewById(R.id.contentFilter);
        txtAdd = (TextView) findViewById(R.id.textViewAddFilter);
        txtAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addItem();
			}
		});
        
        dsoperator = new DsOperator(this);
        dsoperator.open();
        
        dsdistributor = new DsDistributor(this);
        dsdistributor.open();
        
        dssaldo = new DsSaldo(this);
        dssaldo.open();
        
        kondisi = mContainerView.getChildCount();
        fetchData();
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				NavUtils.navigateUpTo(this, new Intent(this, PengaturanActivity.class));
				break;
			case 2:
				try{
					ClassValidation.Is_Valid_saldo(edtFormat);
					if(ClassValidation.Is_Valid_saldo(edtFormat).isEmpty()){
						Toast.makeText(getBaseContext(), "Kosong", Toast.LENGTH_SHORT).show();
					} else {
						int x;
						String tes;
						if((kondisi < mContainerView.getChildCount()) == true) {
							Cursor cru = dsdistributor.updateDataFormat(edtFormat.getText().toString(), edtSeparator.getText().toString(), iddistributor);
							if(cru != null){
								for(x=kondisi+1; x<=mContainerView.getChildCount(); x++){
									ed = (EditText) findViewById(x);
									if(ed.getText().toString().isEmpty() == true){
										tes = null;
									} else {
										tes = ed.getText().toString();
									}
									cr = dssaldo.insertNo(iddistributor, tes);
								}
								Toast.makeText(getBaseContext(), "Data telah ditambahkan", Toast.LENGTH_SHORT).show();
							}
						} else {
							Cursor cru = dsdistributor.updateDataFormat(edtFormat.getText().toString(), edtSeparator.getText().toString(), iddistributor);
							if(cru != null){
								updateDataNoFilter();
							}
							Toast.makeText(getBaseContext(), "Data telah diupdate", Toast.LENGTH_SHORT).show();
						}
						
					}
				} catch(Exception e){
					e.printStackTrace();
				}
				break;
		}
		return true;
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		menu.add(0, 2, 0, null).setIcon(R.drawable.ic_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Cursor cursor = (Cursor) spinner1.getSelectedItem();  
		rowid = cursor.getLong(cursor.getColumnIndex("_id"));
		iddistributor = (int) rowid;
		fetchDataFormat(iddistributor);
		mContainerView.removeAllViews();
		tampilDataNomor();
		kondisi = mContainerView.getChildCount();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("deprecation")
	private void fetchData(){
		Cursor cr = dsoperator.fetchdistributor();
		String[] from = new String[]{"nm_distributor"};
		int[] to = new int[]{android.R.id.text1};
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cr, from, to);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(mAdapter);
	}
	
	private void fetchDataFormat(int id){
		Cursor cr = dsdistributor.fetchDataFormat(id);
		if(cr.isNull(0)){
			edtFormat.setText(null);
		} else {
			edtFormat.setText(cr.getString(0).toString());
		}
		edtSeparator.setText(cr.getString(1).toString());
	}
	
	public void tampilDataNomor(){
		int i;		
		List<AutoObject> data = dssaldo.fetchNomFilterId(iddistributor);
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
			cr = dssaldo.insertNo(iddistributor, tes);			
		}
		if(cr == -1){
			Toast.makeText(getBaseContext(), "Data nomor kosong", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getBaseContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateDataNoFilter(){
		int y;
		String tes;
		List<AutoObject> data = dssaldo.fetchNomFilterId(iddistributor);
		try{
			for(y=0; y<mContainerView.getChildCount(); y++){
				ed = (EditText) findViewById(y+1);
				tes = ed.getText().toString();
				dssaldo.updateDataNoFilter(tes, Integer.valueOf(data.get(y).getDtidNomor()));
			}
		} catch(Exception e){
		}
	}
	
	private void addItem() {
		int id = 1;
		final int cek = (mContainerView.getChildCount());
		
		newView = (ViewGroup) LayoutInflater.from(this).inflate(
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
		ed.setHint("+628");
		ed.setInputType(InputType.TYPE_CLASS_PHONE);
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
				AlertDialog.Builder aBuilder = new AlertDialog.Builder(SaldoActivity.this);
				aBuilder.setMessage("Apakah ingin menghapus data nomor " + no_pembeli.toString() + "?")
						.setCancelable(false)
						.setTitle("Hapus data")
						.setIcon(R.drawable.ic_warning)
						.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								int delete = dssaldo.deleteNoFilter(idnomor);
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

}
