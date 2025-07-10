package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.StayDTO;
import edu.ifmg.TP1_HotelBao.service.ClientService;
import edu.ifmg.TP1_HotelBao.service.RoomService;
import edu.ifmg.TP1_HotelBao.service.StayService;
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
@RequestMapping(value = "/stays")
@Tag(name = "Stay", description = "Controller for managing stays")
public class StayResource {

    @Autowired
    private StayService stayService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private PagedResourcesAssembler<StayDTO> pagedResourcesAssembler;

    @GetMapping(produces = "application/json")
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
    public ResponseEntity<PagedModel<EntityModel<StayDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Page<StayDTO> stays = stayService.findAll(pageable);

        PagedModel<EntityModel<StayDTO>> pagedModel = pagedResourcesAssembler.toModel(
                stays,
                stay -> addLinksToStay(stay)
        );

        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
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
    public ResponseEntity<EntityModel<StayDTO>> findById(@PathVariable Long id) {
        StayDTO stay = stayService.findById(id);
        EntityModel<StayDTO> resource = addLinksToStay(stay);
        return ResponseEntity.ok().body(resource);
    }

    @PostMapping(produces = "application/json")
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
    public ResponseEntity<EntityModel<StayDTO>> insert(@RequestBody StayDTO stayDTO) {
        stayDTO = stayService.insert(stayDTO);
        EntityModel<StayDTO> resource = addLinksToStay(stayDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(stayDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(resource);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
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
    public ResponseEntity<EntityModel<StayDTO>> update(@PathVariable Long id, @RequestBody StayDTO stayDTO) {
        stayDTO = stayService.update(id, stayDTO);
        EntityModel<StayDTO> resource = addLinksToStay(stayDTO);
        return ResponseEntity.ok().body(resource);
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

    private EntityModel<StayDTO> addLinksToStay(StayDTO stay) {
        EntityModel<StayDTO> resource = EntityModel.of(stay);

        // Self link
        Link selfLink = linkTo(methodOn(StayResource.class).findById(stay.getId())).withSelfRel();
        resource.add(selfLink);

        // Link to all stays
        Link allStaysLink = linkTo(methodOn(StayResource.class).findAll(0, 20, "ASC", "id")).withRel("all-stays");
        resource.add(allStaysLink);

        // Link to update stay
        Link updateLink = linkTo(methodOn(StayResource.class).update(stay.getId(), stay)).withRel("update");
        resource.add(updateLink);

        // Link to delete stay
        Link deleteLink = linkTo(methodOn(StayResource.class).delete(stay.getId())).withRel("delete");
        resource.add(deleteLink);

        // Add links to related client if client ID is available
        if (stay.getClientId() != null) {
            Link clientLink = linkTo(methodOn(ClientResource.class).findById(stay.getClientId())).withRel("client");
            resource.add(clientLink);
        }

        if (stay.getRoomId() != null) {
            Link roomLink = linkTo(methodOn(RoomResource.class).findById(stay.getRoomId())).withRel("room");
            resource.add(roomLink);
        }

        return resource;
    }
}