package com.msuser.msuser.dto;


import lombok.*;
import lombok.NoArgsConstructor;

/*Esta clase encapsula los datos que entran a través de la peticion de depositar dinero
en el controlador (endpoint) para enviarlos con Rabbit al ms-transactions donde se procesará
la petición */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequestDTO {
    private String userId;
    private String accountNumber;
    private float valueToTransfer;
    private String shippingCurrency;

}
