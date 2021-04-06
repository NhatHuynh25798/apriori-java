package algorithm.apiori;

import java.util.HashSet;
import java.util.Set;

public class ItemSet implements Comparable<ItemSet>{

	private Set<Integer> itemSet;
	private double support;

	public ItemSet(Set<Integer> itemSet, double support) {
		super();
		this.itemSet = itemSet;
		this.support = support;
	}

	public ItemSet() {
		itemSet = new HashSet<>();
		support = 0;
	}

	public String printDataSet() {
		String result = "";
		for (Integer i : itemSet) {
			result += i + ", ";
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}

	@Override
	public String toString() {
		String result = "";
		for (Integer i : itemSet) {
			result += i + " \t " + support;
		}
		return result;
	}

	@Override
	public int compareTo(ItemSet o) {
		// TODO Auto-generated method stub
		return this.support > o.support ? -1 : 1;
	}

	public Set<Integer> getItemSet() {
		return itemSet;
	}

	public void setItemSet(Set<Integer> itemSet) {
		this.itemSet = itemSet;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}
	
}
