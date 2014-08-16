package id.aditya.andropulsa.kelas;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ClassTransaksi extends AsyncTask<String, Void, Integer> {
	
	ProgressDialog progressDialog;
	Context contx;
	String value , tujuan;
	int data;
	
	public ClassTransaksi(Context ctx, String isi, String no){
		this.contx = ctx;
		this.value = isi;
		this.tujuan = no;
	}
	
	@Override
    protected void onPreExecute() {
		progressDialog = ProgressDialog.show(contx, "Mohon Tunggu",  "Melakukan Transaksi ...", true);
    }

	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			ClassKirimSms cs = new ClassKirimSms();
            data = cs.kirimSMS(contx, tujuan.toString(), value.toString(), null, 0);
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
            Toast.makeText(contx, "Proses Terkirim", Toast.LENGTH_SHORT).show();
        } else if(success == 0) {
            Toast.makeText(contx, "Gagal Terkirim", Toast.LENGTH_SHORT).show();
        }
    }

}
