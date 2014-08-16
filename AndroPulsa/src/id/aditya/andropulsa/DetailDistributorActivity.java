package id.aditya.andropulsa;

import java.util.ArrayList;
import java.util.List;

import id.aditya.andropulsa.database.DsDistributor;
import id.aditya.andropulsa.kelas.ClassDistributor;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

@SuppressLint("NewApi")
public class DetailDistributorActivity extends SherlockActivity implements ActionMode.Callback, OnClickListener, OnItemLongClickListener {
		
	private ListView dataList;
	private DsDistributor dsDistributor;
	private ListDistributor adapter1;
	private ActionMode mActionMode;
	private ArrayList<String> data = new ArrayList<String>();
	private ArrayList<String> dataId = new ArrayList<String>();
	private ArrayList<String> dataTot = new ArrayList<String>();
	private TextView t, t1;
	ArrayAdapter<String> adapter;
	
		@Override
	    protected void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_detail_distributor);
	        ActionBar ab = getSupportActionBar();
	        ab.setLogo(getResources().getDrawable(R.drawable.ic_settings));
	        ab.setDisplayShowTitleEnabled(true);
	        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
	        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	        dataList = (ListView) findViewById(R.id.listdistributor);
	        
	        dsDistributor = new DsDistributor(this);
	        dsDistributor.open();
	        
	        tampilDataDistributor();
		}
		
		public void tampilDataDistributor(){
			Cursor cr = dsDistributor.fetchDistributorAll();
			if(cr != null){
				if(cr.moveToFirst()){
					do {
						String id = cr.getString(0);
						dataId.add(id);
						String nama = cr.getString(1);
						data.add(nama);
						List<String> crd = dsDistributor.count_data();
						String total;
						for(int i=0; i<crd.size(); i++){
							total = crd.get(i).toString();
							dataTot.add(total);
						}
					} while(cr.moveToNext());
				}
			}
			adapter1 = new ListDistributor(this,data,dataId,dataTot);
			dataList.setTextFilterEnabled(true);
			dataList.setAdapter(adapter1);
			dataList.setOnItemLongClickListener(this);
			dataList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					TextView t = (TextView) view.findViewById(R.id.txtIdDs);
					ClassDistributor Cdistributor = new ClassDistributor();
					Cursor cr = dsDistributor.fetchDistributor(Integer.valueOf(t.getText().toString()));
					Cdistributor.setStatus("edit");
					Cdistributor.setId_distributor(cr.getInt(0));
					Cdistributor.setNm_distributor(cr.getString(1));
					Cdistributor.setAlamat(cr.getString(2));
					Cdistributor.setTelp(cr.getString(3));
					Cdistributor.setSms(cr.getString(4));
					Cdistributor.setPin(cr.getInt(5));
					Intent a = new Intent(DetailDistributorActivity.this, DistributorActivity.class);
					a.putExtra("id.aditya.andropulsa.ClassDistributor", Cdistributor);
					startActivity(a);
				}
	        	
	        });
			
		}
		
		public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
			switch(item.getItemId()){
				case android.R.id.home:
					//NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
					DetailDistributorActivity.this.finish();
					break;
				case 3:
					ClassDistributor Cdistributor = new ClassDistributor();
					Cdistributor.setStatus("tambah");
					Intent a = new Intent(this, DistributorActivity.class);
					a.putExtra("id.aditya.andropulsa.ClassDistributor", Cdistributor);
					startActivity(a);
					break;
			}
			return true;
		}
		
		@SuppressLint("InlinedApi")
		@Override
		public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
			menu.add(0, 3, 0, null).setIcon(R.drawable.ic_content_new).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
			return true;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// TODO Auto-generated method stub
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.context_distributor, menu);
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
					hapusDataDistributor();
					return true;
				default:
					return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			mActionMode = null;
		}
		
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			if(mActionMode != null){
				return false;
			}
			t = (TextView) view.findViewById(R.id.txtIdDs);
			t1 = (TextView) view.findViewById(R.id.txtNmDistributor);
			mActionMode = this.startActionMode(this);
			mActionMode.setTitle(t1.getText().toString());
			view.setSelected(true);
			
			return true;
		}
		
		private void refreshData(){
			ListDistributor adapter1 = new ListDistributor(this,data,dataId,dataTot);
			adapter1.clear();
			dataList.setAdapter(adapter1);
		}
		
		private void hapusDataDistributor(){
			AlertDialog.Builder aBuilder = new AlertDialog.Builder(DetailDistributorActivity.this);
			aBuilder.setMessage("Apakah ingin menghapus data distributor " + t1.getText().toString() + "?")
					.setCancelable(false)
					.setTitle("Hapus data")
					.setIcon(R.drawable.ic_warning)
					.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dsDistributor.deleteDistributor(Integer.valueOf(t.getText().toString()));
							refreshData();
							tampilDataDistributor();
							mActionMode.finish();
							Toast.makeText(getBaseContext(), "Data Terhapus", Toast.LENGTH_SHORT).show();							
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
		
	}