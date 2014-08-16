package id.aditya.andropulsa.kelas;

public class AutoObject {
	
	private String dtidNomor, dtNomor;
	
	public AutoObject(String dtidNomor, String dtNomor) {
		super();
		this.dtidNomor = dtidNomor;
		this.dtNomor = dtNomor;
	}

	public String getDtidNomor() {
		return dtidNomor;
	}

	public void setDtidNomor(String dtidNomor) {
		this.dtidNomor = dtidNomor;
	}

	public String getDtNomor() {
		return dtNomor;
	}

	public void setDtNomor(String dtNomor) {
		this.dtNomor = dtNomor;
	}

	@Override
	public String toString() {
		return dtidNomor;
	}
	
}
