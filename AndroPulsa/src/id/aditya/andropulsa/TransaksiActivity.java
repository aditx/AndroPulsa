package id.aditya.andropulsa;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import id.aditya.andropulsa.database.DsDistributor;
import id.aditya.andropulsa.database.DsOperator;
import id.aditya.andropulsa.database.DsPembeli;
import id.aditya.andropulsa.database.DsTransaksi;
import id.aditya.andropulsa.kelas.AutoObject;
import id.aditya.andropulsa.kelas.ClassKirimSms;
import id.aditya.andropulsa.kelas.ClassTransaksi;
import id.aditya.andropulsa.kelas.DataObject;
import id.aditya.andropulsa.kelas.SpinnerObject;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.SubMenu;

public class TransaksiActivity extends SherlockActivity implements OnItemSelectedListener {
	
	private TextView txtIdTrans;
	private Spinner spinner1, spinner2, spinner3;
	private String nm_preference = "DataPreference";
	private Integer id_user;
	private DsTransaksi dsTrans;
	private DsPembeli dsPembeli;
	private DsOperator dsOperator;
	private DsDistributor dsDistributor;
	private SubMenu mItem;
	private EditText editText;
	private ListView list;
	private LinearLayout layout;
	private AlertDialog myalertDialog = null;
	private String idNo_, value, hasil, idno;
	private EditText ed;
	private ImageView ib;
	private RadioButton rB;
	private RadioGroup rG;
	private int multiple, idNominal, params, iddistributor;
	private long cr, rowid;
	
	SimpleCursorAdapter sca;
	ArrayAdapter<String> datay;
	ArrayList<String> datatrans = new ArrayList<String>();
	List<DataObject> data;
	List<EditText> listED = new ArrayList<EditText>();
	SharedPreferences sp;
	SharedPreferences.Editor spe;
	ProgressDialog progressDialog;
	JSONArray jArray = new JSONArray();
	JSONObject jObject = new JSONObject();
	JSONArray jTemp = new JSONArray();
	
	@Override
    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_email));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        txtIdTrans = (TextView)findViewById(R.id.textViewidtrans);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long arg3) {
				// TODO Auto-generated method stub
				idNominal = Integer.parseInt ( ( (SpinnerObject) spinner2.getSelectedItem ()).getDtIdNominal());
				//Toast.makeText(getBaseContext(), String.valueOf(idNominal).toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        spinner2.setEnabled(false);
        spinner3 = (Spinner)findViewById(R.id.spinner_distributor_trans);
        spinner3.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Cursor cursor = (Cursor) spinner3.getSelectedItem();  
				rowid = cursor.getLong(cursor.getColumnIndex("_id"));
				iddistributor = (int) rowid;
				tampilDataOp(iddistributor);
		        //Toast.makeText(getBaseContext(), String.valueOf(iddistributor).toString(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        sp = this.getSharedPreferences(nm_preference, 0);
        spe = sp.edit();
        multiple = sp.getInt("valMultiple", 1);
        
        dsTrans = new DsTransaksi(this);
        dsTrans.open();
        
        dsPembeli = new DsPembeli(this);
        dsPembeli.open();
        
        dsOperator = new DsOperator(this);
        dsOperator.open();
        
        dsDistributor = new DsDistributor(this);
        dsDistributor.open();
        
        idOtomatis();
        loadDataSpinner();
        
        addItem();
	}
	
	@SuppressLint("InlinedApi")
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		
		mItem = menu.addSubMenu(0, R.style.Theme_Sherlock, 0, null);
		mItem.setIcon(R.drawable.ic_send_now);
		
		mItem.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return true;
	}
	
	@SuppressLint("InlineApi")
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				//NavUtils.navigateUpTo(this, new Intent(this, HomeActivity.class));
				TransaksiActivity.this.finish();
			break;
			case R.style.Theme_Sherlock:
				try{
					AlertDialog.Builder myDialog = new AlertDialog.Builder(TransaksiActivity.this);
					myDialog.setTitle("Lakukan Transaksi")
							.setIcon(R.drawable.ic_warning);
					layout = new LinearLayout(TransaksiActivity.this);
					TextView txt = new TextView(this);
					txt.setText(value.toString());
					txt.setPadding(10, 10, 10, 10);
					txt.setTextSize(17);
					layout.setOrientation(LinearLayout.VERTICAL);
					layout.addView(txt);
					myDialog.setView(layout);
					myDialog.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {

			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	try {
			            		for(int i=0; i<jTemp.length(); i++){
			            			JSONObject json = new JSONObject(jTemp.get(i).toString());
				            		String jsonIDtrans = json.getString("IDtrans");
				            		int jsonIDpembeli = (int) json.getInt("IDpembeli");
				            		int jsonIDop = (int) json.getInt("IDop");
				            		int jsonIDuser = (int) json.getInt("IDuser");
				            		int jsonIDnomor = (int) json.getInt("IDnomor");
				            		String jsonTanggal = json.getString("Tanggal");
				            		int jsonStatus = (int) json.getInt("Status");
				            		cr = dsTrans.insert_tb_transaksi(jsonIDtrans,
				            				jsonIDop, jsonIDuser, jsonIDpembeli, 
				    						jsonIDnomor, jsonTanggal, jsonStatus);
				            		//Toast.makeText(getBaseContext(), "Data"+String.valueOf(cr).toString(), Toast.LENGTH_SHORT).show();
			            		}
			            			
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								Toast.makeText(getBaseContext(),e.toString(), Toast.LENGTH_SHORT).show();
							}
			            	//Proses Mengirim Transaksi SMS
				            Cursor cr = dsDistributor.fetchDistributor(iddistributor);
				            ClassTransaksi ct = new ClassTransaksi(TransaksiActivity.this, value, cr.getString(4).toString());
				            ct.execute("");
			            }
			        });
					myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			                dialog.dismiss();
			            }
			        });

					myalertDialog = myDialog.show();
					//Toast.makeText(getBaseContext(),value.toString(), Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getBaseContext(),"Isi semua data dengan benar", Toast.LENGTH_SHORT).show();
				}
				
		    break;
		}
		return true;
	}
	
	private void prosesTransaksi(int params2, String idchecked) throws JSONException {
		String date_now = (String) DateFormat.format("yyyy-MM-dd", new Date());
    	
		if(jTemp.length()<multiple){
			if(jTemp.length() == 0 || jTemp.length() <= params){
				try {
	    			jObject.put("IDtrans", txtIdTrans.getText().toString());
	    			jObject.put("IDop", idNominal);
	    			jObject.put("IDpembeli", Integer.valueOf(idNo_));
	    			jObject.put("IDuser", sp.getInt("id_user", 0));
	    			jObject.put("IDnomor", Integer.valueOf(idchecked));
	    			jObject.put("Tanggal", date_now);
	    			jObject.put("Status", 1);
	    			jArray.put(jObject);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jTemp.put(jArray.getJSONObject(params2).toString());
			} else {
				String edit = jArray.getJSONObject(params2).toString();
				JSONObject json = new JSONObject(edit);
				json.put("IDtrans", txtIdTrans.getText().toString());
				json.put("IDop", idNominal);
				json.put("IDpembeli", Integer.valueOf(idNo_));
				json.put("IDuser", sp.getInt("id_user", 0));
				json.put("IDnomor", Integer.valueOf(idchecked));
				json.put("Tanggal", date_now);
				json.put("Status", 1);
				jTemp.put(params2, json);
			}
			
		} else {
			String edit = jArray.getJSONObject(params2).toString();
			JSONObject json = new JSONObject(edit);
			json.put("IDtrans", txtIdTrans.getText().toString());
			json.put("IDop", idNominal);
			json.put("IDpembeli", Integer.valueOf(idNo_));
			json.put("IDuser", sp.getInt("id_user", 0));
			json.put("IDnomor", Integer.valueOf(idchecked));
			json.put("Tanggal", date_now);
			json.put("Status", 1);
			jTemp.put(params2, json);
		}
			//Toast.makeText(getBaseContext(),""+jTemp, Toast.LENGTH_LONG).show();
	}
	
	private boolean dataTransaksi(){
		Cursor nominal = dsOperator.filterKodeOperator(idNominal);
		String tujuan = listED.get(params).getText().toString();
		String format = nominal.getString(3)+"."+tujuan;
		Cursor pin = dsDistributor.fetchDistributor(iddistributor);
		multiple = sp.getInt("valMultiple", 1);
		StringBuilder sb = new StringBuilder();
		if(datatrans.size()<multiple){
			if(datatrans.isEmpty() == true){
				datatrans.add(format);
			} else if(datatrans.size() <= params){
				datatrans.add(format);
			} else {
				datatrans.set(params,format);
			}
		} else if(datatrans.size()==multiple){
			datatrans.set(params,format);
		} else {
			return false;
		}
		String separator = pin.getString(7).toString();
		int total = datatrans.size() * separator.length();
		for(String s : datatrans){
			total += s.length();
		}
		
		for(String s : datatrans){
			sb.append(separator).append(s);
		}
		hasil = sb.substring(separator.length());
		value = hasil+"."+pin.getString(5).toString();
		return true;
	}
	
	private void tampilDataOp(int iddistributor2){
		id_user = sp.getInt("id_user", 0);
		List<String> op = dsTrans.fetchOperator(id_user,iddistributor2);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, op);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setPrompt("Pilih Operator");
		spinner1.setAdapter(dataAdapter);
	}
	
	private void tampilNominal(){
		id_user = sp.getInt("id_user", 0);
		List<SpinnerObject> data = dsTrans.fetchNominal(spinner1.getSelectedItem().toString(), id_user, iddistributor);
		ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<SpinnerObject>(this, android.R.layout.simple_spinner_item,data);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
	}
	
	@SuppressWarnings("deprecation")
	private void loadDataSpinner(){
		Cursor cr = dsTrans.fetchdistributor();
		String[] from = new String[]{"nm_distributor"};
		int[] to = new int[]{android.R.id.text1};
		SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cr, from, to);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(mAdapter);
	}
	
	private void idOtomatis() {
		Random r = new Random();
		int i = r.nextInt(999-100)+100;
		String date_now = (String) DateFormat.format("yyyyMMdd", new Date());
		txtIdTrans.setText("TR"+date_now.toString()+String.valueOf(i).toString());
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int posisi,
			long arg3) {
		// TODO Auto-generated method stub
		spinner2.setEnabled(true);
		tampilNominal();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void addItem() {
		TableLayout tb = (TableLayout) findViewById(R.id.tl);
		
		for(int i=1; i<=multiple; i++){
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(25, 0, 0, 10);
        	ed = new EditText(this);
        	ed.setId(i);
        	ed.setWidth(145);
        	ed.setEnabled(false);
        	ed.setInputType(InputType.TYPE_CLASS_NUMBER);
        	listED.add(ed);
        	
        	TextView txt = new TextView(this);
        	ib = new ImageView(this);
        	
        	txt.setText("No Tujuan "+String.valueOf(i).toString()+" :");
        	ib.setId(i);
        	ib.setImageResource(R.drawable.ic_add_person_black);
        	
        	ib.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(v.getId() == 1){
						tampilDataPembeli(v.getId()-1);
					} else if(v.getId() != 1 && datatrans.size() == 0){
						Toast.makeText(getBaseContext(),"Tambah sesuai urutan", Toast.LENGTH_SHORT).show();
					} else if(v.getId() != 1 && datatrans.size() == multiple){
						tampilDataPembeli(v.getId()-1);
					} else if(v.getId() != 1 && v.getId() - datatrans.size() == 0){
						tampilDataPembeli(v.getId()-1);
					} else if(v.getId() != 1 && v.getId() - datatrans.size() != 1){
						Toast.makeText(getBaseContext(),"Tambah sesuai urutan", Toast.LENGTH_SHORT).show();
					} else {
						tampilDataPembeli(v.getId()-1);
					}
				}
        		
        	});
        	
        	tableRow.addView(txt);
        	tableRow.addView(ed);
        	tableRow.addView(ib);
        	ed.setLayoutParams(params);
        	tb.addView(tableRow);
        }
    }
	
	@SuppressWarnings("deprecation")
	public int tampilDataPembeli(final int id_click){
		AlertDialog.Builder myDialog = new AlertDialog.Builder(TransaksiActivity.this);
		editText = new EditText(TransaksiActivity.this);
		editText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_color, 0, 0, 0);
		editText.setHint("Cari nama pembeli");
		editText.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				Cursor crs = dsPembeli.fetchSearchPembeli(s.toString());
				SimpleCursorAdapter sca = new SimpleCursorAdapter(TransaksiActivity.this, android.R.layout.simple_list_item_1, crs, new String[] { "nm_pembeli" }, new int[] { android.R.id.text1 });
				list.setAdapter(sca);
				
				list.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						myalertDialog.dismiss();
						idNo_ = String.valueOf(id);
						tampilDataNomor(idNo_, id_click);
					}
					
				});
				
			}
			
		});
		
		list = new ListView(TransaksiActivity.this);
		layout = new LinearLayout(TransaksiActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(editText);
		layout.addView(list);
		myDialog.setView(layout);

		Cursor cr = dsPembeli.fetchPembeli();
		SimpleCursorAdapter sca = new SimpleCursorAdapter(TransaksiActivity.this, android.R.layout.simple_list_item_1, cr, new String[] { "nm_pembeli" }, new int[] { android.R.id.text1 });
		list.setAdapter(sca);
		list.setTextFilterEnabled(true);
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				myalertDialog.dismiss();
				idNo_ = String.valueOf(id).toString();
				tampilDataNomor(idNo_, id_click);
			}
			
		});
		
		myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

		myalertDialog = myDialog.show();
		return id_click;
	}
	
	public String tampilDataNomor(String idNo_, final int id_click){
		AlertDialog.Builder myDialog = new AlertDialog.Builder(TransaksiActivity.this);
		myDialog.setTitle("Pilih Nomor :")
				.setIcon(R.drawable.ic_dial_pad);
		layout = new LinearLayout(TransaksiActivity.this);
		layout.setOrientation(LinearLayout.VERTICAL);	
		rG = new RadioGroup(this);
		
		final List<AutoObject> dataNo = dsPembeli.fetchNomPembeliId(Integer.valueOf(idNo_));
		int i;
		if(dataNo.size() == 0){
			TextView text = new TextView(this);
			text.setPadding(10, 10, 10, 10);
			text.setTextSize(17);
			text.setText("Data nomor kosong");
			rG.addView(text);
		} else {
			for(i=0; i<dataNo.size(); i++){
				rB = new RadioButton(this);
				rB.setId(i);
				rB.setText(dataNo.get(i).getDtNomor().toString());
				idno = dataNo.get(i).getDtidNomor().toString();
				rG.addView(rB);
				rG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(RadioGroup rg, int checkedId) {
						// TODO Auto-generated method stub
						ArrayList<RadioButton> listRB = new ArrayList<RadioButton>();
						for(int x=0; x<rG.getChildCount(); x++){
							View vo = rG.getChildAt(x);
							if(vo instanceof RadioButton){
								listRB.add((RadioButton)vo);
							}
						}
						String no = listRB.get(checkedId).getText().toString();
						listED.get(id_click).setTextColor(Color.parseColor("#000000"));
						listED.get(id_click).setText(no);
						params = id_click;
						String idchecked = dataNo.get(checkedId).getDtidNomor().toString();
						dataTransaksi();
						try {
							prosesTransaksi(params, idchecked);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Log.v("JSON", "Tambah Sukses");
						myalertDialog.dismiss();
					}
				});
			}
		}
		
		
		layout.addView(rG);
		myDialog.setView(layout);
		
		myDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

		myalertDialog = myDialog.show();
		return idNo_;
	}

}