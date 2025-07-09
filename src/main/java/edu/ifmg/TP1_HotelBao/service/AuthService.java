package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.dtos.EmailDTO;
import edu.ifmg.TP1_HotelBao.dtos.RequestTokenDTO;
import edu.ifmg.TP1_HotelBao.entities.Client;
import edu.ifmg.TP1_HotelBao.entities.PasswordRecover;
import edu.ifmg.TP1_HotelBao.repository.ClientRepository;
import edu.ifmg.TP1_HotelBao.repository.PasswordRecoverRepository;
import edu.ifmg.TP1_HotelBao.service.exceptions.ResourceNotFound;
import edu.ifmg.TP1_HotelBao.dtos.NewPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minutes}")
    private int tokenMinutes;

    @Value("${email.password-recover.uri}")
    private String uri;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    private PasswordEncoder passwordEncoder;

    public void createRecoverToken(RequestTokenDTO dto) {

        Client client = clientRepository.findByEmail(dto.getEmail());
        if (client == null) {
            throw new ResourceNotFound("Client not found.");
        }

        //pelo email gerar um token
        String token = UUID.randomUUID().toString();

        //inserir no BD
        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setToken(token);
        passwordRecover.setEmail(dto.getEmail());
        passwordRecover.setExpiration(
                Instant.now().plusSeconds(tokenMinutes * 60L)
        );
        passwordRecoverRepository.save(passwordRecover);

        //enviar o email com o token incluído no corpo
        String body = "Acesse o link para definir uma nova senha" +
                " (válido por "+tokenMinutes+")\n\n " +uri+token;
        emailService.sendMail(
                new EmailDTO(
                        client.getEmail(),
                        "Recuperação de Senha",
                        body));
    }

    public void saveNewPassword(NewPasswordDTO dto) {

        List<PasswordRecover> list =
                passwordRecoverRepository
                        .searchValidToken(dto.getToken(), Instant.now());

        if (list.isEmpty()) {
            throw new ResourceNotFound("Token not found or expired.");
        }

        Client client = clientRepository.findByEmail(list.getFirst().getEmail());

        client.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        clientRepository.save(client);

    }

}
