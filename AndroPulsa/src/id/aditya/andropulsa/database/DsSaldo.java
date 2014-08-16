package id.aditya.andropulsa.database;

import id.aditya.andropulsa.kelas.AutoObject;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsSaldo {
	
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_data_filter = "tb_data_filter";
	private static final String id_filter = "id_filter";
	private static final String id_distributor = "id_distributor";
	private static final String nomor_filter = "nomor_filter";
	private String[] allColumns1 = {id_filter,id_distributor,nomor_filter};
	
	public DsSaldo(Context context) {
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
	
	public List<AutoObject> fetchNomFilterId(int id){
		List<AutoObject> nm = new ArrayList<AutoObject>();

		Cursor cr = database.query(true, tb_data_filter, allColumns1, id_distributor + "=?", new String[] {""+id+""}, null, null, id_filter+" ASC", null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			nm.add ( new AutoObject ( cr.getString(0).toString() , cr.getString(2).toString() ) );
		}
		
		return nm;
	}
	
	public long insertNo(int Iddistributor, String No){
		ContentValues values = new ContentValues();
		values.put(id_distributor, Iddistributor);
		values.put(nomor_filter, No);
		long cr = database.insert(tb_data_filter, null, values);
		return cr;
	}
	
	public Cursor updateDataNoFilter(String noTelp, int idFilter){
		String updateQuery = "UPDATE "+ tb_data_filter +" SET nomor_filter='"+ noTelp +"' WHERE "+ id_filter +" = "+ idFilter +"";
		Cursor cr = database.rawQuery(updateQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public int deleteNoFilter(int idnomor){
		int delete = database.delete(tb_data_filter, id_filter + " = " + idnomor, null);
		
		return delete;
	}
	
	public Cursor fetchDataFilter(int id){
		String selectQuery = "SELECT nomor_filter FROM tb_data_filter WHERE id_distributor = " + id + "";
		Cursor cr = database.rawQuery(selectQuery, null);
		
		return cr;
	}

}
