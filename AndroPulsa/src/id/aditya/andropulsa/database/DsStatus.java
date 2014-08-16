package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsStatus {
	
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_transaksi = "tb_transaksi";
	private static final String _id = "_id";
	
	public DsStatus(Context context) {
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
	
	public Cursor fetchStatus(){
		String query = "SELECT op.hrg_jual, n.nomor_telpon, t.tanggal, t.id_transaksi, t.status, p.nm_pembeli, t._id " +
						"FROM tb_data_op op, tb_data_nomor n, tb_transaksi t, tb_data_pembeli p " +
						"WHERE t.id_op = op.id_op AND t.id_nomor = n.id_nomor AND t.id_pembeli=p.id_pembeli AND t.status='1' "+
						"ORDER BY _id DESC";
		Cursor cr = database.rawQuery(query, null);
		
		return cr;
	}
	
	public Cursor updateStatus(int status, int id){
		String update = "UPDATE tb_transaksi SET status = "+ status +" WHERE _id = "+ id +"";
		Cursor cr = database.rawQuery(update, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
	
	public void deleteStatus(int id){
		database.delete(tb_transaksi, _id + " = " + id, null);
	}
	
}
