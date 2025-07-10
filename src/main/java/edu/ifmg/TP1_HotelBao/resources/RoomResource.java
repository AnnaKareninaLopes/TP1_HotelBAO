package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.RoomDTO;
import edu.ifmg.TP1_HotelBao.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/rooms")
@Tag(name = "Room", description = "Controller for managing hotel rooms")
public class RoomResource {

    @Autowired
    private RoomService roomService;

    @Autowired
    private PagedResourcesAssembler<RoomDTO> pagedResourcesAssembler;

    @GetMapping(produces = "application/json")
    @Operation(
            description = "List all rooms with pagination",
            summary = "List all rooms",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    public ResponseEntity<PagedModel<EntityModel<RoomDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<RoomDTO> rooms = roomService.findAll(pageable);

        PagedModel<EntityModel<RoomDTO>> pagedModel = pagedResourcesAssembler.toModel(
                rooms,
                room -> addLinksToRoom(room)
        );

        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Find room by ID",
            summary = "Find room by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    public ResponseEntity<EntityModel<RoomDTO>> findById(@PathVariable Long id) {
        RoomDTO room = roomService.findByID(id);
        EntityModel<RoomDTO> resource = addLinksToRoom(room);
        return ResponseEntity.ok().body(resource);
    }

    @PostMapping(produces = "application/json")
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
    public ResponseEntity<EntityModel<RoomDTO>> insert(@RequestBody RoomDTO roomDTO) {
        roomDTO = roomService.insert(roomDTO);
        EntityModel<RoomDTO> resource = addLinksToRoom(roomDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(roomDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(resource);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
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
    public ResponseEntity<EntityModel<RoomDTO>> update(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        roomDTO = roomService.update(id, roomDTO);
        EntityModel<RoomDTO> resource = addLinksToRoom(roomDTO);
        return ResponseEntity.ok().body(resource);
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

    private EntityModel<RoomDTO> addLinksToRoom(RoomDTO room) {
        EntityModel<RoomDTO> resource = EntityModel.of(room);

        // Self link
        Link selfLink = linkTo(methodOn(RoomResource.class).findById(room.getId())).withSelfRel();
        resource.add(selfLink);

        // Link to all rooms
        Link allRoomsLink = linkTo(methodOn(RoomResource.class).findAll(0, 20, "ASC", "id")).withRel("all-rooms");
        resource.add(allRoomsLink);

        // Link to update room
        Link updateLink = linkTo(methodOn(RoomResource.class).update(room.getId(), room)).withRel("update");
        resource.add(updateLink);

        // Link to delete room
        Link deleteLink = linkTo(methodOn(RoomResource.class).delete(room.getId())).withRel("delete");
        resource.add(deleteLink);

        // Link staysLink = linkTo(methodOn(StayResource.class).findByRoomId(room.getId())).withRel("stays");
        // resource.add(staysLink);

        return resource;
    }
}