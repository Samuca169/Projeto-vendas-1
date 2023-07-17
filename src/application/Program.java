package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Sale> list = new ArrayList<>();

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.next();

		System.out.println();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			if (path == null) {
				throw new IOException("O sistema não pode encontrar o arquivo especificado");
			}
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				int month = Integer.parseInt(fields[0]);
				int year = Integer.parseInt(fields[1]);
				String seller = fields[2];
				int items = Integer.parseInt(fields[3]);
				double total = Double.parseDouble(fields[4]);
				list.add(new Sale(month, year, seller, items, total));
				line = br.readLine();
			}
			
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
			
			Comparator<Sale> comp = (p1, p2) -> p1.avaragePrice().compareTo(p2.avaragePrice());
			
			List<Sale> filteredList = list.stream()
					.filter(p -> p.getYear() == 2016)
					.sorted(comp.reversed())
					.limit(5)
					.collect(Collectors.toList());
			
			for(Sale sale : filteredList) {
				System.out.println(sale);
			}
			
			Double sum = list.stream()
					.filter(p -> p.getSeller().toUpperCase().equals("LOGAN") && (p.getMonth() == 1 || p.getMonth() == 7))
					.map(p -> p.getTotal())
					.reduce(0.0, (x,y) -> x + y);
			
			System.out.println();
			System.out.println(String.format("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + "%.2f", sum));
			
		} 
		catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}
