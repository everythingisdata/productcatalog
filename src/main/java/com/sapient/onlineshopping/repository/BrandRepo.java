/**
 * 
 */
package com.sapient.onlineshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapient.onlineshopping.entity.Brand;

 
/**
 * @author b.singh
 *
 */
public interface BrandRepo extends JpaRepository<Brand, Integer> {

}