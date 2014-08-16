package id.aditya.andropulsa.kelas;

public class ClassSaldo {
	
	private String nm_distributor, format_saldo, sms_center;
	private int pin, id;
	
	public ClassSaldo(String nm_distributor, String format_saldo, 
			int pin, String sms_center, int id) {
		super();
		this.nm_distributor = nm_distributor;
		this.format_saldo = format_saldo;
		this.sms_center = sms_center;
		this.pin = pin;
		this.id = id;
	}

	public String getNm_distributor() {
		return nm_distributor;
	}

	public void setNm_distributor(String nm_distributor) {
		this.nm_distributor = nm_distributor;
	}

	public String getFormat_saldo() {
		return format_saldo;
	}

	public void setFormat_saldo(String format_saldo) {
		this.format_saldo = format_saldo;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getSms_center() {
		return sms_center;
	}

	public void setSms_center(String sms_center) {
		this.sms_center = sms_center;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
