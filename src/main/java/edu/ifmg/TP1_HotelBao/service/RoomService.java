package edu.ifmg.TP1_HotelBao.service;

import edu.ifmg.TP1_HotelBao.dtos.RoomDTO;
import edu.ifmg.TP1_HotelBao.entities.Room;
import edu.ifmg.TP1_HotelBao.repository.RoomRepository;
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
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;



    @Transactional(readOnly=true)
    public Page<RoomDTO> findAll(Pageable pageable) {
        Page<Room> rooms = roomRepository.findAll(pageable);
        return rooms.map(RoomDTO::new);
    }

    @Transactional(readOnly=true)
    public RoomDTO findByID(Long id) {
        Optional<Room> obj = roomRepository.findById(id);

        Room room =obj.orElseThrow(()->new ResourceNotFound("Room not found: " + id));
        return new RoomDTO(room);
    }

    @Transactional
    public RoomDTO insert(RoomDTO dto) {
        Room entity = new Room();
        entity.setDescricao(dto.getDescricao());
        entity.setValor(dto.getValor());
        entity.setImagemUrl(dto.getImagemUrl());
        entity = roomRepository.save(entity);
        return new RoomDTO(entity);
    }

    @Transactional
    public RoomDTO update(Long id, RoomDTO dto) {
        try {
            Room entity = roomRepository.getReferenceById(id);
            entity.setDescricao(dto.getDescricao());
            entity.setValor(dto.getValor());
            entity.setImagemUrl(dto.getImagemUrl());
            entity = roomRepository.save(entity);
            return new RoomDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Room not found: " + id);
        }
    }

    @Transactional
    public void delete(Long id) {

        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFound("Room not found: " + id);
        }
        try {
            roomRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Room not found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

}
