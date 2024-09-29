package com.knbteam1.inuri.auth.api;

import com.knbteam1.inuri.auth.Customer;
import com.knbteam1.inuri.auth.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPIController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<CustomerDTO> signUp(@RequestBody CustomerSignUpRequestBody customerSignUpRequestBody) {
        CustomerDTO customer = customerService.apiSignUp(
                customerSignUpRequestBody.username(),
                customerSignUpRequestBody.password(),
                customerSignUpRequestBody.name(),
                customerSignUpRequestBody.addr(),
                customerSignUpRequestBody.postcode(),
                customerSignUpRequestBody.tel()
        );
        return ResponseEntity.ok(customer);
    }
}
