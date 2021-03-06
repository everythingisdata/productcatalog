package com.sapient.shopping.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.sapient.shopping.service.IProductCatalogService;
import com.sapient.shopping.sse.SseService;

/**
 *
 * @author b.singh
 *
 */
@RestController
public class ProductCatalogController {

	@Autowired
	SseService service;
	
	@Autowired
	IProductCatalogService iProductCatalogService;
	/**
	 * This method will return the list of product on the base of product sku number. Also register the SSE for this client
	 * @param productsku : Product SKU number  
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */

	@GetMapping("/productcatalog/{productsku}")
	public ResponseEntity<SseEmitter> doSearchProduct(@PathVariable("productsku") Integer productsku)
			throws InterruptedException, IOException {
		final SseEmitter emitter = new SseEmitter();
		service.addEmitter(emitter);
		service.changeNofiy(productsku);
		emitter.onCompletion(() -> service.removeEmitter(emitter));
		emitter.onTimeout(() -> service.removeEmitter(emitter));
		return new ResponseEntity<>(emitter, HttpStatus.OK);
	}
	
	/**
	 * This method will get call when user will buy the product. It will also update the other user if any change in product quantity
	 * @param productsku : SKU number of product, which user want to buy
	 * @param quantity   : Number of items, user want to buys
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */

	@PostMapping("/productcatalog/{productsku}/{quantity}")
	public String updateProductCatalog(@PathVariable("productsku") Integer productsku, @PathVariable("quantity") Integer quantity)
			throws InterruptedException, IOException {		
		System.out.println(productsku +" <0000> "+quantity);
		this.iProductCatalogService.updateProductCatalog(productsku,quantity);
		service.changeNofiy(productsku);
		return "OK";
	}

}