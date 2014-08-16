package id.aditya.andropulsa.kelas;

public class ClassKontak {
	
	private String dataNama, dataTelp;
	private int dataId, dataTot, dataUtang;
	
	public ClassKontak(String dataNama, int dataId, int dataTot, int dataUtang, String dataTelp) {
		super();
		this.dataNama = dataNama;
		this.dataId = dataId;
		this.dataTot = dataTot;
		this.dataUtang = dataUtang;
		this.dataTelp = dataTelp;
	}

	public String getDataNama() {
		return dataNama;
	}

	public void setDataNama(String dataNama) {
		this.dataNama = dataNama;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public int getDataTot() {
		return dataTot;
	}

	public void setDataTot(int dataTot) {
		this.dataTot = dataTot;
	}

	public int getDataUtang() {
		return dataUtang;
	}

	public void setDataUtang(int dataUtang) {
		this.dataUtang = dataUtang;
	}

	public String getDataTelp() {
		return dataTelp;
	}

	public void setDataTelp(String dataTelp) {
		this.dataTelp = dataTelp;
	}

}
