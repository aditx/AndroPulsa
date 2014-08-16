package id.aditya.andropulsa.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassDistributor implements Parcelable {
	private int id_distributor, pin;
	private String nm_distributor, alamat, telp, sms, status;

	public ClassDistributor(){
		
	}
	
	public ClassDistributor(Parcel in){
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		// TODO Auto-generated method stub
		id_distributor = in.readInt();
		nm_distributor = in.readString();
		alamat = in.readString();
		telp = in.readString();
		sms = in.readString();
		pin = in.readInt();
		status = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id_distributor);
		dest.writeString(nm_distributor);
		dest.writeString(alamat);
		dest.writeString(telp);
		dest.writeString(sms);
		dest.writeInt(pin);
		dest.writeString(status);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new ClassDistributor(in);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ClassDistributor[size];
		}
	};

	public int getId_distributor() {
		return id_distributor;
	}

	public void setId_distributor(int id_distributor) {
		this.id_distributor = id_distributor;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getNm_distributor() {
		return nm_distributor;
	}

	public void setNm_distributor(String nm_distributor) {
		this.nm_distributor = nm_distributor;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
