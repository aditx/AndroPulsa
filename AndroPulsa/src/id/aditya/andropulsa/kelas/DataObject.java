package id.aditya.andropulsa.kelas;

public class DataObject {
	
	private String dtIdPembeli, dtNmPembeli;
	
	public DataObject(String dtIdPembeli, String dtNmPembeli) {
		super();
		this.dtIdPembeli = dtIdPembeli;
		this.dtNmPembeli = dtNmPembeli;
	}

	public String getDtIdPembeli() {
		return dtIdPembeli;
	}

	public void setDtIdPembeli(String dtIdPembeli) {
		this.dtIdPembeli = dtIdPembeli;
	}

	public String getDtNmPembeli() {
		return dtNmPembeli;
	}

	public void setDtNmPembeli(String dtNmPembeli) {
		this.dtNmPembeli = dtNmPembeli;
	}

	@Override
	public String toString() {
		return dtIdPembeli;
	}

}
