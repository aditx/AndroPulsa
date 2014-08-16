package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsBroadcast {
	
	private SQLiteDatabase database;
	private DbManager dbManager;
	
	public DsBroadcast(Context context) {
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
	
	public Cursor fetchDataBelumLunas(){
		String query =	"SELECT * FROM ("+
							"SELECT DISTINCT p.id_pembeli, p.nm_pembeli as pembeli, n.nomor_telpon as telpon, SUM(o.hrg_jual) as total, "+
								"COUNT(t.id_pembeli) as jumlah, "+
								"SUM(CASE WHEN t.status = '1' THEN o.hrg_jual END) as piutang, "+
							    "COUNT(CASE WHEN t.status = '1' THEN t.id_pembeli END) as utang, "+
							    "COUNT(CASE WHEN t.status = '2' THEN t.id_pembeli END) as lunas "+
							"FROM tb_transaksi t, tb_data_op o, tb_data_pembeli p, tb_data_nomor n "+
							"WHERE t.id_op = o.id_op AND t.id_pembeli = p.id_pembeli AND t.id_nomor = n.id_nomor "+
							"GROUP BY t.id_pembeli) "+
						"WHERE utang != 0 "+
						"ORDER BY total DESC";
		Cursor cr = database.rawQuery(query, null);
		return cr;	
	}
	
	public Cursor fetchDataLunas(int bonus){
		String query =	"SELECT * FROM ("+
						"SELECT DISTINCT p.id_pembeli, p.nm_pembeli as pembeli, n.nomor_telpon as telpon, SUM(o.hrg_jual) as total, "+
							"COUNT(t.id_pembeli) as jumlah, "+
							"SUM(CASE WHEN t.status = '1' THEN o.hrg_jual END) as piutang, "+
						    "COUNT(CASE WHEN t.status = '1' THEN t.id_pembeli END) as utang, "+
						    "COUNT(CASE WHEN t.status = '2' THEN t.id_pembeli END) as lunas "+
						"FROM tb_transaksi t, tb_data_op o, tb_data_pembeli p, tb_data_nomor n "+
						"WHERE t.id_op = o.id_op AND t.id_pembeli = p.id_pembeli AND t.id_nomor = n.id_nomor "+
						"GROUP BY t.id_pembeli) "+
					"WHERE utang = 0 AND total >= "+ bonus + " "+
					"ORDER BY total DESC";
		Cursor cr = database.rawQuery(query, null);
		return cr;
	}

}
