package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.RoomDTO;
import edu.ifmg.TP1_HotelBao.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping(value = "/rooms")
public class RoomResource {

    @Autowired
    private RoomService roomService;

    @GetMapping
    @Operation(
            description = "List all rooms with pagination",
            summary = "List all rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
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
    @Operation(
            description = "Find room by ID",
            summary = "Find room by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )

    public ResponseEntity<RoomDTO> findById(@PathVariable Long id) {
        RoomDTO room = roomService.findByID(id);
        return ResponseEntity.ok().body(room);
    }


    @PostMapping
    @Operation(
            description = "Create a new room",
            summary = "Create a new room",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Room created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<RoomDTO> insert(@RequestBody RoomDTO roomDTO) {
        roomDTO = roomService.insert(roomDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(roomDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(roomDTO);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            description = "Update an existing room",
            summary = "Update room information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<RoomDTO> update(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO = roomService.update(id, roomDTO);
        return ResponseEntity.ok().body(roomDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            description = "Delete a room by ID",
            summary = "Delete room",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Room successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
