package warehouse;

//Klasse für die Teile, welche einsortiert werden sollen
public class Part implements BasicUnits{

	private String description;
	private int partnumber;
	
	public Part(String description, int partnumber) {
		super();
		this.description = description;
		this.partnumber = partnumber;
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

	@Override
	public int getUnitX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUnitY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUnitZ() {
		// TODO Auto-generated method stub
		return 0;
	}

}
