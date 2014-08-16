package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DsReset {
	private SQLiteDatabase database;
	private DbManager dbManager;
	private static final String tb_agen = "tb_agen";
	private static final String Username = "username";
	
		//Instance from constructor
		public DsReset(Context context) {
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
			
		public Cursor fetchReset(String username){
			String selectQuery = "SELECT username, password, telpon FROM " + tb_agen + " WHERE "
					+ Username + " = '" + username + "'";
			Cursor cr = database.rawQuery(selectQuery, null);
			if(cr != null){
				cr.moveToFirst();
			}
			return cr;
		}
}
