package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.StayDTO;
import edu.ifmg.TP1_HotelBao.service.ClientService;
import edu.ifmg.TP1_HotelBao.service.RoomService;
import edu.ifmg.TP1_HotelBao.service.StayService;
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
@RequestMapping(value = "/stays")
public class StayResource {

    @Autowired
    private StayService stayService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoomService roomService;

    @GetMapping
    @Operation(
            description = "List all stays with pagination",
            summary = "List all stays",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
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
    @Operation(
            description = "Find stay by ID",
            summary = "Find stay by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")

    public ResponseEntity<StayDTO> findById(@PathVariable Long id) {
        StayDTO stay = stayService.findById(id);
        return ResponseEntity.ok().body(stay);
    }

    @PostMapping
    @Operation(
            description = "Create a new stay",
            summary = "Create a new stay",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Stay created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<StayDTO> insert(@RequestBody StayDTO stayDTO) {

        stayDTO = stayService.insert(stayDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(stayDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(stayDTO);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            description = "Update an existing stay",
            summary = "Update stay information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<StayDTO> update(@PathVariable Long id, @RequestBody StayDTO stayDTO) {
        stayDTO = stayService.update(id, stayDTO);
        return ResponseEntity.ok().body(stayDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            description = "Delete a stay by ID",
            summary = "Delete stay",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Stay successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stayService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
