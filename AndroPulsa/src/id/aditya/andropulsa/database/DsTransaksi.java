package id.aditya.andropulsa.database;

import id.aditya.andropulsa.kelas.SpinnerObject;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsTransaksi {

	private SQLiteDatabase database;
	private DbManager dbManager;
	public static final String tb_data_op = "tb_data_op";
	public static final String id_op = "id_op";
	public static final String id_agen = "id_agen";
	public static final String nm_op = "nm_op";
	public static final String kode_op = "kode_op";
	public static final String nominal = "nominal";
	
	private static final String tb_transaksi = "tb_transaksi";
	private static final String id_transaksi = "id_transaksi";
	private static final String id_pembeli = "id_pembeli";
	private static final String id_nomor = "id_nomor";
	private static final String tanggal = "tanggal";
	private static final String status = "status";
	private static final String id_distributor = "id_distributor";
	
	//private String[] allColumns = {id_transaksi,id_op,id_agen,id_pembeli,id_nomor,tanggal,status};
	
	//Instance from constructor
	public DsTransaksi(Context context) {
		dbManager = new DbManager(context);
	}
			
	//Open database connection
	public void open() throws SQLException {
		database = dbManager.getWritableDatabase();
	}
			
	//close database connection
	public void close(){
		dbManager.close();
	}
	
	public List<String> fetchOperator(int iduser, int iddist){
		List<String> op = new ArrayList<String>();
		
		Cursor cr = database.query(true, tb_data_op, new String[] {nm_op}, id_agen + "=?" + " AND " + id_distributor + " LIKE ?", new String[] {""+iduser+"",""+iddist+""}, null, null, nm_op+" DESC", null);
		
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			op.add(cr.getString(0));
		}
		
		return op;
	}
	
	public List<SpinnerObject> fetchNominal(String nama_op, int iduser, int iddist){
		List<SpinnerObject> nm = new ArrayList<SpinnerObject>();

		Cursor cr = database.query(true, tb_data_op, new String[] {id_op, nominal}, nm_op + "=?" + " AND " + id_agen + " LIKE ?" + " AND " + id_distributor + " LIKE ?", new String[] {""+nama_op+"",""+iduser+"",""+iddist+""}, null, null, nm_op+" DESC", null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			//nm.add(cr.getString(0));
			nm.add ( new SpinnerObject ( cr.getString(0).toString() , cr.getString(1).toString() ) );
		}
		
		return nm;
	}
	
	public long insert_tb_transaksi(String idtransaksi, int idop, int iduser, int idpembeli, int idno, String tgl, int stats){
		ContentValues values = new ContentValues();
		values.put(id_transaksi, idtransaksi);
		values.put(id_op, idop);
		values.put(id_agen, iduser);
		values.put(id_pembeli, idpembeli);
		values.put(id_nomor, idno);
		values.put(tanggal, tgl);
		values.put(status, stats);
				
		long cr = database.insert(tb_transaksi, null, values);
		return cr;
	}
	
	public Cursor fetchdistributor(){
		String selectQuery = "SELECT id_distributor as _id, nm_distributor FROM tb_distributor";
		Cursor cr = database.rawQuery(selectQuery, null);
		
		return cr;
	}
	
}
