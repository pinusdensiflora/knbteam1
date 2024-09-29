package com.knbteam1.inuri.auth;

public record CustomerDTO(Integer cid,
                          String username,
                          String role,
                          String name,
                          String postcode,
                          String caddr,
                          String ctel
) {

    public static CustomerDTO from (Customer customer){
        return new CustomerDTO(
                customer.getCid(),
                customer.getUsername(),
                customer.getRole(),
                customer.getName(),
                customer.getPostcode(),
                customer.getCaddr(),
                customer.getCtel()
        );
    }
}