package algorithm.apiori;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Data {
	public static Map<String, ItemSet> importFileText(String fileName, String charset, String delemited, double support)
			throws IOException, FileNotFoundException {
		Map<String, ItemSet> dataSet = new HashMap<>();
		File file = new File(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			String arrObj[] = line.split(delemited);
			ItemSet itemSet = new ItemSet();
			itemSet.setSupport(support);
			if (arrObj.length == 2) {
				String[] items = arrObj[1].split(",");
				for (int i = 0; i < items.length; i++) {
					int num = Integer.parseInt(items[i].toString());
					itemSet.getItemSet().add(num);
				}
			}
			dataSet.put(arrObj[0], itemSet);
		}

		br.close();
		return dataSet;
	}

	public static void main(String[] args) throws  IOException {
		String path = "D:\\Datamining\\Apriori\\File\\dataset.txt";
		String charset = "UTF-8";
		String delemited = "\t";
		double support = 0.5;
		Map<String, ItemSet> map = importFileText(path, charset, delemited, support);
		
		System.out.println("DATA_MINING");
		System.out.println("TID: " + "\t" + "Itemset: ");
		for (Map.Entry<String, ItemSet> mapEntry : map.entrySet()) {
			System.out.println(mapEntry.getKey() + "\t" + mapEntry.getValue().printDataSet());
		}
		
		System.out.println("--------------");
		Apriori apriori = new Apriori(map, 2);
//		Set<ItemSet> setCi = apriori .getCadidates();
//		System.out.println("items\tsupport");
//		for(ItemSet itemSet : setCi) {
//			System.out.println(itemSet);
//		}
//		System.out.println("--------------");
//		
//		apriori .printSetItem(apriori .getCandidates(setCi, 2));
		
		System.out.println("--------------");
		apriori.aprioriGenerate();
		
		System.out.println("--------------");
		apriori.getLawGen();
	}
}