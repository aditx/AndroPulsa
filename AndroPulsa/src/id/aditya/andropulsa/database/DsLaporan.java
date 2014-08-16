package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsLaporan {
	
	private SQLiteDatabase database;
	private DbManager dbManager;
	
	public DsLaporan(Context context) {
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
	
	public Cursor fetchLaporan(String awal, String akhir, String status){
		String query = "SELECT t.tanggal as tanggal, p.nm_pembeli, n.nomor_telpon as nomor, op.nominal as nominal, t.status as status, op.hrg_jual as harga " +
				"FROM tb_data_op op, tb_data_nomor n, tb_transaksi t, tb_data_pembeli p " +
				"WHERE t.id_op = op.id_op AND t.id_nomor = n.id_nomor AND t.id_pembeli = p.id_pembeli AND t.status='"+ status +"' AND t.tanggal >= Date('"+ awal +"') AND t.tanggal <= Date('"+ akhir +"')";
		Cursor cr = database.rawQuery(query, null);
		
		return cr;
	}

}
