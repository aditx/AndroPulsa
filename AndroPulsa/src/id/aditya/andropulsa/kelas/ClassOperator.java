package id.aditya.andropulsa.kelas;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassOperator implements Parcelable {
	private String nm_op, kode, nominal, status;
	private int hrg_beli, hrg_jual, id_op, id_distributor, row_id;
	
	
	public ClassOperator(){}
	
	public ClassOperator(Parcel in){
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		// TODO Auto-generated method stub
		status = in.readString();
		id_op = in.readInt();
		nm_op = in.readString();
		kode = in.readString();
		nominal = in.readString();
		hrg_beli = in.readInt();
		hrg_jual = in.readInt();
		id_distributor = in.readInt();
		row_id = in.readInt();
	}

	public int getId_op() {
		return id_op;
	}

	public void setId_op(int id_op) {
		this.id_op = id_op;
	}

	public String getNm_op() {
		return nm_op;
	}

	public void setNm_op(String nm_op) {
		this.nm_op = nm_op;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getNominal() {
		return nominal;
	}

	public void setNominal(String nominal) {
		this.nominal = nominal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getHrg_beli() {
		return hrg_beli;
	}

	public void setHrg_beli(int hrg_beli) {
		this.hrg_beli = hrg_beli;
	}

	public int getHrg_jual() {
		return hrg_jual;
	}

	public void setHrg_jual(int hrg_jual) {
		this.hrg_jual = hrg_jual;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(status);
		dest.writeInt(id_op);
		dest.writeString(nm_op);
		dest.writeString(kode);
		dest.writeString(nominal);
		dest.writeInt(hrg_beli);
		dest.writeInt(hrg_jual);
		dest.writeInt(id_distributor);
		dest.writeInt(row_id);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new ClassOperator(in);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ClassOperator[size];
		}
	};


	public int getId_distributor() {
		return id_distributor;
	}

	public void setId_distributor(int id_distributor) {
		this.id_distributor = id_distributor;
	}

	public int getRow_id() {
		return row_id;
	}

	public void setRow_id(int row_id) {
		this.row_id = row_id;
	}
	
}
