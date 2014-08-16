package id.aditya.andropulsa.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassPembeli implements Parcelable {
	private int id_pembeli;
	private String nm_pembeli, almt_pembeli, nomor, status;
	
	public int getId_pembeli() {
		return id_pembeli;
	}

	public void setId_pembeli(int id_pembeli) {
		this.id_pembeli = id_pembeli;
	}

	public ClassPembeli(){
		
	}
	
	public ClassPembeli(Parcel in){
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		// TODO Auto-generated method stub
		id_pembeli = in.readInt();
		nm_pembeli = in.readString();
		almt_pembeli = in.readString();
		nomor = in.readString();
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
		dest.writeInt(id_pembeli);
		dest.writeString(nm_pembeli);
		dest.writeString(almt_pembeli);
		dest.writeString(nomor);
		dest.writeString(status);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new ClassPembeli(in);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ClassPembeli[size];
		}
	};

	public String getNm_pembeli() {
		return nm_pembeli;
	}

	public void setNm_pembeli(String nm_pembeli) {
		this.nm_pembeli = nm_pembeli;
	}

	public String getAlmt_pembeli() {
		return almt_pembeli;
	}

	public void setAlmt_pembeli(String almt_pembeli) {
		this.almt_pembeli = almt_pembeli;
	}

	public String getNomor() {
		return nomor;
	}

	public void setNomor(String nomor) {
		this.nomor = nomor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
