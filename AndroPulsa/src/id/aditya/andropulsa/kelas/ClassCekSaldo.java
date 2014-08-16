package id.aditya.andropulsa.kelas;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClassCekSaldo extends AsyncTask<String, Void, Integer> {
	
	ProgressDialog progressDialog;
	Context contx;
	String value;
	String dataNomor;
	int data;
	
	public ClassCekSaldo(Context ctx, String isi, String dataNomor){
		this.contx = ctx;
		this.value = isi;
		this.dataNomor = dataNomor;
	}
	
	@Override
    protected void onPreExecute() {
		progressDialog = ProgressDialog.show(contx, "Mohon Tunggu",  "Melakukan Cek Saldo ...", true);
    }

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			ClassKirimSms cs = new ClassKirimSms();
        	data = cs.kirimSMS(contx, dataNomor, value, null, 0);
            
            return data;
            
		} catch(Exception e){
			return data;
		}
		
	}
	
	@Override
    protected void onPostExecute(final Integer success) {
		new Thread(new Runnable() {
            @Override
            public void run() {
            	try { Thread.sleep(3000);} catch (Exception e) {}
                	progressDialog.dismiss();
                }
        }).start();

        if(success == 1) {
            Toast.makeText(contx, "Pesan Terkirim", Toast.LENGTH_SHORT).show();
        } else if(success == 0) {
            Toast.makeText(contx, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
        }
    }

}
