package com.shop.manager.managerservice;

import com.shop.manager.managerservice.client.RestCatalogueClient;
import com.shop.manager.managerservice.controller.ProductController;
import com.shop.manager.managerservice.domain.Product;
import com.shop.manager.managerservice.dto.ProductPayload;
import com.shop.manager.managerservice.exception.CustomBadRequestException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {


    //private RestCatalogueClient restCatalogueClient = Mockito.mock(RestCatalogueClient.class);
    @Mock
    private RestCatalogueClient restCatalogueClient;
    @InjectMocks //
    private ProductController productController;


    @Test
    @DisplayName("createProduct and redirect to page with product page") // this is description
    public void createProduct_ValidRequest_ReturnToValidPage() {
        //given
        var payload = new ProductPayload(null, "Product 1", "Description product 1", new BigDecimal(10));
        var model = new ConcurrentModel();
        var bindingResult = new MapBindingResult(Map.of(), "BR");

        doReturn(new Product(1L, "Product 1", new BigDecimal(10), "Description product 1"))
                .when(this.restCatalogueClient)
                .create(notNull(), any(), any());
        //when
        String result = this.productController.createProduct(payload, bindingResult, model);
        //then
        assertEquals("redirect:/catalogue/products/1", result);

        verify(this.restCatalogueClient).create("Product 1", "Description product 1", new BigDecimal(10));
    }

    @Test
    @DisplayName("createProduct and redirect to error page") // this is description
    public void createProduct_InvalidRequest_ReturnToErrorPage() {
        //given
        var payload = new ProductPayload(null, null, null, new BigDecimal(10));
        var model = new ConcurrentModel();
        var bindingResult = new BeanPropertyBindingResult(payload, "BR");
        bindingResult.addError(new ObjectError("Error", "Error description"));

        //when
        String result = this.productController.createProduct(payload, bindingResult, model);
        //then
        assertEquals("catalogue/products/new_product", result);
        assertEquals(payload, model.getAttribute("payload"));


    }

}
