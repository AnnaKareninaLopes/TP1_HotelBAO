package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Transactional
    public void limparBanco() {
        passwordRecoverRepository.deleteAll();
        clientRepository.deleteAll();
        roleRepository.deleteAll();
        roomRepository.deleteAll();
        roomRepository.deleteAll();
    }

}
