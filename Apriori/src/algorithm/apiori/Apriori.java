package algorithm.apiori;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Apriori {

	private Map<String, ItemSet> dataSet;
	private double support;
	private Set<ItemSet> setL;

	public Apriori(Map<String, ItemSet> dataSet, double support) {
		super();
		this.dataSet = dataSet;
		this.support = support;
		setL = new HashSet<>();
	}

	public Set<ItemSet> getCadidates() {
		Set<ItemSet> setLi = new HashSet<>();
		for (ItemSet item : this.dataSet.values()) {
			for (Integer integer : item.getItemSet()) {
				int count = countFrequent(integer);
				Set<Integer> setItem = new HashSet<>();
				setItem.add(integer);
				ItemSet itemSet = new ItemSet(setItem, count);
				if (!checkContain(itemSet, setLi)) {
					setLi.add(itemSet);
				}
			}
		}
		setLi = checkMinimumSupport(setLi);
		return setLi;
	}

	private Set<ItemSet> checkMinimumSupport(Set<ItemSet> set) {
		Set<ItemSet> setLi = new HashSet<>();
		for (ItemSet itemSet : set) {
			if (itemSet.getSupport() >= this.support) {
				setLi.add(itemSet);
			}
		}
		Set<ItemSet> setLiResult = new TreeSet<>(setLi);
		return setLiResult;
	}

	private boolean checkContain(ItemSet itemSet, Set<ItemSet> setLi) {
		for (ItemSet item : setLi) {
			if (item.getItemSet().equals(itemSet.getItemSet())) {
				return true;
			}
		}
		return false;
	}

	private int countFrequent(Integer number) {
		int count = 0;
		for (ItemSet item : this.dataSet.values()) {
			for (Integer integer : item.getItemSet()) {
				if (integer == number)
					count++;
			}
		}
		return count;
	}

	public Set<ItemSet> aprioriGenerate() {
		System.out.println("Buoc phat sinh lan thu nhat");
		Set<ItemSet> setCan = getCadidates();
		this.setL.addAll(setCan);
		printSetItem2(this.setL);
		int size = 2;
		Set<Set<Integer>> setGen = new HashSet<>();
		while ((setGen = getCandidates(setCan, size)) != null) {
			System.out.println("Buoc phat sinh lan thu " + size);
			Set<ItemSet> setItem = new HashSet<>();
			for (Set<Integer> item : setGen) {
				if (hasSubnet(item)) {
					int count = countFrequent(item);
					setItem.add(new ItemSet(item, count));
				}
			}
			setItem = checkMinimumSupport(setItem);
			this.setL.addAll(setItem);
			printSetItem2(this.setL);

			size++;
		}
		return setL;
	}

	private boolean hasSubnet(Set<Integer> item) {
		Set<Set<Integer>> setSubnet = getSubnet(item);
		int count = 0;
		for (Set<Integer> integer : setSubnet) {
			if (checkSubnet(integer)) {
				count++;
			}
		}
		return (count == setSubnet.size()) ? true : false;
	}

	private boolean checkSubnet(Set<Integer> integer) {
		for (ItemSet itemSet : this.setL) {
			if (itemSet.getItemSet().equals(integer))
				return true;
		}
		return false;
	}

	private int countFrequent(Set<Integer> item) {
		int count = 0;
		for (ItemSet itemSet : this.dataSet.values()) {
			if (itemSet.getItemSet().containsAll(item)) {
				count++;
			}
		}
		return count;
	}

	public Set<Set<Integer>> getSubnet(Set<Integer> set) {
		Set<Set<Integer>> setRes = new HashSet<>();

		int arr[] = new int[set.size()];
		int count = 0;
		for (Integer integer : set) {
			arr[count] = integer;
			count++;
		}

		int n = arr.length;

		for (int i = 0; i < (1 << n); i++) {
			Set<Integer> setInteger = new HashSet<>();
			for (int j = 0; j < n; j++) {
				if ((i & (1 << j)) > 0) {
					setInteger.add(arr[j]);
				}
			}
			if (setInteger.size() == set.size() - 1) {
				setRes.add(setInteger);
			}
		}
		return setRes.isEmpty() ? null : setRes;

	}

	public Set<Set<Integer>> getCandidates(Set<ItemSet> set, int size) {
		Set<Set<Integer>> setRes = new HashSet<>();

		int arr[] = new int[set.size()];
		int count = 0;
		for (ItemSet iSet : set) {
			for (Integer integer : iSet.getItemSet()) {
				arr[count] = integer;
//				System.out.println(arr[count]);
			}
			count++;
		}
		int n = arr.length;

		for (int i = 0; i < (1 << n); i++) {
			Set<Integer> setInteger = new HashSet<>();
			for (int j = 0; j < n; j++) {
				if ((i & (1 << j)) > 0) {
					setInteger.add(arr[j]);
				}
			}
			if (setInteger.size() == size) {
				setRes.add(setInteger);
			}
		}
		return setRes.isEmpty() ? null : setRes;
	}
	
	public Set<Law> getLawGen() {
		Set<Law> setLaw = new HashSet<>();
		for(ItemSet itemSet : this.setL) {
			if(itemSet.getItemSet().size() >= 2) {
				String arr[] = new String[itemSet.getItemSet().size()];
				int count =0;
				for(Integer integer : itemSet.getItemSet()) {
					arr[count] = integer + "";
					count++;
				}
				AssociationLaw associationLaw = new AssociationLaw(arr);
				Set<Law> setLawTmp = associationLaw.getLawGenerate();
				for (Law law : setLawTmp) {
					int countSetA = countFrequent(law.getSetA());
					Set<Integer> setB = new HashSet<>();
					setB.addAll(law.getSetA());
					setB.addAll(law.getSetB());
					int countSetB = countFrequent(setB);
					double minConfidence = getMinConfidence(countSetA, countSetB);
					
					law.setMinConfidence(minConfidence); 
					setLaw.add(law);
				}
			}
		}
		printLaw(setLaw);
		return setLaw;
	}

	private void printLaw(Set<Law> setLaw) {
		System.out.println("Phat sinh luat ket hop!");
		for(Law law : setLaw) {
			System.out.println(law.print());
		}
	}

	private double getMinConfidence(int countSetA, int countSetB) {
		double res = (double) countSetB / countSetA;
		return res;
	}

	public void printSetItem(Set<Set<Integer>> set) {
		for (Set<Integer> itemSet : set) {
			System.out.println(itemSet);
		}
	}

	private void printSetItem2(Set<ItemSet> setL) {
		System.out.println("Item\tSupport");
		for (ItemSet itemSet : setL) {
			System.out.println(itemSet.getItemSet() + "\t" + itemSet.getSupport());
		}
	}

}
