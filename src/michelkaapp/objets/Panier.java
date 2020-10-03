package michelkaapp.objets;

import java.util.List;

public class Panier {

	private List<Product> produits;
	private List<Tarif> prestations;
	


	public Panier(List<Tarif> prestations) {
		this.prestations = prestations;
	}


	public Panier() {
		
	}


	public Panier(List<Product> produits, List<Tarif> prestations) {
		this.produits = produits;
		this.prestations = prestations;
	}
	
	
	public List<Product> getProduits() {
		return produits;
	}
	public void setProduits(List<Product> produits) {
		this.produits = produits;
	}
	public List<Tarif> getPrestations() {
		return prestations;
	}
	public void setPrestations(List<Tarif> prestations) {
		this.prestations = prestations;
	}


	@Override
	public String toString() {
		return "Panier [produits=" + produits + ", prestations=" + prestations + "]";
	}
	
	
	
	
	
}
