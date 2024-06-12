package com.msuser.msuser.controller;

import com.msuser.msuser.client.IFeignClient;
import com.msuser.msuser.dto.DepositDTO;
import com.msuser.msuser.queue.TransactionMessageSender;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/*Este controlador debería tener todos los métodos de transacción, como ingreso de dinero
y traspaso de dinero a otro usuarios*/

@CrossOrigin(origins = "http://localhost:3333")
@RequestMapping("/transactions")
@RestController
public class TransactionController{


    private TransactionMessageSender transactionMessageSender;
    private IFeignClient feignClient;

    @Autowired
    public TransactionController(TransactionMessageSender transactionMessageSender, IFeignClient feignClient) {
        this.transactionMessageSender = transactionMessageSender;
        this.feignClient = feignClient;
    }

    @PostMapping("/deposit/")
    public ResponseEntity<String> getDepositSaving(@RequestBody DepositDTO depositRequest, Authentication authentication) {
        System.out.println("-------------------");
        System.out.println(depositRequest.getEmail());
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String userId = jwt.getClaimAsString("sub");

            depositRequest.setUserId(userId);
            feignClient.getDepositSaving(depositRequest);
            //transactionMessageSender.sendDepositMessage(depositRequest);

            return ResponseEntity.status(HttpStatus.OK).body("Solicitud de depósito enviada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al enviar la solicitud de depósito: " + e.getMessage());
        }
    }


    @GetMapping("/deposit/{accountNumber}")
    public ResponseEntity<DepositDTO> getDepositDetail(@PathVariable String accountNumber, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString("sub");

        try {
            return feignClient.getDepositDetail(accountNumber);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaimAsString("sub");

        try {
            return feignClient.getAll(userId);
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}

