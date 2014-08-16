package id.aditya.andropulsa.kelas;

public class SpinnerObject {
	private String dtIdNominal;
	private String dtNominal;
	
	public SpinnerObject(String dtIdNominal, String dtNominal){
		this.dtIdNominal = dtIdNominal;
		this.dtNominal = dtNominal;
	}

	public String getDtIdNominal() {
		return dtIdNominal;
	}

	public String getDtNominal() {
		return dtNominal;
	}

	public String toString(){
		return dtNominal;
	}

}
