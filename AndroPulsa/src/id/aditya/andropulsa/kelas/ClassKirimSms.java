package id.aditya.andropulsa.kelas;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class ClassKirimSms extends Service {
	
	private int hasil = 1;
	private int sts;
	ArrayList<String> dataNomor = new ArrayList<String>();
	
	public int kirimSMS(final Context context, String no, String pesan, ArrayList<String> dataNomor2, int status){
		String mengirim = "Mengirim SMS";
		String terkirim = "SMS Terkirim";
		this.dataNomor = dataNomor2;
		this.sts = status;
		
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(mengirim), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(terkirim), 0);
		
		BroadcastReceiver brKirim = new BroadcastReceiver(){

			@Override
			public void onReceive(Context ctx, Intent arg1) {
				// TODO Auto-generated method stub
				switch(getResultCode()){
					case Activity.RESULT_OK:
						hasil = 1;
						if(sts == 0){
							Toast.makeText(context, "Data Terkirim", Toast.LENGTH_SHORT).show();
						} else if(sts == 1){
							Toast.makeText(context, "Pesan Terkirim", Toast.LENGTH_SHORT).show();
						}
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						hasil = 0;
						Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						hasil = 0;
						Toast.makeText(context, "Tidak ada Jaringan", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						hasil = 0;
						Toast.makeText(context, "PDU Kosong", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						hasil = 0;
						Toast.makeText(context, "Radio Nonaktif", Toast.LENGTH_SHORT).show();
						break;
				}
			}
			
		};
		
		context.registerReceiver(brKirim, new IntentFilter(mengirim));
		
		if(sts == 0){
			SmsManager sms = SmsManager.getDefault();
			sms.sendTextMessage(no, null, pesan, sentPI, deliveredPI);
		} else if(sts == 1){
			for(int i=0; i<dataNomor.size(); i++){
				SmsManager sms = SmsManager.getDefault();
				sms.sendTextMessage(dataNomor.get(i).toString(), null, pesan, sentPI, deliveredPI);
			}
		}
		
		return hasil;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
