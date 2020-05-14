package it.polito.tdp.crimes.model;

public class Adiacenza {
	
	private String o1;
	private String o2;
	private int weight;
	private double weight2;
	/**
	 * @param o1
	 * @param o2
	 * @param weight
	 */
	public Adiacenza(String o1, String o2, int weight) {
		super();
		this.o1 = o1;
		this.o2 = o2;
		this.weight = weight;
	}
	public Adiacenza(String o1, String o2, double weight2) {
		super();
		this.o1 = o1;
		this.o2 = o2;
		this.weight2 = weight2;
	}
	/**
	 * @return the weight2
	 */
	public double getWeight2() {
		return weight2;
	}
	/**
	 * @param weight2 the weight2 to set
	 */
	public void setWeight2(double weight2) {
		this.weight2 = weight2;
	}
	/**
	 * @return the o1
	 */
	public String getO1() {
		return o1;
	}
	/**
	 * @param o1 the o1 to set
	 */
	public void setO1(String o1) {
		this.o1 = o1;
	}
	/**
	 * @return the o2
	 */
	public String getO2() {
		return o2;
	}
	/**
	 * @param o2 the o2 to set
	 */
	public void setO2(String o2) {
		this.o2 = o2;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return o1+" "+ o2 +" "+weight;
	}
	
	public String toStringD() {
		return o1+" "+ o2 +" "+weight;
	}
	
	

}
