package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.RoomDTO;
import edu.ifmg.TP1_HotelBao.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/rooms")
public class RoomResource {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<Page<RoomDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);

        Page<RoomDTO> rooms = roomService.findAll(pageable);
        return ResponseEntity.ok().body(rooms);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {
        RoomDTO room = roomService.findByID(id);
        return ResponseEntity.ok().body(room);
    }


    @PostMapping
    public ResponseEntity<RoomDTO> insert(@RequestBody RoomDTO roomDTO) {
        roomDTO = roomService.insert(roomDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(roomDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(roomDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO = roomService.update(id, roomDTO);
        return ResponseEntity.ok().body(roomDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
