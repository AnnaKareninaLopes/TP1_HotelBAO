package edu.ifmg.TP1_HotelBao.resources;

import edu.ifmg.TP1_HotelBao.dtos.*;
import edu.ifmg.TP1_HotelBao.repository.StayRepository;
import edu.ifmg.TP1_HotelBao.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
@Tag(name = "Client", description = "Controller for managing clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @Autowired
    private StayRepository stayRepository;

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
    public ResponseEntity<Page<ClientDTO>> findAll(Pageable pageable){
        Page<ClientDTO> clients = clientService.findAll(pageable);
        return ResponseEntity.ok().body(clients);
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
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok().body(client);
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
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Long id) {
        InvoiceDTO invoice = clientService.getInvoice(id);
        return ResponseEntity.ok().body(invoice);
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
    public ResponseEntity<StayValueDTO> getMaxStay(@PathVariable Long id) {
        StayValueDTO dto = clientService.getMaxStayByClient(id);
        return ResponseEntity.ok().body(dto);
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
    public ResponseEntity<StayValueDTO> getMinStay(@PathVariable Long id) {
        StayValueDTO dto = clientService.getMinStayByClient(id);
        return ResponseEntity.ok().body(dto);
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
    public ResponseEntity<StayCountDTO> getTotalEstadias(@PathVariable Long id) {
        StayCountDTO dto = clientService.getTotalStaysByClient(id);
        return ResponseEntity.ok().body(dto);
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
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientInsertDTO dto) {
        ClientDTO client = clientService.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(client.getId()).toUri();

        return ResponseEntity.created(uri).body(client);
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
    public ResponseEntity<ClientDTO> signup(@RequestBody ClientInsertDTO dto) {
        ClientDTO client = clientService.signup(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(client);
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
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        clientDTO = clientService.update(id, clientDTO);
        return ResponseEntity.ok().body(clientDTO);
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

}
