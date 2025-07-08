package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.dtos.ClientDTO;
import edu.ifmg.TP1_HotelBao.entities.Client;
import edu.ifmg.TP1_HotelBao.repository.ClientRepository;

import edu.ifmg.TP1_HotelBao.service.exceptions.DatabaseException;
import edu.ifmg.TP1_HotelBao.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public void validateClientExists(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado.");
        }
    }

    @Transactional(readOnly=true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        Page<ClientDTO> clients = clientRepository.findAll(pageable)
                .map(ClientDTO::new);
        return clients;
    }

    @Transactional(readOnly=true)
    public ClientDTO findById(long id) {
        Optional<Client> obj = clientRepository.findById(id);

        Client client = obj.orElseThrow(() -> new ResourceNotFound("Client not found: " + id));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setLogin(dto.getLogin());
        entity.setCelular(dto.getCelular());
        entity.setEndereco(dto.getEndereco());
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = clientRepository.getReferenceById(id);
            entity.setNome(dto.getNome());
            entity.setEmail(dto.getEmail());
            entity.setSenha(dto.getSenha());
            entity.setLogin(dto.getLogin());
            entity.setCelular(dto.getCelular());
            entity.setEndereco(dto.getEndereco());
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Client not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id){
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFound("Client not found: " + id);
        }
        try {
            clientRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Client not found: " + id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

}
