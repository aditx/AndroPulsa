package id.aditya.andropulsa.database;

import id.aditya.andropulsa.kelas.ClassRegistrasi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DsRegistrasi {

	private SQLiteDatabase database;
	private DbManager dbManager;
	//Table Agent
	private static final String tb_agen = "tb_agen";
	private static final String id_agen = "id_agen";
	private static final String nm_agen = "nm_agen";
	private static final String username = "username";
	private static final String password = "password";
	private static final String telpon = "telpon";
	//Table User
	private static final String tb_user= "tb_user";
	private static final String id_user = "id_user";
	//Second variables
	private static final String Nm_agen = "nm_agen";
	private static final String Username = "username";
	private static final String Password = "password";
	private static final String Telpon = "telpon";
	private static final String Id_agen = "id_agen";
	
	private String[] allColumns1 = {id_agen,nm_agen,username,password,telpon};
	private String[] allColumns2 = {id_user,id_agen};
	
	//Instance from constructor
	public DsRegistrasi(Context context) {
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
	
	//method for insert tb_agen
	public ClassRegistrasi insert_tb_agen(String nm_agen, String username, String password, String telpon){
		ContentValues values = new ContentValues();
		values.put(Nm_agen, nm_agen);
		values.put(Username, username);
		values.put(Password, password);
		values.put(Telpon, telpon);

			long insertIdAgen = database.insert(tb_agen, null, values);
			Cursor cursor = database.query(tb_agen, allColumns1, id_agen + " = " + insertIdAgen, null, null, null, null);
			cursor.moveToFirst();
			
			ClassRegistrasi returnidagen = cursorToIdAgen(cursor);
			cursor.close();
			
			return returnidagen;
		
	}
	
	private ClassRegistrasi cursorToIdAgen(Cursor cursor) {
		// TODO Auto-generated method stub
		ClassRegistrasi registrasi = new ClassRegistrasi();
		Log.v("info", "Id Agen :"+ cursor.getInt(0));
		Log.v("info", "Data Agen :"+ cursor.getString(1) +","+ cursor.getString(2) +"," + cursor.getString(3) +","+ cursor.getString(4));
		
		registrasi.setId_agen(cursor.getInt(0));
		
		//return as object registration
		return registrasi;
	}
	
	//method for insert tb_user
	public ClassRegistrasi insert_tb_user(int id_agen) {
		ContentValues values = new ContentValues();
		values.put(Id_agen, id_agen);
		
			long insertIdUser = database.insert(tb_user, null, values);
			Cursor cursor = database.query(tb_user, allColumns2, id_user + " = " + insertIdUser, null, null, null, null);
			cursor.moveToFirst();
			
			ClassRegistrasi returniduser = cursorToIdUser(cursor);
			cursor.close();
			
			return returniduser;
	}

	private ClassRegistrasi cursorToIdUser(Cursor cursor) {
		// TODO Auto-generated method stub
		ClassRegistrasi registrasi = new ClassRegistrasi();
		Log.v("info", "Id User:"+ cursor.getInt(0));
		
		registrasi.setId_user(cursor.getInt(0));
		
		return registrasi;
	}
	
}
