package id.aditya.andropulsa.database;

import java.util.ArrayList;
import java.util.List;

import id.aditya.andropulsa.kelas.ClassRegistrasi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DsOperator {

	private SQLiteDatabase database;
	private DbManager dbManager;
	public static final String tb_data_op = "tb_data_op";
	public static final String id_op = "id_op";
	public static final String id_agen = "id_agen";
	public static final String nm_op = "nm_op";
	public static final String kode_op = "kode_op";
	public static final String nominal = "nominal";
	public static final String hrg_beli = "hrg_beli";
	public static final String hrg_jual = "hrg_jual";
	
	private static final String tb_distributor = "tb_distributor";
	private static final String id_distributor = "id_distributor";
	
	private String[] allColumns = {id_op,id_agen,nm_op,kode_op,nominal,hrg_beli,hrg_jual,id_distributor};
	//private String[] allColumns2 = {id_op, id_user, nm_op, kode_op, hrg_jual, hrg_beli, nominal, id_distributor};
	
	//Instance from constructor
	public DsOperator(Context context) {
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
	
	//method for insert tb_user
	public ClassRegistrasi insert_tb_data_op(int Id_user, String Nm_op, String Kode_op, 
											String Nominal, int Hrg_beli, int Hrg_jual, int iddistributor) {
		ContentValues values = new ContentValues();
		values.put(id_agen, Id_user);
		values.put(nm_op, Nm_op);
		values.put(kode_op, Kode_op);
		values.put(nominal, Nominal);
		values.put(hrg_beli, Hrg_beli);
		values.put(hrg_jual, Hrg_jual);
		values.put(id_distributor, iddistributor);
		
		long insertIdOp = database.insert(tb_data_op, null, values);
		Cursor cursor = database.query(tb_data_op, allColumns, id_op + " = " + insertIdOp, null, null, null, null);
		cursor.moveToFirst();
				
		ClassRegistrasi returnidop = cursorToIdOp(cursor);
		cursor.close();
		
		return returnidop;
			
	}

	private ClassRegistrasi cursorToIdOp(Cursor cursor) {
		// TODO Auto-generated method stub
		ClassRegistrasi registrasi = new ClassRegistrasi();
		Log.v("info", "IdOp :"+ cursor.getInt(0) +" IdUser :"+ cursor.getInt(1));
		Log.v("info", "Nm_op :"+ cursor.getString(2) +","+ cursor.getString(3) +"," + cursor.getString(4));
		Log.v("info", "Hrg_beli :"+ cursor.getInt(5) +" Hrg_jual :"+ cursor.getInt(6));
		
		registrasi.setId_op(cursor.getInt(0));
		
		//return as object registration
		return registrasi;
	}
	
	public Cursor fetchOperator(int iduser){
		//Cursor cr = database.query(true, tb_data_op, allColumns2, id_user + "=?", new String[] {""+iduser+""}, null, null, nm_op+" DESC", null);
		String query = "SELECT op.id_op, op.id_agen, op.nm_op, op.kode_op, op.hrg_jual, op.hrg_beli, op.nominal, op.id_distributor, tmp.row_id FROM tb_data_op op, tb_view2 tmp WHERE op.id_distributor=tmp._id";
		Cursor cr = database.rawQuery(query, null);
		return cr;
	}
	
	public Cursor fetchOperatorCustom(int iduser, int iddist){
		String query = "SELECT op.id_op, op.id_agen, op.nm_op, op.kode_op, op.hrg_jual, op.hrg_beli, op.nominal, op.id_distributor, tmp.row_id FROM tb_data_op op, tb_view2 tmp WHERE op.id_distributor=tmp._id AND op.id_distributor = "+ iddist +"";
		Cursor cr = database.rawQuery(query, null);
		return cr;
	}
	
	public Cursor fetchTemp(int id){
		String query = "SELECT row_id FROM tb_view2 WHERE _id = "+ id +"";
		Cursor cr = database.rawQuery(query, null);
		cr.moveToFirst();
		return cr;
	}
	
	public Cursor updateOperator(String nmop, String kodeop, String nominal, int hrgbeli, int hrgjual, int idop, int iddistributor){
		String updateQuery = "UPDATE "+ tb_data_op +" SET nm_op='"+ nmop +"',kode_op='"+ kodeop +"',nominal='"+ nominal + "',hrg_beli='"+ hrgbeli +"',hrg_jual='"+ hrgjual +"" +
				"',id_distributor='"+ iddistributor +"' WHERE "+ id_op +" = "+ idop +"";
		Cursor cr = database.rawQuery(updateQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public int deleteOperator(int idop){
		int delete = database.delete(tb_data_op, id_op + " = " + idop, null);
		
		return delete;
	}
	
	public Cursor filterOperator(String search, int iduser){
		//Cursor cr = database.query(true, tb_data_op, allColumns2, nm_op + " LIKE ?" + " AND " + id_user + " LIKE ?", new String[] {"%"+search+"%", "%"+iduser+"%"}, null, null, null, null);
		String query = "SELECT op.id_op, op.id_agen, op.nm_op, op.kode_op, op.hrg_jual, op.hrg_beli, op.nominal, op.id_distributor, tmp.row_id FROM tb_data_op op, tb_view2 tmp WHERE op.id_distributor=tmp._id AND op.nm_op LIKE '%"+ search +"%'";
		Cursor cr = database.rawQuery(query, null);
		return cr;
	}
	
	public List<String> fetchOperatorAuto(int iduser){
		List<String> data = new ArrayList<String>();
		Cursor cr = database.query(true, tb_data_op, new String[] {nm_op}, id_agen + "=?", new String[] {""+iduser+""}, null, null, nm_op+" DESC", null);
		for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()) {
			data.add(cr.getString(0));
		}
		return data;
	}
	
	public Cursor filterKodeOperator(int kode){
		Cursor cr = database.query(true, tb_data_op, allColumns, id_op + "=?", new String[] {""+kode+""}, null, null, null, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public Cursor fetchdistributor(){
		String selectQuery = "SELECT id_distributor as _id, nm_distributor FROM " + tb_distributor + "";
		Cursor cr = database.rawQuery(selectQuery, null);
		
		return cr;
	}
	
	public Cursor fetchdist(){
		String selectQuery = "SELECT * FROM tb_distributor";
		Cursor cr = database.rawQuery(selectQuery, null);
		return cr;
	}
	
	public Cursor pilihIdDistributor(int id){
		String query = "SELECT _id FROM tb_view2 WHERE row_id = "+ id +"";
		Cursor cr = database.rawQuery(query, null);
		cr.moveToFirst();
		return cr;
	}

}
