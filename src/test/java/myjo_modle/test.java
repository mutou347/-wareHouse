package myjo_modle;

import org.springframework.beans.factory.annotation.Value;

public class test {
	@Value("${tianmaQuery}")
	private static String tianmaQuery;
	public static void main(String[] args) {
		System.out.println(tianmaQuery);
	}
}
