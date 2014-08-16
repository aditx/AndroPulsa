package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsLogin {
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_agen = "tb_agen";
	private static final String tb_user = "tb_user";
	//private static final String id_agen = "id_agen";
	private static final String Username = "username";
	private static final String Password = "password";
	
		//Instance from constructor
		public DsLogin(Context context) {
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
		
		public Cursor fetchLogin(String username, String password){
			String selectQuery = "SELECT tb_agen.username, tb_agen.password, tb_agen.nm_agen, tb_agen.id_agen FROM " + tb_agen + " WHERE "
					+ Username + " = '" + username + "' AND " + Password + " = '" + password + "'";
			Cursor cr = database.rawQuery(selectQuery, null);
			if(cr != null){
				cr.moveToFirst();
			}
			return cr;
		}
		
}
