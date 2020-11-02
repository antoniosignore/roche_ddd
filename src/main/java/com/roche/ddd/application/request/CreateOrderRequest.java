package com.roche.ddd.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roche.ddd.domain.Product;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateOrderRequest {

    @NotNull
    private final Product product;

    @NotNull
    private final String buyerEmail;

    @JsonCreator
    public CreateOrderRequest(@NotNull final Product product, @NotNull String buyerEmail) {
        this.product = product;
        this.buyerEmail = buyerEmail;
    }
}
