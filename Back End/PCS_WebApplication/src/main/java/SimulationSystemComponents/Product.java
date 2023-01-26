package SimulationSystemComponents;

public class Product {
	
	int id;
	// implement the color
	
	public Product(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "P" + this.id;
	}

}
