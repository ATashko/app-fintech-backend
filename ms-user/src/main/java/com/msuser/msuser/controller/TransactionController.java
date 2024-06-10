package com.msuser.msuser.controller;

import com.msuser.msuser.dto.DepositRequestDTO;
import com.msuser.msuser.queue.TransactionMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/*Este controlador debería tener todos los métodos de transacción, como ingreso de dinero
y traspaso de dinero a otro usuarios*/

@CrossOrigin(origins = "*")
@RequestMapping("/transactions")
@RestController
public class TransactionController{

    @Autowired
    private TransactionMessageSender transactionMessageSender;

    @PostMapping("/deposit")
    public ResponseEntity<String> depositMoney(@RequestBody DepositRequestDTO depositRequest, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getClaimAsString("sub");

            depositRequest.setUserId(userId);

            transactionMessageSender.sendDepositMessage(depositRequest);

            return ResponseEntity.status(HttpStatus.OK).body("Solicitud de depósito enviada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al enviar la solicitud de depósito: " + e.getMessage());
        }
    }
}

