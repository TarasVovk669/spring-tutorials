package com.reactive.webflux.demo.sec03_route_f.mapper;

import com.reactive.webflux.demo.sec03_route_f.dto.CustomerDto;
import com.reactive.webflux.demo.sec03_route_f.entity.Customer;

public class EntityDtoMapper {

    public static Customer toEntity(CustomerDto dto){
        var customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setId(dto.id());
        return customer;
    }

    public static CustomerDto toDto(Customer customer){
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

}
