package id.aditya.andropulsa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

	private static final String db_name = "db_pulsa.db";
	private static final int db_version = 1;
	
	// create table agent
	public static final String tb_agen = "tb_agen";
	public static final String id_agen = "id_agen";
	public static final String nm_agen = "nm_agen";
	public static final String username = "username";
	public static final String password = "password";
	public static final String telpon = "telpon";
	public static final String id_distributor = "id_distributor";
	public static final String pin = "pin";
	private static final String create_tb_agen = "create table "
			+ tb_agen + "("
			+ id_agen + " integer primary key autoincrement,"
			+ nm_agen + " varchar(25) not null,"
			+ username + " varchar(10) not null,"
			+ password + " varchar(15) not null,"
			+ telpon + " varchar(15) not null);";
	
	// create table user
	//public static final String tb_user = "tb_user";
	//public static final String id_user = "id_user";
	//public static final String id_distributor = "id_distributor";
	//public static final String pin = "pin";
	//private static final String create_tb_user = "create table "
			//+ tb_user + "("
			//+ id_user + " integer primary key autoincrement,"
			//+ id_agen + " integer not null);";
	
	// create table distributor
	public static final String tb_distributor = "tb_distributor";
	public static final String nm_distributor = "nm_distributor";
	public static final String alamat = "alamat";
	public static final String sms_center = "sms_center";
	public static final String format_saldo = "format_saldo";
	public static final String separator = "separator";
	private static final String create_tb_distributor = "create table "
			+ tb_distributor + "("
			+ id_distributor + " integer primary key,"
			+ nm_distributor + " varchar(15) not null,"
			+ alamat + " varchar(25) null,"
			+ telpon + " varchar(15) null,"
			+ sms_center + " varchar(12) not null,"
			+ pin + " integer not null,"
			+ format_saldo + " varchar(5) null,"
			+ separator + " varchar(2) DEFAULT ',');";
	
	// create table data operator
	public static final String tb_data_op = "tb_data_op";
	public static final String id_op = "id_op";
	public static final String nm_op = "nm_op";
	public static final String kode_op = "kode_op";
	public static final String nominal = "nominal";
	public static final String hrg_beli = "hrg_beli";
	public static final String hrg_jual = "hrg_jual";
	private static final String create_tb_data_op = "create table "
			+ tb_data_op + "("
			+ id_op + " integer primary key autoincrement,"
			+ id_agen + " integer not null,"
			+ nm_op + " varchar(15) not null,"
			+ kode_op + " varchar(5) not null,"
			+ nominal + " varchar(20) not null,"
			+ hrg_beli + " integer not null,"
			+ hrg_jual + " integer not null,"
			+ id_distributor + " integer not null);";
	
	// create table pembeli
	public static final String tb_data_pembeli = "tb_data_pembeli";
	public static final String id_pembeli = "id_pembeli";
	public static final String nm_pembeli = "nm_pembeli";
	public static final String alamat_pembeli = "alamat_pembeli";
	private static final String create_tb_data_pembeli = "create table "
			+ tb_data_pembeli + "("
			+ id_pembeli + " integer primary key autoincrement,"
			+ nm_pembeli + " varchar(15) not null,"
			+ alamat_pembeli + " varchar(25) not null);";
	
	// create table data nomor
	public static final String tb_data_nomor = "tb_data_nomor";
	public static final String id_nomor = "id_nomor";
	public static final String nomor_telpon = "nomor_telpon";
	private static final String create_tb_data_nomor = "create table "
			+ tb_data_nomor + "("
			+ id_nomor + " integer primary key autoincrement,"
			+ id_pembeli + " integer not null,"
			+ nomor_telpon + " varchar(12) not null);";
	
	// create table data transaksi
	public static final String tb_transaksi = "tb_transaksi";
	public static final String _id = "_id";
	public static final String id_transaksi = "id_transaksi";
	public static final String tanggal = "tanggal";
	public static final String status = "status";
	private static final String create_tb_transaksi = "create table "
			+ tb_transaksi + "("
			+ _id + " integer primary key autoincrement,"
			+ id_transaksi + " varchar(14),"
			+ id_op + " integer not null,"
			+ id_agen + " integer not null,"
			+ id_pembeli + " integer not null,"
			+ id_nomor + " integer not null,"
			+ tanggal + " text not null,"
			+ status + " varchar(2) not null);";
	
	// create table data filter
	public static final String tb_data_filter = "tb_data_filter";
	public static final String id_filter = "id_filter";
	public static final String nomor_filter = "nomor_filter";
	private static final String create_tb_data_filter = "create table "
			+ tb_data_filter + "("
			+ id_filter + " integer primary key autoincrement,"
			+ id_distributor + " integer not null,"
			+ nomor_filter + " varchar(15) not null);";
	
	private static final String create_tb_view = "create view tb_view as select id_distributor as _id, nm_distributor FROM tb_distributor";
	private static final String create_tb_view2 = "create view tb_view2 as select COUNT(*) as row_id, q1.* from tb_view as q1 LEFT JOIN tb_view as q2 WHERE q1._id >= q2._id GROUP BY q1._id";
	
	public DbManager(Context context) {
		super(context, db_name, null, db_version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(create_tb_agen);
		//db.execSQL(create_tb_user);
		db.execSQL(create_tb_distributor);
		db.execSQL(create_tb_data_op);
		db.execSQL(create_tb_data_pembeli);
		db.execSQL(create_tb_data_nomor);
		db.execSQL(create_tb_transaksi);
		db.execSQL(create_tb_data_filter);
		db.execSQL(create_tb_view);
		db.execSQL(create_tb_view2);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists" + tb_agen);
		//db.execSQL("drop table if exists" + tb_user);
		db.execSQL("drop table if exists" + tb_distributor);
		db.execSQL("drop table if exists" + tb_data_op);
		db.execSQL("drop table if exists" + tb_data_pembeli);
		db.execSQL("drop table if exists" + tb_data_nomor);
		db.execSQL("drop table if exists" + tb_transaksi);
		db.execSQL("drop table if exists" + tb_data_filter);
		
		//create new table
		onCreate(db);
	}

}
