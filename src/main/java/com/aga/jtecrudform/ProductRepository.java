package com.aga.jtecrudform;


import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface ProductRepository extends ListCrudRepository<Product, UUID> {
}
