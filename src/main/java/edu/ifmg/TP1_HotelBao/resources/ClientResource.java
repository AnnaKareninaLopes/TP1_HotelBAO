package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.*;
import edu.ifmg.TP1_HotelBao.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/clients")
@Tag(name = "Client", description = "Controller for managing clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @Autowired
    private PagedResourcesAssembler<ClientDTO> pagedResourcesAssembler;

    @GetMapping(produces = "application/json")
    @Operation(
            description = "List all clients with pagination",
            summary = "List all clients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<PagedModel<EntityModel<ClientDTO>>> findAll(Pageable pageable){
        Page<ClientDTO> clients = clientService.findAll(pageable);
        PagedModel<EntityModel<ClientDTO>> pagedModel = pagedResourcesAssembler.toModel(
                clients,
                client -> addLinksToClient(client)
        );
        return ResponseEntity.ok().body(pagedModel);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Find client by ID",
            summary = "Find client by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<ClientDTO>> findById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        EntityModel<ClientDTO> resource = addLinksToClient(client);
        return ResponseEntity.ok().body(resource);
    }

    @GetMapping(value = "/{id}/invoice", produces = "application/json")
    @Operation(
            description = "Get client invoice with stay details",
            summary = "Get client invoice",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<InvoiceDTO>> getInvoice(@PathVariable Long id) {
        InvoiceDTO invoice = clientService.getInvoice(id);
        EntityModel<InvoiceDTO> resource = EntityModel.of(invoice);

        // Add link to client
        Link clientLink = linkTo(methodOn(ClientResource.class).findById(id)).withRel("client");
        resource.add(clientLink);

        // Add link to self
        Link selfLink = linkTo(methodOn(ClientResource.class).getInvoice(id)).withSelfRel();
        resource.add(selfLink);

        return ResponseEntity.ok().body(resource);
    }

    @GetMapping(value = "/{id}/stay/max", produces = "application/json")
    @Operation(
            description = "Returns the client's most expensive stay",
            summary = "Most expensive stay",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Client or stay not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<EntityModel<StayValueDTO>> getMaxStay(@PathVariable Long id) {
        StayValueDTO dto = clientService.getMaxStayByClient(id);
        EntityModel<StayValueDTO> resource = EntityModel.of(dto);

        // Add link to client
        Link clientLink = linkTo(methodOn(ClientResource.class).findById(id)).withRel("client");
        resource.add(clientLink);

        // Add link to self
        Link selfLink = linkTo(methodOn(ClientResource.class).getMaxStay(id)).withSelfRel();
        resource.add(selfLink);

        return ResponseEntity.ok().body(resource);
    }

    @GetMapping(value = "/{id}/stay/min", produces = "application/json")
    @Operation(
            description = "Returns the client's cheapest stay",
            summary = "Cheapest stay",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Client or stay not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<EntityModel<StayValueDTO>> getMinStay(@PathVariable Long id) {
        StayValueDTO dto = clientService.getMinStayByClient(id);
        EntityModel<StayValueDTO> resource = EntityModel.of(dto);

        // Add link to client
        Link clientLink = linkTo(methodOn(ClientResource.class).findById(id)).withRel("client");
        resource.add(clientLink);

        // Add link to self
        Link selfLink = linkTo(methodOn(ClientResource.class).getMinStay(id)).withSelfRel();
        resource.add(selfLink);

        return ResponseEntity.ok().body(resource);
    }

    @GetMapping(value = "/{id}/stay/total", produces = "application/json")
    @Operation(
            description = "Returns the total number of stays for a client",
            summary = "Total stays count",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CLIENT')")
    public ResponseEntity<EntityModel<StayCountDTO>> getTotalEstadias(@PathVariable Long id) {
        StayCountDTO dto = clientService.getTotalStaysByClient(id);
        EntityModel<StayCountDTO> resource = EntityModel.of(dto);

        // Add link to client
        Link clientLink = linkTo(methodOn(ClientResource.class).findById(id)).withRel("client");
        resource.add(clientLink);

        // Add link to self
        Link selfLink = linkTo(methodOn(ClientResource.class).getTotalEstadias(id)).withSelfRel();
        resource.add(selfLink);

        return ResponseEntity.ok().body(resource);
    }

    @PostMapping(produces = "application/json")
    @Operation(
            description = "Create a new client with admin privileges",
            summary = "Create a new client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<ClientDTO>> insert(@RequestBody ClientInsertDTO dto) {
        ClientDTO client = clientService.insert(dto);
        EntityModel<ClientDTO> resource = addLinksToClient(client);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).body(resource);
    }

    @PostMapping(value = "/signup", produces = "application/json")
    @Operation(
            description = "Public endpoint for client registration",
            summary = "Sign up as a client",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data or email already exists")
            }
    )
    public ResponseEntity<EntityModel<ClientDTO>> signup(@RequestBody ClientInsertDTO dto) {
        ClientDTO client = clientService.signup(dto);
        EntityModel<ClientDTO> resource = addLinksToClient(client);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).body(resource);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Update an existing client",
            summary = "Update client information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<EntityModel<ClientDTO>> update(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.update(id, clientDTO);
        EntityModel<ClientDTO> resource = addLinksToClient(updatedClient);
        return ResponseEntity.ok().body(resource);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            description = "Delete a client by ID",
            summary = "Delete client",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content - Client successfully deleted"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ClientDTO> addLinksToClient(ClientDTO client) {
        EntityModel<ClientDTO> resource = EntityModel.of(client);

        // Self link
        Link selfLink = linkTo(methodOn(ClientResource.class).findById(client.getId())).withSelfRel();
        resource.add(selfLink);

        // Add invoice link
        Link invoiceLink = linkTo(methodOn(ClientResource.class).getInvoice(client.getId())).withRel("invoice");
        resource.add(invoiceLink);

        // Add stay statistics links
        Link maxStayLink = linkTo(methodOn(ClientResource.class).getMaxStay(client.getId())).withRel("max-stay");
        resource.add(maxStayLink);

        Link minStayLink = linkTo(methodOn(ClientResource.class).getMinStay(client.getId())).withRel("min-stay");
        resource.add(minStayLink);

        Link totalStaysLink = linkTo(methodOn(ClientResource.class).getTotalEstadias(client.getId())).withRel("total-stays");
        resource.add(totalStaysLink);

        // Add update link
        Link updateLink = linkTo(methodOn(ClientResource.class).update(client.getId(), client)).withRel("update");
        resource.add(updateLink);

        // Add delete link
        Link deleteLink = linkTo(methodOn(ClientResource.class).delete(client.getId())).withRel("delete");
        resource.add(deleteLink);

        return resource;
    }
}