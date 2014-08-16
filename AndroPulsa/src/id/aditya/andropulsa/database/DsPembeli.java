package id.aditya.andropulsa.database;

import java.util.ArrayList;
import java.util.List;

import id.aditya.andropulsa.kelas.AutoObject;
import id.aditya.andropulsa.kelas.ClassPembeli;
import id.aditya.andropulsa.kelas.DataObject;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DsPembeli {
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_data_pembeli = "tb_data_pembeli";
	private static final String id_pembeli = "id_pembeli";
	private static final String id_pembelix = "id_pembeli as _id";
	private static final String nm_pembeli = "nm_pembeli";
	private static final String alamat_pembeli = "alamat_pembeli";
	
	private static final String tb_data_nomor = "tb_data_nomor";
	private static final String id_nomor = "id_nomor";
	private static final String nomor_telpon = "nomor_telpon";
	
	private String[] allColumns = {id_pembelix,nm_pembeli,alamat_pembeli};
	private String[] allColumns1 = {id_nomor,id_pembeli,nomor_telpon};
	
	//Instance from constructor
	public DsPembeli(Context context) {
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
	
	public ClassPembeli insertPembeli(String Nm_pembeli, String Alamat_pembeli){
		ContentValues values = new ContentValues();
		values.put(nm_pembeli, Nm_pembeli);
		values.put(alamat_pembeli, Alamat_pembeli);
		
		long insertIdPembeli = database.insert(tb_data_pembeli, null, values);
		Cursor cursor = database.query(tb_data_pembeli, allColumns, id_pembeli + " = " + insertIdPembeli, null, null, null, null);
		cursor.moveToFirst();
		
		ClassPembeli returnidpembeli = cursorToIdPembeli(cursor);
		cursor.close();
		return returnidpembeli;
	}

	private ClassPembeli cursorToIdPembeli(Cursor cursor) {
		// TODO Auto-generated method stub
		ClassPembeli pembeli = new ClassPembeli();
		pembeli.setId_pembeli(cursor.getInt(0));
		return pembeli;
	}
	
	public long insertNo(int Idpembeli, String No){
		ContentValues values = new ContentValues();
		values.put(id_pembeli, Idpembeli);
		values.put(nomor_telpon, No);
		long cr = database.insert(tb_data_nomor, null, values);
		return cr;
	}
	
	public Cursor fetchPembeli(){
		Cursor cr = database.query(true, tb_data_pembeli, allColumns, null, null, null, null, nm_pembeli+" ASC", null);
		
		return cr;
	}
	
	public Cursor fetchPembeliId(int idpembeli){
		Cursor cr = database.query(true, tb_data_pembeli, allColumns, id_pembeli + "=?", new String[] {""+idpembeli+""}, null, null, null, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public List<String> fetchNomPembeli(int idpembeli){
		List<String> data = new ArrayList<String>();
		Cursor cr = database.query(true, tb_data_nomor, allColumns1, id_pembeli + "=?", new String[] {""+idpembeli+""}, null, null, id_nomor+" ASC", null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			data.add(cr.getString(2));
		}
		return data;
	}
	
	public List<AutoObject> fetchNomPembeliId(int idNo_){
		List<AutoObject> nm = new ArrayList<AutoObject>();

		Cursor cr = database.query(true, tb_data_nomor, allColumns1, id_pembeli + "=?", new String[] {""+idNo_+""}, null, null, id_nomor+" ASC", null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			//nm.add(cr.getString(0));
			nm.add ( new AutoObject ( cr.getString(0).toString() , cr.getString(2).toString() ) );
		}
		
		return nm;
	}
	
	public int deleteNoPembeli(int idnomor){
		int delete = database.delete(tb_data_nomor, id_nomor + " = " + idnomor, null);
		
		return delete;
	}
	
	public int deleteNoPembeliId(int idpembeli){
		int delete = database.delete(tb_data_nomor, id_pembeli + " = " + idpembeli, null);
		
		return delete;
	}
	
	public int deletePembeli(int idpembeli){
		int delete = database.delete(tb_data_pembeli, id_pembeli + " = " + idpembeli, null);
		
		return delete;
	}
	
	public Cursor updateDataPembeli(String nmPembeli, String almtPembeli, int idPembeli){
		String updateQuery = "UPDATE "+ tb_data_pembeli +" SET nm_pembeli='"+ nmPembeli +"',alamat_pembeli='"+ almtPembeli +
				"' WHERE "+ id_pembeli +" = "+ idPembeli +"";
		Cursor cr = database.rawQuery(updateQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public Cursor updateDataNoPembeli(String noTelp, int idNomor){
		String updateQuery = "UPDATE "+ tb_data_nomor +" SET nomor_telpon='"+ noTelp +"' WHERE "+ id_nomor +" = "+ idNomor +"";
		Cursor cr = database.rawQuery(updateQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public List<DataObject> fetchDataPembeli(){
		List<DataObject> data = new ArrayList<DataObject>();
		
		Cursor cr = database.query(true, tb_data_pembeli, allColumns, null, null, null, null, nm_pembeli+" ASC", null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			data.add ( new DataObject ( cr.getString(0).toString() , cr.getString(1).toString() ) );
		}
		return data;
	}
	
	public Cursor fetchSearchPembeli(String cons){
		
		Cursor cr = database.query(true, tb_data_pembeli, allColumns, nm_pembeli + " LIKE ?", new String[] {"%"+cons+"%"}, null, null, nm_pembeli+" ASC", null);
		
		return cr;
		
	}
	
	public Cursor CountPembayaran(){
		String query =	"SELECT DISTINCT p.id_pembeli, p.nm_pembeli, SUM(o.hrg_jual) as total, "+
							"SUM(CASE WHEN t.status = '1' THEN o.hrg_jual END) as piutang, "+
						"FROM tb_transaksi t, tb_data_op o, tb_data_pembeli p "+
						"WHERE t.id_op = o.id_op AND t.id_pembeli = p.id_pembeli "+
						"GROUP BY t.id_pembeli "+
						"ORDER BY p.nm_pembeli ASC";
		Cursor cr = database.rawQuery(query, null);
		return cr;
	}
	
	public List<String> countPembelian(){
		List<String> data1 = new ArrayList<String>();
		List<String> data2 = new ArrayList<String>();
		
		String query1 = "SELECT COUNT(*) as total FROM tb_data_pembeli";
		Cursor cr1 = database.rawQuery(query1, null);
		cr1.moveToFirst();
		int total = cr1.getInt(0);
		
		String query2 = "SELECT id_pembeli FROM tb_data_pembeli ORDER BY nm_pembeli ASC";
		Cursor cr2 = database.rawQuery(query2, null);
		
		for(cr2.moveToFirst(); !cr2.isAfterLast(); cr2.moveToNext()) {
			data1.add(String.valueOf(cr2.getInt(0)));
		}
		
		try{
			for(int i = 0; i<total; i++){
				String query3 = "SELECT SUM(op.hrg_jual) FROM tb_data_op op, tb_transaksi tr WHERE op.id_op = tr.id_op AND tr.id_pembeli = "+ Integer.valueOf(data1.get(i).toString()) +"";
				Cursor cr3 = database.rawQuery(query3, null);
				cr3.moveToFirst();
				data2.add(i, String.valueOf(cr3.getInt(0)));
			}
		} catch(Exception e){
			Log.v("Error", e.toString());
		}
		
		return data2;
	}
	
	public List<String> countPiutang(){
		List<String> data1 = new ArrayList<String>();
		List<String> data2 = new ArrayList<String>();
		
		String query1 = "SELECT COUNT(*) as total FROM tb_data_pembeli";
		Cursor cr1 = database.rawQuery(query1, null);
		cr1.moveToFirst();
		int total = cr1.getInt(0);
		
		String query2 = "SELECT id_pembeli FROM tb_data_pembeli ORDER BY nm_pembeli ASC";
		Cursor cr2 = database.rawQuery(query2, null);
		
		for(cr2.moveToFirst(); !cr2.isAfterLast(); cr2.moveToNext()) {
			data1.add(String.valueOf(cr2.getInt(0)));
		}
		
		try{
			for(int i = 0; i<total; i++){
				String query3 = "SELECT SUM(op.hrg_jual) FROM tb_data_op op, tb_transaksi tr WHERE op.id_op = tr.id_op AND tr.status='1' AND tr.id_pembeli = "+ Integer.valueOf(data1.get(i).toString()) +"";
				Cursor cr3 = database.rawQuery(query3, null);
				cr3.moveToFirst();
				data2.add(i, String.valueOf(cr3.getInt(0)));
			}
		} catch(Exception e){
			Log.v("Error", e.toString());
		}
		
		return data2;
	}
	
}
