package danisik.pia.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Goods extends EntityParent{

	private String name;

	private Long quantity;

	private Float pricePerOne;

	private Float discount;

	private Float priceQuantity;

	private Float priceQuantityDiscount;

	private Float taxRate;

	private Float dph;

	private Float priceFull;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="invoice_id")
	private Invoice invoice;

	public Goods(String name, Long quantity, Float pricePerOne, Float discount, Float taxRate) {
		discount = discount / 100;
		taxRate = taxRate / 100;

		this.setName(name);
		this.setQuantity(quantity);
		this.setPricePerOne(pricePerOne);
		this.setDiscount(discount);
		this.setTaxRate(taxRate);

		Float priceQuantity = pricePerOne * quantity;
		Float priceQuantityDiscount = priceQuantity - (priceQuantity * discount);
		Float dph = priceQuantityDiscount * taxRate;
		Float priceFull = priceQuantityDiscount + dph;

		this.setPriceQuantity(priceQuantity);
		this.setPriceQuantityDiscount(priceQuantityDiscount);
		this.setDph(dph);
		this.setPriceFull(priceFull);
	}

}
