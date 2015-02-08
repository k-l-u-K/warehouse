package warehouse;

//Klasse für die Teile, welche einsortiert werden sollen
public class Part {

	private String description;
	private int partnumber;
	private int amount;
	
	public Part(String description, int partnumber, int amount) {
		this.description = description;
		this.partnumber = partnumber;
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPartnumber() {
		return partnumber;
	}

	public void setPartnumber(int partnumber) {
		this.partnumber = partnumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
