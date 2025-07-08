package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.StayDTO;
import edu.ifmg.TP1_HotelBao.service.ClientService;
import edu.ifmg.TP1_HotelBao.service.RoomService;
import edu.ifmg.TP1_HotelBao.service.StayService;
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
@RequestMapping(value = "/stays")
public class StayResource {

    @Autowired
    private StayService stayService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity <Page<StayDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<StayDTO> stays = stayService.findAll(pageable);
        return ResponseEntity.ok().body(stays);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StayDTO> findById(@PathVariable Long id) {
        StayDTO stay = stayService.findById(id);
        return ResponseEntity.ok().body(stay);
    }

    @PostMapping
    public ResponseEntity<StayDTO> insert(@RequestBody StayDTO stayDTO) {

        stayDTO = stayService.insert(stayDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(stayDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(stayDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<StayDTO> update(@PathVariable Long id, @RequestBody StayDTO stayDTO) {
        stayDTO = stayService.update(id, stayDTO);
        return ResponseEntity.ok().body(stayDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stayService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
