package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.EmailDTO;
import edu.ifmg.TP1_HotelBao.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/email")
public class EmailResource {

    @Autowired
    private EmailService emailService;

    @PostMapping
    @Operation(
            description = "Send an email using the system",
            summary = "Send email",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Email sent successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid email information"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error - Email could not be sent")
            }
    )
    public ResponseEntity<Void> sendEmail
            (@Valid @RequestBody EmailDTO dto) {

        emailService.sendMail(dto);
        return ResponseEntity.noContent().build();

    }

}
