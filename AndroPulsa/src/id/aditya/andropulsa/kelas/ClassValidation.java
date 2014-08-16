package id.aditya.andropulsa.kelas;

import android.widget.EditText;

public class ClassValidation {
	
		public static String Is_Valid_username(EditText edt){
			String Vusername = null;
			if(edt.getText().toString().length() <= 0){
				edt.setError("Username wajib diisi");
			} else if(edt.getText().toString().length() > 10){
				edt.setError("Karakter melebihi batas");
			} else {
				Vusername = edt.getText().toString();
			}
			return Vusername;
		}
	
		public static String Is_Valid_nama(EditText edNama){
			String Vnama = null;
			if(edNama.getText().toString().length() <= 0){
				edNama.setError("Nama wajib diisi");
			} else if(edNama.getText().toString().length() > 25){
				edNama.setError("Karakter melebihi batas");
			} else if(!edNama.getText().toString().matches("[a-zA-Z ]+")){
				edNama.setError("Gunakan karakter huruf");
			} else {
				Vnama = edNama.getText().toString();
			}
			return Vnama;
		}
		
		public static String Is_Valid_password(EditText edPassword){
			String Vpassword = null;
			if(edPassword.getText().toString().length() <= 0){
				edPassword.setError("Password wajib diisi");
			} else if(edPassword.getText().toString().length() > 10){
				edPassword.setError("Password melebihi batas"); 
			} else {
				Vpassword = edPassword.getText().toString();
			}
			return Vpassword;
		}
		
		public static String Is_Valid_telpon(EditText edTelpon){
			String Vtelpon = null;
			if(edTelpon.getText().toString().length() <= 0){
				edTelpon.setError("No telpon wajib diisi");
			} else if(edTelpon.getText().toString().length() > 12){
				edTelpon.setError("Karakter melebihi batas"); 
			} else {
				Vtelpon = edTelpon.getText().toString();
			}
			return Vtelpon;
		}
		
		public static String Is_Valid_distributor(EditText edDistributor){
			String Vdistributor = null;
			if(edDistributor.getText().toString().length() <= 0){
				edDistributor.setError("Nama agen wajib diisi");
			} else if(edDistributor.getText().toString().length() > 15){
				edDistributor.setError("Karakter melebihi batas"); 
			} else {
				Vdistributor = edDistributor.getText().toString();
			}
			return Vdistributor;
		}
		
		public static Integer Is_Valid_pin(EditText edPin){
			String Vpin = null;
			Integer VpinInt = null;
			if(edPin.getText().toString().length() <= 0){
				edPin.setError("PIN wajib diisi");
			} else if(edPin.getText().toString().length() > 4){
				edPin.setError("PIN melebihi batas"); 
			} else if(edPin.getText().toString().length() < 4){
				edPin.setError("PIN tidak sesuai"); 
			} else {
				Vpin = edPin.getText().toString();
				VpinInt = Integer.parseInt(Vpin);
			}
			return VpinInt;
		}
		
		public static String Is_Valid_NmOp(EditText edtNmOp2){
			String Vnmop = null;
			if(edtNmOp2.getText().toString().length() <= 0){
				edtNmOp2.setError("Nama wajib diisi");
			} else if(edtNmOp2.getText().toString().length() > 10){
				edtNmOp2.setError("Karakter melebihi batas");
			} else {
				Vnmop = edtNmOp2.getText().toString();
			}
			return Vnmop;
		}
		
		public static String Is_Valid_Kode(EditText edtKode){
			String Vkode = null;
			if(edtKode.getText().toString().length() <= 0){
				edtKode.setError("Kode wajib diisi");
			} else if(edtKode.getText().toString().length() > 10){
				edtKode.setError("Karakter melebihi batas");
			} else {
				Vkode = edtKode.getText().toString();
			}
			return Vkode;
		}
		
		public static String Is_Valid_Nominal(EditText edtNominal){
			String Vnominal = null;
			if(edtNominal.getText().toString().length() <= 0){
				edtNominal.setError("Nominal wajib diisi");
			} else if(edtNominal.getText().toString().length() > 10){
				edtNominal.setError("Karakter melebihi batas");
			} else {
				Vnominal = edtNominal.getText().toString();
			}
			return Vnominal;
		}
		
		public static Integer Is_Valid_hrg_beli(EditText edtHrg_Beli){
			String Vhb = null;
			Integer VhbInt = null;
			if(edtHrg_Beli.getText().toString().length() <= 0){
				edtHrg_Beli.setError("Harga wajib diisi");
			} else if(edtHrg_Beli.getText().toString().length() > 5){
				edtHrg_Beli.setError("Angka melebihi batas"); 
			} else {
				Vhb = edtHrg_Beli.getText().toString();
				VhbInt = Integer.parseInt(Vhb);
			}
			return VhbInt;
		}
		
		public static Integer Is_Valid_hrg_jual(EditText edtHrg_Jual){
			String Vhj = null;
			Integer VhjInt = null;
			if(edtHrg_Jual.getText().toString().length() <= 0){
				edtHrg_Jual.setError("Harga wajib diisi");
			} else if(edtHrg_Jual.getText().toString().length() > 5){
				edtHrg_Jual.setError("Angka melebihi batas"); 
			} else {
				Vhj = edtHrg_Jual.getText().toString();
				VhjInt = Integer.parseInt(Vhj);
			}
			return VhjInt;
		}
		
		public static String Is_Valid_nama_pembeli(EditText edtNama){
			String Vnama = null;
			if(edtNama.getText().toString().length() <= 0){
				edtNama.setError("Nama wajib diisi");
			} else if(edtNama.getText().toString().length() > 15){
				edtNama.setError("Karakter melebihi batas");
			} else if(!edtNama.getText().toString().matches("[a-zA-Z ]+")){
				edtNama.setError("Gunakan karakter huruf");
			} else {
				Vnama = edtNama.getText().toString();
			}
			return Vnama;
		}
		
		public static String Is_Valid_alamat(EditText edtAlamat){
			String Valamat = null;
			if(edtAlamat.getText().toString().length() <= 0){
				edtAlamat.setError("Alamat wajib diisi");
			} else if(edtAlamat.getText().toString().length() > 25){
				edtAlamat.setError("Karakter melebihi batas");
			} else if(!edtAlamat.getText().toString().matches("[a-zA-Z ]+")){
				edtAlamat.setError("Gunakan karakter huruf");
			} else {
				Valamat = edtAlamat.getText().toString();
			}
			return Valamat;
		}
		
		public static String Is_Valid_no(EditText edtNo){
			String Vno = null;
			if(edtNo.getText().toString().length() <= 0){
				edtNo.setError("Nomor wajib diisi");
			} else if(edtNo.getText().toString().length() > 12){
				edtNo.setError("Karakter melebihi batas");
			} else {
				Vno = edtNo.getText().toString();
			}
			return Vno;
		}
		
		public static String Is_Valid_saldo(EditText edtSaldo){
			String Vsaldo = null;
			if(edtSaldo.getText().toString().length() <= 0){
				edtSaldo.setError("Kode wajib diisi");
			} else if(edtSaldo.getText().toString().length() > 5){
				edtSaldo.setError("Kode melebihi batas");
			} else {
				Vsaldo = edtSaldo.getText().toString();
			}
			return Vsaldo;
		}

}
