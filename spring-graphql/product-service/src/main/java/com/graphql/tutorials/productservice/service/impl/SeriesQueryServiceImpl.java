package com.graphql.tutorials.productservice.service.impl;

import com.graphql.tutorials.productservice.domain.Series;
import com.graphql.tutorials.productservice.generated.types.ManufacturerInput;
import com.graphql.tutorials.productservice.generated.types.SeriesInput;
import com.graphql.tutorials.productservice.repository.SeriesRepository;
import com.graphql.tutorials.productservice.repository.specification.ManufacturerSpecification;
import com.graphql.tutorials.productservice.repository.specification.SeriesSpecification;
import com.graphql.tutorials.productservice.service.SeriesQueryService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeriesQueryServiceImpl implements SeriesQueryService {

    private final SeriesRepository seriesRepository;

    public SeriesQueryServiceImpl(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    public List<Series> findSeries(Optional<SeriesInput> optionalSeriesInput) {
        var input = optionalSeriesInput.orElse(new SeriesInput());
        var manufacturerInput = null == input.getManufacturer() ? new ManufacturerInput() : input.getManufacturer();
        var specification = Specification.where(null == input.getName() || input.getName().isBlank()
                        ? null
                        : SeriesSpecification.seriesNameContainsIgnoreCase(input.getName()))
                .and(null == manufacturerInput.getName() || manufacturerInput.getName().isBlank()
                        ? null
                        : SeriesSpecification.manufacturerNameContainsIgnoreCase(manufacturerInput.getName()))
                .and(null == manufacturerInput.getOriginCountry() || manufacturerInput.getOriginCountry().isBlank()
                        ? null
                        : SeriesSpecification.manufacturerOriginCountryContainsIgnoreCase(manufacturerInput.getOriginCountry()));

        return seriesRepository.findAll(specification, Sort.by(ManufacturerSpecification.sortOrdersFrom(input.getSorts())));
    }
}
