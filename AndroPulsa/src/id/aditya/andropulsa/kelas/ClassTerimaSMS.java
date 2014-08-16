package id.aditya.andropulsa.kelas;

import id.aditya.andropulsa.database.DsSaldo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class ClassTerimaSMS extends BroadcastReceiver {
	
	private String pesan, pengirim;
	private DsSaldo dssaldo;
	private String nm_preference = "DataPreference";
	SharedPreferences sp;
	SharedPreferences.Editor spe;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		dssaldo = new DsSaldo(context);
		dssaldo.open();
		sp = context.getSharedPreferences(nm_preference, 0);
		spe = sp.edit();
		
		if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
			Bundle extras = intent.getExtras();
			try{
				if(extras != null){
					Object[] smsExtra = (Object[]) extras.get("pdus");
					
					for(int i=0; i< smsExtra.length; i++){
						SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
						pesan = sms.getMessageBody();
						pengirim = sms.getOriginatingAddress();
						Cursor cr = dssaldo.fetchDataFilter(sp.getInt("id_distributor", 0));
						for(int j=0; j<cr.getCount(); j++){
							for(cr.moveToFirst(); !cr.isAfterLast(); cr.moveToNext()){
								if(pengirim.toString().equals(cr.getString(0).toString()) == true){
									abortBroadcast();
									Toast.makeText(context, pesan.toString(), Toast.LENGTH_LONG).show();
									Log.d("Receiver Pesan : ", pesan.toString());
									Log.d("Receiver Dari : ", pengirim.toString());
								} else {
									Log.d("Receiver Pesan : ", pesan.toString());
									Log.d("Receiver Dari : ", pengirim.toString());
								}
							}
						}
						
					}
				}
			} catch(Exception e){
				Log.e("SmsReceiver", "Exception smsReceiver" +e);
			}
		}
		
	}

}
