package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsPembeli;
import id.aditya.andropulsa.kelas.ClassPembeli;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ActionMode;
import android.view.inputmethod.InputMethodManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;

@SuppressLint("NewApi")
public class ListPembeliActivity extends SherlockActivity implements ActionMode.Callback, OnItemLongClickListener {
	//private static final int item_id = 1;
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> dataId = new ArrayList<String>();
	private ArrayList<String> dataTot = new ArrayList<String>();
	private ArrayList<String> dataUtang = new ArrayList<String>();
	private DsPembeli dspembeli;
	private ListView dataList;
	private ActionMode mActionMode;
	private String id_, pref;
	private TextView t, t1;
	private EditText edtSearch;
	private ImageView imgNotFound;
	private LinearLayout line_out;
	private ListPembeli adapter1;
	private String data_preference = "id.aditya.andropulsa_preferences";
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	ProgressDialog progressDialog;
	ArrayAdapter<String> adapter;
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembeli);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_social_group));
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        
        sp = this.getSharedPreferences(data_preference, 0);
        spe = sp.edit();
        
        imgNotFound = (ImageView) findViewById(R.id.imgpembeli_notfound);
        line_out = (LinearLayout) findViewById(R.id.listpembeli_notfound);
        dataList = (ListView) findViewById(R.id.listpembeli);
        dataList.setOnItemLongClickListener(this);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView t = (TextView) view.findViewById(R.id.txtId);
				TextView tTot = (TextView)view.findViewById(R.id.txtTotal);
				TextView tUtang = (TextView)view.findViewById(R.id.txtUtang);
				//String value = (String) parent.getAdapter().getItem(position);
				//Toast.makeText(getBaseContext(), t.getText().toString(), Toast.LENGTH_SHORT).show();
				Intent a = new Intent(ListPembeliActivity.this, DetailPembeliActivity.class);
				a.putExtra("id_", t.getText().toString());
				a.putExtra("tot", tTot.getText().toString());
				a.putExtra("utang", tUtang.getText().toString());
				startActivity(a);
			}
        	
        });
        
        new loadData().execute();
        
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				//NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
				ListPembeliActivity.this.finish();
				break;
			case 3:
				ClassPembeli Cpembeli = new ClassPembeli();
				Cpembeli.setStatus("tambah");
				Intent a = new Intent(this, DataPembeliActivity.class);
				a.putExtra("id.aditya.andropulsa.ClassPembeli", Cpembeli);
				startActivity(a);
				break;
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		//menu.add(0, 2, 0, null).setIcon(R.drawable.ic_search).setShowAsAction(item_id);
		
		/*getSupportMenuInflater().inflate(R.menu.menu_search, menu);
		
		edtSearch = (EditText) menu.findItem(R.id.menu_search_pembeli).getActionView();
		edtSearch.setHintTextColor(getResources().getColor(R.color.abs__primary_text_holo_dark));
		edtSearch.addTextChangedListener(textWatcher);
		com.actionbarsherlock.view.MenuItem menuSearch = menu.findItem(R.id.menu_search_pembeli);
		menuSearch.setOnActionExpandListener(new OnActionExpandListener(){

			@Override
			public boolean onMenuItemActionExpand(com.actionbarsherlock.view.MenuItem item) {
				// TODO Auto-generated method stub
				edtSearch.setText("");
				edtSearch.requestFocus();
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(com.actionbarsherlock.view.MenuItem item) {
				// TODO Auto-generated method stub
				edtSearch.requestFocus();
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				return true;
			}
			
		});*/
		menu.add(0, 3, 0, null).setIcon(R.drawable.ic_content_new).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}
	
	private TextWatcher textWatcher = new TextWatcher()
	{
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			adapter1.getFilter().filter(s.toString());
		}
		
	    public void beforeTextChanged(CharSequence s, int start, int count, int after)
	    {
	    	
	    }

	    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
	    {
	        // your search logic here
	    }
	};
	
	public class loadData extends AsyncTask<Void, Void, Void>{

		protected void onPreExecute(){
			dspembeli = new DsPembeli(ListPembeliActivity.this);
	        dspembeli.open();
			progressDialog = new ProgressDialog(ListPembeliActivity.this);
			progressDialog.setMessage("Mohon tunggu...");
			progressDialog.setIndeterminate(false);
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			tampilDataPembeli();
			return null;
		}
		
		protected void onPostExecute(Void unused) {
			dataList.setTextFilterEnabled(true);
			dataList.setAdapter(adapter1);
			progressDialog.dismiss();
			dspembeli.close();
	    }
		
	}
	
	public void tampilDataPembeli(){
		Cursor cr = dspembeli.fetchPembeli();
		try{
			if(cr != null){
				if(cr.moveToFirst()){
					List<String> crd = dspembeli.countPembelian();
					List<String> cru = dspembeli.countPiutang();
					do {
						String id = cr.getString(0);
						dataId.add(id);
						String nama = cr.getString(1);
						data.add(nama);
						String total, piutang;
						for(int i=0; i<crd.size(); i++){
							total = crd.get(i).toString();
							dataTot.add(total);
						}
						
						for(int i=0; i<cru.size(); i++){
							piutang = cru.get(i).toString();
							dataUtang.add(piutang);
						}
					} while(cr.moveToNext());
					crd.clear();
					cru.clear();
				}
			}
		} catch(Exception e) {
			Toast.makeText(getBaseContext(), "Refresh Kembali", Toast.LENGTH_SHORT).show();
		} finally {
			cr.close();
		}
		pref = sp.getString("prefBonus", "");
		adapter1 = new ListPembeli(this,data,dataId,dataTot,dataUtang, pref.toString());
		
		/*if(adapter1.getCount() != 0){
			dataList.setVisibility(View.VISIBLE);
			line_out.setVisibility(View.GONE);
			imgNotFound.setVisibility(View.GONE);
		} else {
			line_out.setVisibility(View.VISIBLE);
			dataList.setVisibility(View.GONE);
			imgNotFound.setVisibility(View.VISIBLE);
		}*/
	}
	
	private void refreshData(){
		ListPembeli adapter1 = new ListPembeli(this,data,dataId,dataTot,dataUtang, pref.toString());
		adapter1.clear();
		dataList.setAdapter(adapter1);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
			case R.id.action_hapus:
				hapusDataPembeli();
				return true;
			case R.id.action_edit:
				dspembeli = new DsPembeli(ListPembeliActivity.this);
				ClassPembeli Cpembeli = new ClassPembeli();
				Cursor cr = null;
				try{
			        dspembeli.open();
					cr = dspembeli.fetchPembeliId(Integer.valueOf(id_));
					Cpembeli.setStatus("edit");
					Cpembeli.setId_pembeli(Integer.valueOf(id_));
					Cpembeli.setNm_pembeli(cr.getString(1));
					Cpembeli.setAlmt_pembeli(cr.getString(2));
					Intent i = new Intent(ListPembeliActivity.this, DataPembeliActivity.class);
					i.putExtra("id.aditya.andropulsa.ClassPembeli", Cpembeli);
					//Toast.makeText(getBaseContext(), "Edit "+id_.toString(), Toast.LENGTH_SHORT).show();
					startActivity(i);
				} finally {
					cr.close();
					dspembeli.close();
				}
				
				return true;
			default:
				return false;
		}
	}
	
	private void hapusDataPembeli(){
		AlertDialog.Builder aBuilder = new AlertDialog.Builder(ListPembeliActivity.this);
		aBuilder.setMessage("Apakah ingin menghapus data pembeli " + t1.getText().toString() + "?")
				.setCancelable(false)
				.setTitle("Hapus data")
				.setIcon(R.drawable.ic_warning)
				.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dspembeli = new DsPembeli(ListPembeliActivity.this);
						try{
							dspembeli.open();
							dspembeli.deleteNoPembeliId(Integer.valueOf(id_));
							dspembeli.deletePembeli(Integer.valueOf(id_));
							refreshData();
							tampilDataPembeli();
							mActionMode.finish();
							Toast.makeText(getBaseContext(), "Data Terhapus", Toast.LENGTH_SHORT).show();
						} finally {
							dspembeli.close();
						}
					}
				})
				
				.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
						mActionMode.finish();
					}
				}).show();
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		mActionMode = null;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		if(mActionMode != null){
			return false;
		}
		t = (TextView) view.findViewById(R.id.txtId);
		t1 = (TextView) view.findViewById(R.id.txtNmPembeli);
		id_ = t.getText().toString();
		mActionMode = this.startActionMode(this);
		mActionMode.setTitle(t1.getText().toString());
		view.setSelected(true);
		
		return true;
	}

}
