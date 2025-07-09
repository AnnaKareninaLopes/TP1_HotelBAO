package edu.ifmg.TP1_HotelBao.resources;

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
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody
                                                   RequestTokenDTO dto) {
        authService.createRecoverToken(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/new-password")
    public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody
                                                NewPasswordDTO dto) {
        authService.saveNewPassword(dto);
        return ResponseEntity.noContent().build();
    }

}
