package com.graphql.tutorials.productservice.service.impl;

import com.graphql.tutorials.productservice.domain.Manufacturer;
import com.graphql.tutorials.productservice.generated.types.ManufacturerInput;
import com.graphql.tutorials.productservice.repository.ManufacturerRepository;
import com.graphql.tutorials.productservice.repository.specification.ManufacturerSpecification;
import com.graphql.tutorials.productservice.service.ManufacturerQueryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerQueryServiceImpl implements ManufacturerQueryService {

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerQueryServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> getManufacturers(Optional<ManufacturerInput> optionalInput) {
        var input = optionalInput.orElse(new ManufacturerInput());
        var specification = Specification.where(null == input.getName() || input.getName().isBlank()
                        ? null
                        : ManufacturerSpecification.nameContainsIgnoreCase(input.getName()))
                .and(null == input.getOriginCountry() || input.getOriginCountry().isBlank()
                        ? null
                        : ManufacturerSpecification.originCountryContainsIgnoreCase(input.getOriginCountry()))
                ;

        return manufacturerRepository.findAll(specification,
                Sort.by(ManufacturerSpecification.sortOrdersFrom(input.getSorts())));

    }
}
