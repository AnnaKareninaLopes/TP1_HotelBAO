package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.dtos.StayDTO;
import edu.ifmg.TP1_HotelBao.entities.Stay;
import edu.ifmg.TP1_HotelBao.repository.ClientRepository;
import edu.ifmg.TP1_HotelBao.repository.RoomRepository;
import edu.ifmg.TP1_HotelBao.repository.StayRepository;
import edu.ifmg.TP1_HotelBao.service.exceptions.ClientNotFoundException;
import edu.ifmg.TP1_HotelBao.service.exceptions.DatabaseException;
import edu.ifmg.TP1_HotelBao.service.exceptions.ResourceNotFound;
import edu.ifmg.TP1_HotelBao.service.exceptions.RoomUnavailableException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class StayService {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public Page<StayDTO> findAll(Pageable pageable) {
        Page<Stay> stays = stayRepository.findAll(pageable);
        return stays.map(StayDTO::new);
    }

    @Transactional
    public StayDTO findById(Long id) {
        Optional<Stay> obj = stayRepository.findById(id);

        Stay stay = obj.orElseThrow(() -> new ResourceNotFound("Stay not found: " + id));
        return new StayDTO(stay);
    }

    private void validateClientAndRoom(Long clientId, Long roomId, LocalDate dataEntrada) {
        if (!stayRepository.existsClientById(clientId)) {
            throw new ClientNotFoundException("Cliente com ID " + clientId + " não encontrado.");
        }

        if (!stayRepository.existsRoomById(roomId)) {
            throw new ResourceNotFound("Quarto com ID " + roomId + " não encontrado.");
        }

        if (stayRepository.existsRoomOccupied(roomId, dataEntrada)) {
            throw new RoomUnavailableException("O quarto com ID " + roomId + " já está ocupado na data informada.");
        }
    }

    @Transactional
    public StayDTO insert(StayDTO dto) {

        validateClientAndRoom(dto.getClientId(), dto.getRoomId(), dto.getDataEntrada());

        Stay entity = new Stay();
        copyDtoToEntity(dto, entity);
        entity = stayRepository.save(entity);
        return new StayDTO(entity);
    }

    @Transactional
    public StayDTO update(Long id, StayDTO dto) {
        try {
            Stay entity = stayRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = stayRepository.save(entity);
            return new StayDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Stay not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!stayRepository.existsById(id)) {
            throw new ResourceNotFound("Stay not found: " + id);
        }
        try {
            stayRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Stay not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(StayDTO dto, Stay entity) {
        entity.setDataEntrada(dto.getDataEntrada());
        entity.setCliente(clientRepository.getReferenceById(dto.getClientId()));
        entity.setRoom(roomRepository.getReferenceById(dto.getRoomId()));
    }


}
