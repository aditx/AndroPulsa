package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsProfile {
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_agen = "tb_agen";
	private static final String tb_user = "tb_user";
	private static final String id_agen = "id_agen";
	private static final String id_user = "id_user";
	
	//Instance from constructor
	public DsProfile(Context context) {
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
	
	public Cursor fetchDataAgen(){
		String query = "SELECT * FROM tb_agen";
		Cursor cr = database.rawQuery(query, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
		
	public Cursor fetchProfile(int iduser){
		String selectQuery = "SELECT tb_agen.nm_agen, tb_agen.username, tb_agen.password, tb_agen.telpon, tb_agen.id_agen, tb_user.id_user " +
								"FROM " + tb_agen + "," + tb_user + " " +
									"WHERE " + tb_agen + "." + id_agen + " = " + tb_user + "." + id_agen + " AND " + id_user + " = " + iduser + "";
		Cursor cr = database.rawQuery(selectQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
		
	public Cursor updateProfile(String nm_agen, String username, String password, String telpon, int idagen){
		String updateQuery = "UPDATE "+ tb_agen +" SET nm_agen='"+ nm_agen +"',username='"+ username +"',password='"+ password + "',telpon='"+ telpon +"" +
								"' WHERE "+ id_agen +" = "+ idagen +"";
		Cursor cr = database.rawQuery(updateQuery, null);
		if(cr != null){
			cr.moveToFirst();
		}
		return cr;
	}
		
}