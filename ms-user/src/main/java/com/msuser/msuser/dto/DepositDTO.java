package com.msuser.msuser.dto;


import lombok.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*Esta clase encapsula los datos que entran a través de la peticion de depositar dinero
en el controlador (endpoint) para enviarlos con Rabbit al ms-transactions donde se procesará
la petición */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositDTO {

    private String userId;
    private String accountNumber;
    private BigDecimal valueToTransfer;
    private String shippingCurrency;
    private String email;

}
