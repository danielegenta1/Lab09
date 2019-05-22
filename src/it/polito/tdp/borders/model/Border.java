package it.polito.tdp.borders.model;

public class Border 
{
	private int state1no;
	private int state2no;
	private String state1ab;
	private String state2ab;
	private int dyad;
	private int year;
	private int conttype;
	private double version;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state1no;
		result = prime * result + state2no;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Border other = (Border) obj;
		if (state1no != other.state1no)
			return false;
		if (state2no != other.state2no)
			return false;
		return true;
	}


	public int getState1no() {
		return state1no;
	}


	public void setState1no(int state1no) {
		this.state1no = state1no;
	}


	public int getState2no() {
		return state2no;
	}


	public void setState2no(int state2no) {
		this.state2no = state2no;
	}


	public String getState1ab() {
		return state1ab;
	}


	public void setState1ab(String state1ab) {
		this.state1ab = state1ab;
	}


	public String getState2ab() {
		return state2ab;
	}


	public void setState2ab(String state2ab) {
		this.state2ab = state2ab;
	}


	public int getDyad() {
		return dyad;
	}


	public void setDyad(int dyad) {
		this.dyad = dyad;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getConttype() {
		return conttype;
	}


	public void setConttype(int conttype) {
		this.conttype = conttype;
	}


	public double getVersion() {
		return version;
	}


	public void setVersion(double version) {
		this.version = version;
	}


	public Border(int state1no, int state2no, String state1ab, String state2ab, int dyad, int year, int conttype,
			double version) {
		super();
		this.state1no = state1no;
		this.state2no = state2no;
		this.state1ab = state1ab;
		this.state2ab = state2ab;
		this.dyad = dyad;
		this.year = year;
		this.conttype = conttype;
		this.version = version;
	}
	
	

}
