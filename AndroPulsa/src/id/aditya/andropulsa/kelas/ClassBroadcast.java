package id.aditya.andropulsa.kelas;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClassBroadcast extends AsyncTask<String, Void, Integer> {
	
	ProgressDialog progressDialog;
	Context contx;
	String value;
	ArrayList<String> dataNomor = new ArrayList<String>();
	int data;
	
	public ClassBroadcast(Context ctx, String isi, ArrayList<String> dataNomor){
		this.contx = ctx;
		this.value = isi;
		this.dataNomor = dataNomor;
	}
	
	@Override
    protected void onPreExecute() {
		progressDialog = ProgressDialog.show(contx, "Mohon Tunggu",  "Mengirim Pesan ...", true);
    }

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			ClassKirimSms cs = new ClassKirimSms();
        	data = cs.kirimSMS(contx, null, value, dataNomor, 1);
            
            return data;
            
		} catch(Exception e){
			return data;
		}
		
	}
	
	@Override
    protected void onPostExecute(final Integer success) {
		if(this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }

        if(success == 1) {
            Toast.makeText(contx, "Pesan Terkirim", Toast.LENGTH_SHORT).show();
        } else if(success == 0) {
            Toast.makeText(contx, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
        }
    }

}
