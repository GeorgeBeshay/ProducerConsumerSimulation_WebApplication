package SimulationSystemComponents;

import java.util.Random;
import java.awt.Color;

public class Product{
	
	int id;
	String prodColor;
	
	public Product(int id) {
		Random random = new Random();
		this.id = id;
		Color prodColor = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		this.prodColor = String.format("#%02x%02x%02x", prodColor.getRed(), prodColor.getGreen(), prodColor.getBlue());
		System.out.println(this.prodColor);
	}
	
	public Product(Product product) {
		this.id = product.id;
		this.prodColor = product.prodColor;
	}
	
	@Override
	public String toString() {
		return "P" + this.id;
	}

	public String getProdColor() {
		return prodColor;
	}
	
	@Override
	public Product clone() {
		return new Product(this);
	}
	
	

}
