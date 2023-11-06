package com.graphql.tutorials.productservice.service.impl;

import com.graphql.tutorials.productservice.domain.Model;
import com.graphql.tutorials.productservice.generated.types.*;
import com.graphql.tutorials.productservice.repository.ModelRepository;
import com.graphql.tutorials.productservice.repository.specification.ManufacturerSpecification;
import com.graphql.tutorials.productservice.repository.specification.ModelSpecification;
import com.graphql.tutorials.productservice.service.ModelQueryService;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelQueryServiceImpl implements ModelQueryService {

    private final ModelRepository modelRepository;

    public ModelQueryServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findModels(Optional<ModelInput> optionalInput, Optional<NumericComparisonInput> optNumericInput) {
        var input = optionalInput.orElse(new ModelInput());
        var numericInput = optNumericInput.orElse(new NumericComparisonInput());
        var seriesInput = null == input.getSeries() ? new SeriesInput() : input.getSeries();
        var manufacturerInput = null == seriesInput.getManufacturer() ? new ManufacturerInput() : seriesInput.getManufacturer();

        var specification = Specification.where(
                        null == input.getName() || input.getName().isBlank() ? null : ModelSpecification.modelNameContainsIgnoreCase(input.getName()))
                .and(null == manufacturerInput.getName() || manufacturerInput.getName().isBlank() ? null : ModelSpecification.manufacturerNameContainsIgnoreCase(manufacturerInput.getName()))
                .and(null == manufacturerInput.getOriginCountry() || manufacturerInput.getOriginCountry().isBlank() ? null : ModelSpecification.manufacturerOriginCountryContainsIgnoreCase(manufacturerInput.getOriginCountry()))
                .and(null == seriesInput.getName() || seriesInput.getName().isBlank() ? null : ModelSpecification.seriesNameContainsIgnoreCase(seriesInput.getName()))
                .and(null != input.getIsAvailable()
                        ? ModelSpecification.available(input.getIsAvailable())
                        : null)
                .and(null != input.getTransmission()
                        ? ModelSpecification.transmissionEquals(input.getTransmission()) : null)
                .and(null != input.getExteriorColors()
                        && !input.getExteriorColors().isEmpty()
                        ? ModelSpecification.exteriorColorsLikeIgnoreCase(input.getExteriorColors())
                        : null)
                .and(null != numericInput.getOperator()
                        ? numericInput.getOperator().equals(NumericComparison.GREATER_THAN_EQUALS)
                        ? ModelSpecification.priceGreaterThanEquals(numericInput.getValue())
                        : numericInput.getOperator().equals(NumericComparison.LESS_THAN_EQUALS)
                        ? ModelSpecification.priceLessThanEquals(numericInput.getValue())
                        : ModelSpecification.priceBetween(numericInput.getValue(), numericInput.getHighValue())
                        : null);

        return modelRepository.findAll(specification, Sort.by(ManufacturerSpecification.sortOrdersFrom(input.getSorts())));

    }

    @Override
    public Page<Model> findModels(Optional<ModelInput> input,
                                  Optional<NumericComparisonInput> optNumericInput,
                                  Integer page,
                                  Integer size) {
        var modelInput = input.orElse(new ModelInput());
        var numericInput = optNumericInput.orElse(new NumericComparisonInput());
        var seriesInput = modelInput.getSeries() == null ? new SeriesInput()
                : modelInput.getSeries();
        var manufacturerInput = seriesInput.getManufacturer() == null ?
                new ManufacturerInput() : seriesInput.getManufacturer();

        var specification = Specification.where(
                StringUtils.isNotBlank(manufacturerInput.getName()) ?
                        ModelSpecification.manufacturerNameContainsIgnoreCase(
                                manufacturerInput.getName()
                        ) : null
        ).and(
                StringUtils.isNotBlank(manufacturerInput.getOriginCountry()) ?
                        ModelSpecification.manufacturerOriginCountryContainsIgnoreCase(
                                manufacturerInput.getOriginCountry()
                        ) : null
        ).and(
                StringUtils.isNotBlank(seriesInput.getName()) ?
                        ModelSpecification.seriesNameContainsIgnoreCase(
                                seriesInput.getName()
                        ) : null
        ).and(
                StringUtils.isNotBlank(modelInput.getName()) ?
                        ModelSpecification.modelNameContainsIgnoreCase(
                                modelInput.getName()
                        ) : null
        ).and(
                modelInput.getIsAvailable() != null ?
                        ModelSpecification.available(modelInput.getIsAvailable())
                        : null
        ).and(
                modelInput.getTransmission() != null ?
                        ModelSpecification.transmissionEquals(modelInput.getTransmission())
                        : null
        ).and(
                !CollectionUtils.isEmpty(modelInput.getExteriorColors()) ?
                        ModelSpecification.exteriorColorsLikeIgnoreCase(modelInput.getExteriorColors())
                        : null
        ).and(null != numericInput.getOperator()
                ? numericInput.getOperator().equals(NumericComparison.GREATER_THAN_EQUALS)
                ? ModelSpecification.priceGreaterThanEquals(numericInput.getValue())
                : numericInput.getOperator().equals(NumericComparison.LESS_THAN_EQUALS)
                ? ModelSpecification.priceLessThanEquals(numericInput.getValue())
                : ModelSpecification.priceBetween(numericInput.getValue(), numericInput.getHighValue())
                : null);

        var sortOrders = ModelSpecification.sortOrdersFrom(
                modelInput.getSorts()
        );

        var pageable = PageRequest.of(
                Optional.ofNullable(page).orElse(0),
                Optional.ofNullable(size).orElse(50),
                Sort.by(sortOrders)
        );

        return modelRepository.findAll(specification, pageable);
    }

    @Override
    public List<ModelSimple> getSimpleModels(List<Long> modelIds) {
        return modelRepository.findAllById(modelIds).stream()
                .map(e -> ModelSimple.newBuilder()
                        .id(e.getId())
                        .name(e.getName())
                        .onTheRoadPrice(e.getOnTheRoadPrice())
                        .transmission(Transmission.valueOf(e.getTransmission()))
                        .exteriorColor(e.getExteriorColor())
                        .interiorColor(e.getInteriorColor())
                        .releaseYear(e.getReleaseYear())
                        .bodyType(BodyType.valueOf(e.getBodyType()))
                        .fuel(Fuel.valueOf(e.getFuel()))
                        .build())
                .collect(Collectors.toList());
    }

}
