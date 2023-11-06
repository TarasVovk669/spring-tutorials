package com.graphql.tutorials.productservice.service;

import com.graphql.tutorials.productservice.domain.Manufacturer;
import com.graphql.tutorials.productservice.generated.types.ManufacturerInput;

import java.util.List;
import java.util.Optional;

public interface ManufacturerQueryService {
    List<Manufacturer> getManufacturers(Optional<ManufacturerInput> optionalInput);
}
