package edu.ifmg.TP1_HotelBao.resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ifmg.TP1_HotelBao.service.AuthService;
import edu.ifmg.TP1_HotelBao.dtos.NewPasswordDTO;
import edu.ifmg.TP1_HotelBao.dtos.RequestTokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/recover-token")
    @Operation(
            description = "Gera um token de recuperacao de senha",
            summary = "Gera token",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody
                                                   RequestTokenDTO dto) {
        authService.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/new-password")
    @Operation(
            description = "Gera uma nova senha",
            summary = "Gera senha",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Forbidden", responseCode = "403"),
            }
    )
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody
                                                NewPasswordDTO dto) {
        authService.saveNewPassword(dto);
        return ResponseEntity.noContent().build();
    }

}
