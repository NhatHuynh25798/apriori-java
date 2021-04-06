package algorithm.apiori;

import java.util.HashSet;
import java.util.Set;

public class Law {
	private Set<Integer> setA;
	private Set<Integer> setB;
	private double minConfidence;

	public Law(Set<Integer> setA, Set<Integer> setB, double minConfidence) {
		super();
		this.setA = setA;
		this.setB = setB;
		this.minConfidence = minConfidence;
	}

	public Law(double minConfidence) {
		super();
		this.setA = new HashSet<>();
		this.setB = new HashSet<>();
		this.minConfidence = minConfidence;
	}

	public String print() {
		String res = "";
		for (Integer i : setA)
			res += i;
		res += " --> ";
		for (Integer j : setB)
			res += j;
		res += "\t min_conf= " + minConfidence;
		return res;
	}

	public void setMinConfidence(double minConfidence) {
		this.minConfidence = minConfidence;
	}

	public Set<Integer> getSetA() {
		return setA;
	}

	public void setSetA(Set<Integer> setA) {
		this.setA = setA;
	}

	public Set<Integer> getSetB() {
		return setB;
	}

	public void setSetB(Set<Integer> setB) {
		this.setB = setB;
	}

	public double getMinConfidence() {
		return minConfidence;
	}

}
