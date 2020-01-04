package danisik.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Domain entity representing Goods in application. Goods is used in Invoice.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Goods extends EntityParent{

	private String name;

	private Long quantity;

	private Float pricePerOne;

	private Float discount;

	private Float priceQuantityDiscount;

	private Float taxRate;

	private Float dph;

	private Float priceFull;

	@ManyToOne
	@JoinColumn(name="invoice_id")
	private Invoice invoice;

	/**
	 * Constructor for domain entity Contact.
	 * @param name Name of goods.
	 * @param quantity Quantity of goods.
	 * @param pricePerOne Price of goods for 1.
	 * @param discount Discount of goods.
	 * @param taxRate Tax rate of goods.
	 */
	public Goods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate) {
		this.setName(name);
		this.setQuantity(quantity);
		this.setPricePerOne(pricePerOne);
		this.setDiscount(discount);
		this.setTaxRate(taxRate);

		Float priceQuantity = pricePerOne * quantity;
		Float priceQuantityDiscount = priceQuantity - (priceQuantity * (discount / 100));
		Float dph = priceQuantityDiscount * (taxRate / 100);
		Float priceFull = priceQuantityDiscount + dph;

		this.setPriceQuantityDiscount(priceQuantityDiscount);
		this.setDph(dph);
		this.setPriceFull(priceFull);
	}

}
