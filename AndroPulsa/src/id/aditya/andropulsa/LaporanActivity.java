package id.aditya.andropulsa;

import id.aditya.andropulsa.database.DsLaporan;
import id.aditya.andropulsa.kelas.ClassLaporan;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.view.SubMenu;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class LaporanActivity extends SherlockActivity implements OnClickListener {
	
	private SubMenu mItem;
	private static final int item_id = 1;
	private Spinner spinner2;
	private EditText edtTgl, edtTgl2;
	private ImageView btnSearch;
	private int click, total = 0, jml;
	private DsLaporan dslaporan;
	private TableLayout tl, th;
	private TextView txtJml, txtSaldo;
	private Cursor cr;
	private String start, end, date_now;
	
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> data1 = new ArrayList<String>();
	//java.text.DateFormat fDate = DateFormat.getDateFormat(this);
	Calendar date = Calendar.getInstance();
	
	DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int month,
				int day) {
			// TODO Auto-generated method stub
			String mDay = "", mMonth = "";
			if(day<10)
				mDay = "0"+String.valueOf(day);
			else
				mDay = String.valueOf(day);
			if((month+1)<10)
				mMonth = "0" + (month+1);
			else
				mMonth = String.valueOf((month+1));
			
			if(click == 0){
				start = String.valueOf(year)+"-"+mMonth.toString()+"-"+mDay.toString();
				edtTgl.setText(mDay.toString()+"-"+mMonth.toString()+"-"+String.valueOf(year));
			} else if(click == 1){
				end = String.valueOf(year)+"-"+mMonth.toString()+"-"+mDay.toString();
				edtTgl2.setText(mDay.toString()+"-"+mMonth.toString()+"-"+String.valueOf(year));
			}
			
		}
	};
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        ActionBar ab = getSupportActionBar();
        ab.setLogo(getResources().getDrawable(R.drawable.ic_today));
        ab.setDisplayShowTitleEnabled(true);
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1E90FF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        tl = (TableLayout)findViewById(R.id.report_table);
        th = (TableLayout)findViewById(R.id.head_table);
        date_now = (String) DateFormat.format("dd-MM-yyyy", new Date());
        edtTgl = (EditText)findViewById(R.id.edtTanggal);
        //edtTgl.setText(date_now);
        edtTgl.setClickable(true);
        edtTgl.setFocusable(false);
        edtTgl.setTextColor(Color.BLACK);
        edtTgl.setOnClickListener(this);
        //start = date_now;
        
        edtTgl2 = (EditText)findViewById(R.id.edtTanggal2);
        //edtTgl2.setText(date_now);
        edtTgl2.setClickable(true);
        edtTgl2.setFocusable(false);
        edtTgl2.setTextColor(Color.BLACK);
        edtTgl2.setOnClickListener(this);
        //end = date_now;
        
        txtJml = (TextView)findViewById(R.id.textViewTotal);
        txtSaldo = (TextView)findViewById(R.id.textViewSaldo);
        
        btnSearch = (ImageView)findViewById(R.id.imageSearch);
        btnSearch.setOnClickListener(this);
        spinner2 = (Spinner)findViewById(R.id.spinner_sts);
        data1.add("Belum Lunas");
        data1.add("Lunas");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data1);
        //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        
        dslaporan = new DsLaporan(this);
        dslaporan.open();
	}
	
	private void tampilTanggal(){
		new DatePickerDialog(LaporanActivity.this, d,
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH),
				date.get(Calendar.DAY_OF_MONTH)).show();
	}
	
	private void headerTable(){
		TableRow tr_head = new TableRow(this);
		tr_head.setBackgroundResource(R.drawable.layout_table);
		//tr_head.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0E0E6")));
		
		TextView thead_nominal = new TextView(this);
		thead_nominal.setId(10);
		thead_nominal.setText("Tanggal");
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
		thead_tanggal.setText("Nominal");
		thead_tanggal.setPadding(10, 10, 10, 10);
		thead_tanggal.setTextColor(Color.BLACK);
		tr_head.addView(thead_tanggal);
		
		TextView thead_aksi = new TextView(this);
		thead_aksi.setId(40);
		thead_aksi.setText("Status");
		thead_aksi.setPadding(10, 10, 10, 10);
		thead_aksi.setTextColor(Color.BLACK);
		tr_head.addView(thead_aksi);
		
		th.addView(tr_head, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
	}
	
	@SuppressWarnings("deprecation")
	private void displayTable(){
		
		cr = dslaporan.fetchLaporan(start, end, String.valueOf(spinner2.getSelectedItemPosition()+1));
		//Toast.makeText(getBaseContext(), String.valueOf(cr.getString(0)), Toast.LENGTH_SHORT).show();
		int count = 0;
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
			jml = cr.getCount();
			total = total + cr.getInt(5);
			
			final String tanggal = cr.getString(0);
			final String no_tujuan = cr.getString(2);
			final String nominal = cr.getString(3);
			final String status = cr.getString(4);
			//final Integer id = cr.getInt(4);
			
			String tgl = tanggal.substring(8, 10);
			String bln = tanggal.substring(5, 7);
			String thn = tanggal.substring(2, 4);
			
			TableRow tr = new TableRow(this);
			//if(count%2!=0) tr.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0E0E6")));
			tr.setId(100+count);
			tr.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			
			TextView txthrg_tanggal = new TextView(this);
			txthrg_tanggal.setId(200+count);
			txthrg_tanggal.setPadding(10, 10, 10, 10);
			txthrg_tanggal.setText(tgl+"/"+bln+"/"+thn);
			txthrg_tanggal.setTextColor(Color.BLACK);
			tr.addView(txthrg_tanggal);
			
			TextView txt_tujuan = new TextView(this);
			txt_tujuan.setId(300+count);
			txt_tujuan.setText(no_tujuan.toString().substring(0, 6)+"xxx");
			txt_tujuan.setPadding(10, 10, 10, 10);
			txt_tujuan.setTextColor(Color.BLACK);
			tr.addView(txt_tujuan);
			
			TextView txt_nominal = new TextView(this);
			txt_nominal.setId(400+count);
			txt_nominal.setText(nominal);
			txt_nominal.setPadding(10, 10, 10, 10);
			txt_nominal.setTextColor(Color.BLACK);
			tr.addView(txt_nominal);
			
			TextView txt_status = new TextView(this);
			txt_status.setId(500+count);
			if(status.equals("1") == true){
				txt_status.setText("B.L     ");
			} else if(status.equals("2") == true){
				txt_status.setText("L       ");
			}
			txt_status.setPadding(10, 10, 10, 10);
			txt_status.setTextColor(Color.BLACK);
			tr.addView(txt_status);
			
			tl.addView(tr, new TableLayout.LayoutParams(
					LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
					count++;
		}
		if(cr.getCount() != 0){
			txtJml.setText("Jumlah : "+String.valueOf(cr.getCount()));
		} else {
			txtJml.setText("Jumlah : 0");
		}
		txtSaldo.setText("Total : Rp."+String.valueOf(total));
	}
	
	@SuppressLint("InlinedApi")
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu){
		mItem = menu.addSubMenu(0, item_id, 0, null).setIcon(R.drawable.ic_social_share);
		mItem.add(0, 2, 0, "Export Exel").setIcon(R.drawable.ic_exel);
		mItem.add(0, 3, 0, "Cetak PDF").setIcon(R.drawable.ic_pdf);
		
		mItem.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		switch(item.getItemId()){
			case android.R.id.home:
				LaporanActivity.this.finish();
				break;
			case 2:
				exportCSV();
				saveExel();
				break;
			case 3:
				try {
					savePDF();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.edtTanggal:
				click = 0;
				tampilTanggal();
				break;
			case R.id.edtTanggal2:
				click = 1;
				tampilTanggal();
				break;
			case R.id.imageSearch:
				tl.removeAllViews();
				th.removeAllViews();
				headerTable();
				total = 0;
				displayTable();
				break;
		}
	}
	
	private void exportCSV(){
		File exportDir = new File(Environment.getExternalStorageDirectory(),"");
		
		if(!exportDir.exists()){
			exportDir.mkdirs();
		}
		
		File file = new File(exportDir, "Laporan_" + date_now + ".csv");
		try{
			file.createNewFile();
			CSVWriter csv = new CSVWriter(new FileWriter(file),',');
			cr = dslaporan.fetchLaporan(start, end, String.valueOf(spinner2.getSelectedItemPosition()+1));
			csv.writeNext(cr.getColumnNames());
			
			while(cr.moveToNext()){
				String sts = null;
				if(cr.getString(4).equals("1") == true){
					sts = "Belum Lunas";
				} else if(cr.getString(4).equals("2") == true) {
					sts = "Lunas";
				}
				String kolom[] = {cr.getString(0),
						cr.getString(1),
						cr.getString(2), 
						cr.getString(3), 
						sts.toString(), 
						String.valueOf(cr.getInt(5))};
				csv.writeNext(kolom);
			}
			csv.close();
			//Toast.makeText(getBaseContext(), "Berhasil Export", Toast.LENGTH_SHORT).show();
		} catch(Exception e){
			Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	private void saveExel(){
		ArrayList arrList1 = null, arrList2 = null;
		
		String inFilePath = Environment.getExternalStorageDirectory().toString()+"/Laporan_" + date_now + ".csv";
		String outFilePath = Environment.getExternalStorageDirectory().toString()+"/Laporan_" + date_now + ".xls";
		String thisLine;
		int count = 0;
		try{
			FileInputStream fi = new FileInputStream(inFilePath);
			DataInputStream di = new DataInputStream(fi);
			int i = 0;
			arrList1 = new ArrayList();
			while((thisLine = di.readLine()) != null){
				arrList2 = new ArrayList();
				String strar[] = thisLine.split(",");
				for(int j=0; j<strar.length; j++){
					arrList2.add(strar[j]);
				}
				arrList1.add(arrList2);
				System.out.println();
				i++;
			}
		} catch(Exception e){
			Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		try{
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("new sheet");
			sheet.setColumnWidth(0, 3000);
			sheet.setColumnWidth(1, 5000);
			sheet.setColumnWidth(2, 4000);
			sheet.setColumnWidth(3, 3500);
			sheet.setColumnWidth(4, 4500);
			for(int k=0; k<arrList1.size(); k++){
				ArrayList arData = (ArrayList)arrList1.get(k);
				HSSFRow row = sheet.createRow((short) 0+k);
				for(int p=0; p<arData.size(); p++){
					HSSFCell cell = row.createCell((short) p);
					String data = arData.get(p).toString();
					if(data.startsWith("=")) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if(data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(data);
					}
				}
				System.out.println();
			}
			FileOutputStream fo = new FileOutputStream(outFilePath);
			hwb.write(fo);
			fo.close();
			Toast.makeText(getBaseContext(), "File Exel berhasil dibuat", Toast.LENGTH_SHORT).show();
		} catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
	}
	
	private PdfPTable generatePDFTable() throws DocumentException {
		PdfPTable tabel = new PdfPTable(6);
		float[] columnWidths = {2.5f, 2.7f, 3f, 2.5f, 1.5f, 2.5f};
		
		PdfPCell cell1 = new PdfPCell(new Phrase("Tanggal"));
		cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tabel.addCell(cell1);
		PdfPCell cell2 = new PdfPCell(new Phrase("Nama"));
		cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tabel.addCell(cell2);
		PdfPCell cell3 = new PdfPCell(new Phrase("Telpon"));
		cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tabel.addCell(cell3);
		PdfPCell cell4 = new PdfPCell(new Phrase("Nominal"));
		cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tabel.addCell(cell4);
		PdfPCell cell5 = new PdfPCell(new Phrase("Harga"));
		cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tabel.addCell(cell5);
		PdfPCell cell6 = new PdfPCell(new Phrase("Status"));
		cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
		tabel.setWidths(columnWidths);
		tabel.addCell(cell6);
		
		tabel.setHeaderRows(1);
		
		cr = dslaporan.fetchLaporan(start, end, String.valueOf(spinner2.getSelectedItemPosition()+1));
		while(cr.moveToNext()){
			tabel.addCell(cr.getString(0));
			tabel.addCell(cr.getString(1));
			tabel.addCell(cr.getString(2));
			tabel.addCell(cr.getString(3));
			tabel.addCell(String.valueOf(cr.getInt(5)));
			if(cr.getString(4).equals("1") == true){
				tabel.addCell("Belum Lunas");
			} else if(cr.getString(4).equals("2") == true) {
				tabel.addCell("Lunas");
			}
		}

		return tabel;
	}
	
	private void savePDF() throws DocumentException, FileNotFoundException{
		ClassLaporan cl = new ClassLaporan(LaporanActivity.this, generatePDFTable(), start, end, jml, total, date_now);
		cl.execute("");
	}
	
}
