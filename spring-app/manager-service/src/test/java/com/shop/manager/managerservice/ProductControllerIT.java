package com.shop.manager.managerservice;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

//integration tests
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    // @WithMockUser //for set user to request
    @DisplayName("getNewProductPage success") // this is description
    public void getNewProductPage_success() throws Exception {
        //given
        var requestBuilder = MockMvcRequestBuilders.get("/catalogue/products/create")
                .with(user("t.vovk").roles("MANAGER"));
        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andDo(print())
                .andExpectAll(status().isOk(),
                        view().name("catalogue/products/new_product"));
    }
}
