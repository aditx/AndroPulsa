package id.aditya.andropulsa.database;

import id.aditya.andropulsa.kelas.ClassSaldo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DsDistributor {
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_distributor = "tb_distributor";
	private static final String id_distributor = "id_distributor";
	private static final String nm_distributor = "nm_distributor";
	private static final String alamat = "alamat";
	private static final String telpon = "telpon";
	private static final String sms_center = "sms_center";
	private static final String pin = "pin";
	
	public static final String tb_data_op = "tb_data_op";
	public static final String nm_op = "nm_op";
	
	//Instance from constructor
	public DsDistributor(Context context) {
		dbManager = new DbManager(context);
	}
			
	//Open database connection
	public void open() throws SQLException {
		database = dbManager.getWritableDatabase();
	}
			
	//Close database connection
	public void close(){
		dbManager.close();
	}
	
	public Cursor fetchDistributor(int iddistributor){
		String selectQuery = "SELECT * FROM " + tb_distributor + " WHERE " + id_distributor + " = " + iddistributor + "";
		Cursor cr = database.rawQuery(selectQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public Cursor fetchDistributorAll(){
		String selectQuery = "SELECT * FROM " + tb_distributor + " ORDER BY "+ nm_distributor +" ASC";
		Cursor cr = database.rawQuery(selectQuery, null);
		
		return cr;
	}
	
	public Cursor fetchDataFormat(int id){
		String selectQuery = "SELECT format_saldo, separator FROM " + tb_distributor + " WHERE id_distributor = "+ id +"";
		Cursor cr = database.rawQuery(selectQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public List<ClassSaldo> fetchDataSaldo(){
		List<ClassSaldo> data = new ArrayList<ClassSaldo>();
		String format;
		String selectQuery = "SELECT nm_distributor, format_saldo, pin, sms_center, id_distributor FROM " + tb_distributor + "";
		Cursor cr = database.rawQuery(selectQuery, null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			if(cr.isNull(1)){
				format = "";
			} else {
				format = cr.getString(1).toString();
			}
			data.add ( new ClassSaldo ( cr.getString(0).toString() , format.toString(), cr.getInt(2), cr.getString(3), cr.getInt(4) ) );
		}
		return data;
	}
	
	public Cursor updateDataFormat(String format, String separator, int id){
		String query = "UPDATE tb_distributor SET format_saldo = '"+ format +"', separator = '" + separator + "' WHERE id_distributor = "+ id +"";
		Cursor cr = database.rawQuery(query, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public Cursor updateDistributor(String nm_distributor, String alamat, String telpon, String sms_center, int iddistributor, int pin){
		String updateQuery = "UPDATE "+ tb_distributor +" SET nm_distributor='"+ nm_distributor +"',alamat='"+ alamat +"',telpon='"+ telpon + "'," +
				"sms_center='"+ sms_center +"',pin='"+ pin +"' WHERE "+ id_distributor +" = "+ iddistributor +"";
		Cursor cr = database.rawQuery(updateQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public void deleteDistributor(int id){
		database.delete(tb_distributor, id_distributor + " = " + id, null);
		try{
			database.delete(tb_data_op, id_distributor + " = " + id, null);
		} catch(Exception e){
			//do something
		}
	}
	
	public long insert_tb_distributor(String nmdistributor, String Alamat, String telp, String sms, int Pin){
		ContentValues values = new ContentValues();
		values.put(nm_distributor, nmdistributor);
		values.put(alamat, Alamat);
		values.put(telpon, telp);
		values.put(sms_center, sms);
		values.put(pin, Pin);
				
		long cr = database.insert(tb_distributor, null, values);
		return cr;
	}
	
	public long insert_tb_distributor_new(String nmdistributor, String Alamat, String telp, String sms, int Pin){
		int id;
		//Check ID
		String query = "SELECT * FROM tb_distributor ORDER BY id_distributor DESC";
		Cursor cr = database.rawQuery(query, null);
		cr.moveToFirst();
		if(cr.getCount() != 0){
			id = cr.getInt(0) + 1;
		} else {
			id = 1;
		}
		
		try{
			ContentValues values = new ContentValues();
			values.put(id_distributor, id);
			values.put(nm_distributor, nmdistributor);
			values.put(alamat, Alamat);
			values.put(telpon, telp);
			values.put(sms_center, sms);
			values.put(pin, Pin);
					
			long cri = database.insert(tb_distributor, null, values);
			return cri;
		} catch(Exception e){
			return 0;
		}
		
	}
	
	public List<String> count_data(){
		//Proses cek id_distributor
		List<String> data1 = new ArrayList<String>();
		List<String> data2 = new ArrayList<String>();
		String query1 = "SELECT id_distributor FROM tb_distributor ORDER BY nm_distributor ASC";
		Cursor cr1 = database.rawQuery(query1, null);
		
		//Proses hitung jumlah id_distributor
		String query2 = "SELECT COUNT(*) as total FROM tb_distributor";
		Cursor cr2 = database.rawQuery(query2, null);
		cr2.moveToFirst();
		int total = cr2.getInt(0);
		
		for(cr1.moveToFirst(); !cr1.isAfterLast(); cr1.moveToNext()) {
			data1.add(String.valueOf(cr1.getInt(0)));
		}
		
		try{
			for(int i=0; i<total; i++){
				String query3 = "SELECT COUNT(*) FROM tb_data_op WHERE id_distributor = "+ Integer.valueOf(data1.get(i).toString()) +"";
				Cursor cr3 = database.rawQuery(query3, null);
				cr3.moveToFirst();
				data2.add(i, String.valueOf(cr3.getInt(0)));
				Log.v("Sukses", "Sukses");
			}
		} catch(Exception e){
			Log.v("Error", e.toString());
		}
		
		return data2;
	}
	
}
