package id.aditya.andropulsa;

import java.util.ArrayList;

import id.aditya.andropulsa.database.DsOperator;
import id.aditya.andropulsa.kelas.ClassOperator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;

@SuppressLint("NewApi")
public class ListOperatorActivity extends SherlockActivity implements OnNavigationListener {
	
	private EditText edtSearch;
	private TableLayout tl;
	private DsOperator operator;
	private String nm_preference = "DataPreference";
	private Integer id_user;
	private Button btn_tambah;
	private TextView txt_notfound, txt_kosong;
	private ImageView img_notfound;
	private int stsTbl = 0;
	private Cursor cr;
	
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	ActionBar ab;
	ArrayAdapter<String> adb;
	
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_data_storage));
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
                
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        operator = new DsOperator(this);
        operator.open();
        
        ArrayList<String> itemMenu = new ArrayList<String>();
        itemMenu.add(0, "All Operator");
        Cursor cr = operator.fetchdist();
        for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
        	itemMenu.add(cr.getString(1));
        }
        adb = new ArrayAdapter<String>(ab.getThemedContext(), android.R.layout.simple_list_item_1, android.R.id.text1, itemMenu);
        
        ab.setListNavigationCallbacks(adb, this);
        
        tl = (TableLayout)findViewById(R.id.main_table);
        txt_notfound = (TextView)findViewById(R.id.textview_notfound);
        txt_kosong = (TextView)findViewById(R.id.textview_null);
        img_notfound = (ImageView)findViewById(R.id.imgnotfound);
        
        btn_tambah = (Button)findViewById(R.id.btn_tambah_op);
        btn_tambah.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				ClassOperator Coperator = new ClassOperator();
				Coperator.setStatus("tambah");
				Intent i = new Intent(ListOperatorActivity.this, DataOperatorActivity.class);
				i.putExtra("id.aditya.andropulsa.ClassOperator", Coperator);
				startActivity(i);
				//ListOperatorActivity.this.finish();
			}
        	
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		//menu.add(0, 2, 0, null).setIcon(R.drawable.ic_search).setShowAsAction(item_id);
		getSupportMenuInflater().inflate(R.menu.menu, menu);
		
		edtSearch = (EditText) menu.findItem(R.id.menu_search).getActionView();
		edtSearch.setHintTextColor(getResources().getColor(R.color.abs__primary_text_holo_dark));
		edtSearch.addTextChangedListener(textWatcher);
		MenuItem menuSearch = menu.findItem(R.id.menu_search);
		
		menuSearch.setOnActionExpandListener(new OnActionExpandListener(){

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				// TODO Auto-generated method stub
				edtSearch.setText("");
				edtSearch.requestFocus();
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				// TODO Auto-generated method stub
				edtSearch.requestFocus();
				
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
				return true;
			}
			
		});
		return true;
	}
	
	@SuppressLint({ "InlineApi", "ServiceCast" })
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				//NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
				ListOperatorActivity.this.finish();
				break;
		}
		return true;
	}
	
	private TextWatcher textWatcher = new TextWatcher()
	{
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}
		
	    public void beforeTextChanged(CharSequence s, int start, int count, int after)
	    {
	    	txt_notfound.setVisibility(View.GONE);
	    	tl.removeAllViews();
	    	stsTbl = 0;
	    	displayTable(0);
	    }

	    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
	    {
	        // your search logic here
	    	id_user = sp.getInt("id_user", 0);
	    	String inputan = edtSearch.getText().toString();
	    	Cursor cr = operator.filterOperator(inputan,id_user);
	    	if(cr.getCount()==0) {
	    		tl.removeAllViews();
	    		txt_notfound.setVisibility(View.VISIBLE);
	    	} else {
	    		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
		    		if(inputan.trim().equalsIgnoreCase(cr.getString(2))){
		    			tl.removeAllViews();
		    			stsTbl = 1;
		    			displayTable(0);
		    		}
		    	}
	    	}
	    }
	};
	
	@SuppressWarnings("deprecation")
	private void displayTable(int id){
		id_user = sp.getInt("id_user", 0);
		if(ab.getSelectedNavigationIndex() == 0){
			if(stsTbl == 0){
				cr = operator.fetchOperator(id_user);
			} else if (stsTbl == 1){
				String inputan = edtSearch.getText().toString();
		    	cr = operator.filterOperator(inputan,id_user);
			}
		} else {
			cr = operator.fetchOperatorCustom(id_user, id);
		}
		
		
		TableRow tr_head = new TableRow(this);
		tr_head.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0E0E6")));
		
		TextView thead_nm_op = new TextView(this);
		thead_nm_op.setId(10);
		thead_nm_op.setText("Operator");
		thead_nm_op.setPadding(10, 15, 10, 10);
		thead_nm_op.setTextColor(Color.BLACK);
		tr_head.addView(thead_nm_op);
		
		TextView thead_nominal = new TextView(this);
		thead_nominal.setId(20);
		thead_nominal.setText("Nominal");
		thead_nominal.setPadding(10, 15, 10, 10);
		thead_nominal.setTextColor(Color.BLACK);
		tr_head.addView(thead_nominal);
		
		TextView thead_harga = new TextView(this);
		thead_harga.setId(30);
		thead_harga.setText("Harga");
		thead_harga.setPadding(10, 15, 10, 10);
		thead_harga.setTextColor(Color.BLACK);
		tr_head.addView(thead_harga);
		
		TextView thead_aksi = new TextView(this);
		TableRow.LayoutParams params = new TableRow.LayoutParams();
		params.span = 2;
		thead_aksi.setId(40);
		thead_aksi.setText("Aksi");
		thead_aksi.setPadding(10, 15, 10, 10);
		thead_aksi.setTextColor(Color.BLACK);
		tr_head.addView(thead_aksi, 3, params);
		
		tl.addView(tr_head, new TableLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
		
		int count = 0;
		while(cr.moveToNext()){
			final Integer idop = cr.getInt(0);
			final String nmop = cr.getString(2);
			final String kodeop = cr.getString(3);
			final Integer hrgjual = cr.getInt(4);
			final Integer hrgbeli = cr.getInt(5);
			final String nominal = cr.getString(6);
			final Integer id_distributor = cr.getInt(7);
			final Integer row_id = cr.getInt(8);
			
			//Create table row
			TableRow tr = new TableRow(this);
			if(count%2!=0) tr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0E0E6")));
			tr.setId(100+count);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
					
			//Display nm_op
			final TextView txtnm_op = new TextView(this);
			txtnm_op.setId(200+count);
			txtnm_op.setText(nmop.toString());
			txtnm_op.setPadding(10, 15, 10, 10);
			txtnm_op.setTextColor(Color.BLACK);
			tr.addView(txtnm_op);
			
			//Display nominal
			TextView txt_nominal = new TextView(this);
			txt_nominal.setId(300+count);
			txt_nominal.setText(nominal.toString());
			txt_nominal.setPadding(10, 15, 10, 10);
			txt_nominal.setTextColor(Color.BLACK);
			tr.addView(txt_nominal);
			
			//Display nm_op
			TextView txthrg_jual = new TextView(this);
			txthrg_jual.setId(400+count);
			txthrg_jual.setText("Rp."+hrgjual.toString());
			txthrg_jual.setTextColor(Color.BLACK);
			tr.addView(txthrg_jual);
			
			ImageView imgEdit = new ImageView(this);
			imgEdit.setId(500+count);
			imgEdit.setPadding(10, 10, 10, 10);
			imgEdit.setImageResource(R.drawable.ic_edit_light);
			imgEdit.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ClassOperator Coperator = new ClassOperator();
					Coperator.setStatus("edit");
					Coperator.setId_op(idop);
					Coperator.setNm_op(nmop);
					Coperator.setKode(kodeop);
					Coperator.setNominal(nominal);
					Coperator.setHrg_beli(hrgbeli);
					Coperator.setHrg_jual(hrgjual);
					Coperator.setId_distributor(id_distributor);
					Coperator.setRow_id(row_id);
					Intent i = new Intent(ListOperatorActivity.this, DataOperatorActivity.class);
					i.putExtra("id.aditya.andropulsa.ClassOperator", Coperator);
					startActivity(i);
					//ListOperatorActivity.this.finish();
				}
			});
			tr.addView(imgEdit);
			
			ImageView imgDelete = new ImageView(this);
			imgDelete.setId(600+count);
			imgDelete.setPadding(0, 9, 0, 0);
			imgDelete.setImageResource(R.drawable.ic_discard);
			imgDelete.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder aBuilder = new AlertDialog.Builder(ListOperatorActivity.this);
					aBuilder.setMessage("Apakah ingin menghapus data\n" + nmop + " " + nominal + "?")
							.setCancelable(false)
							.setTitle("Hapus data")
							.setIcon(R.drawable.ic_warning)
							.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									int delete = operator.deleteOperator(idop);
									if(delete == 0){
										Toast.makeText(getBaseContext(), "Data gagal dihapus", Toast.LENGTH_SHORT).show();
									} else {
										Toast.makeText(getBaseContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
										tl.removeAllViews();
										displayTable(0);
									}
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
			});
			tr.addView(imgDelete);
			
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
					count++;
		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
			Cursor id = operator.pilihIdDistributor(itemPosition);
			id_user = sp.getInt("id_user", 0);
			cr = operator.fetchOperator(id_user);
			if(cr.getCount() == 0){
				img_notfound.setVisibility(View.VISIBLE);
				txt_kosong.setVisibility(View.VISIBLE);
			} else {
				img_notfound.setVisibility(View.GONE);
				txt_kosong.setVisibility(View.GONE);
				tl.removeAllViews();
				if(ab.getSelectedNavigationIndex() == 0){
					displayTable(0);
				} else {
					displayTable(id.getInt(0));
				}
			}
		return false;
	}
	
}