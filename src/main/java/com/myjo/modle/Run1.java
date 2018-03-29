package com.myjo.modle;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.myjo.modle.csv.MargeCSV;
import com.myjo.modle.httpClint.SupplyInfo;
import com.myjo.modle.pojo.TianMaItem;

@Component
public class Run1 implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		MargeCSV marge=new MargeCSV();
		Map<String, TianMaItem> supplierToHeavy = marge.supplierToHeavy(new File("E:\\MYJOProject\\inventoryIAndMerge\\mergeCSV\\merge_Table_1522198944166.csv"));
		SupplyInfo info=new SupplyInfo();
		System.out.println(supplierToHeavy);
		System.out.println("===================等着吧=====================");
		Map<String, TianMaItem> searchByArticleno = info.getSearchByArticleno(supplierToHeavy);
		File file=new File("E:\\file.txt");
		file.createNewFile();
		FileWriter fw=new FileWriter(file);
		fw.write(searchByArticleno.toString());
		fw.flush();
		fw.close();
	}
}
