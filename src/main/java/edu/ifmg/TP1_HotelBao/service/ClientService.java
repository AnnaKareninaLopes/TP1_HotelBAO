package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.dtos.ClientDTO;
import edu.ifmg.TP1_HotelBao.dtos.ClientInsertDTO;
import edu.ifmg.TP1_HotelBao.dtos.RoleDTO;
import edu.ifmg.TP1_HotelBao.entities.Client;
import edu.ifmg.TP1_HotelBao.entities.Role;
import edu.ifmg.TP1_HotelBao.projections.UserDetailsProjection;
import edu.ifmg.TP1_HotelBao.repository.ClientRepository;

import edu.ifmg.TP1_HotelBao.repository.RoleRepository;
import edu.ifmg.TP1_HotelBao.service.exceptions.DatabaseException;
import edu.ifmg.TP1_HotelBao.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

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
    public ClientDTO insert(ClientInsertDTO dto) {

        Client client = new Client();

        copyDtoToEntity(dto, client);
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        client = clientRepository.save(client);

        return new ClientDTO(client);

    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {

        try {
            Client entity = clientRepository.getReferenceById(id);

            copyDtoToEntity(dto, entity);
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
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setCelular(dto.getCelular());
        entity.setEndereco(dto.getEndereco());

        entity.getRoles().clear();
        for(RoleDTO role: dto.getRoles()) {
            Role roleEntity = roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(roleEntity);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result =
                clientRepository.findUserAndRoleByLogin(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Login not found");
        }

        // Montar o Client manualmente com os dados da projeção
        Client client = new Client();
        client.setLogin(result.get(0).getUsername());
        client.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection p : result) {
            client.addRole(new Role(p.getRoleId(), p.getAuthority()));
        }

        return client;
    }

    public ClientDTO signup(ClientInsertDTO dto) {

        Client client = new Client();
        copyDtoToEntity(dto, client);

        Role role = roleRepository.findByAuthority("ROLE_CLIENTE");

        client.getRoles().clear();
        client.getRoles().add(role);
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        client = clientRepository.save(client);

        return new ClientDTO(client);
    }

}
