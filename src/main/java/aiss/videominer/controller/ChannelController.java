package aiss.videominer.controller;

import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name = "Channels", description = "API para la gestión de canales de vídeo")
@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository channelRepository;

    @Operation(summary = "Obtener todos los canales",
            description = "Devuelve una lista con todos los canales almacenados")
    @ApiResponse(responseCode = "200",
            description = "Lista de canales obtenida con éxito")
    @GetMapping
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Operation(summary = "Obtener un canal por ID",
            description = "Devuelve los detalles de un canal específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Canal encontrado"),
            @ApiResponse(responseCode = "404", description = "Canal no encontrado")
    })
    @GetMapping("/{id}")
    public Channel findOne(@PathVariable String id) {
        Optional<Channel> channel = channelRepository.findById(id);
        if (channel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found");
        }
        return channel.get();
    }

    @Operation(summary = "Crear un nuevo canal",
            description = "Añade un nuevo canal a la base de datos H2")
    @ApiResponse(responseCode = "201", description = "Canal creado con éxito")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Channel create(@RequestBody Channel channel) {
        return channelRepository.save(channel);
    }

    @Operation(summary = "Actualizar un canal",
            description = "Modifica los datos de un canal existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Canal actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Canal no encontrado")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody Channel updatedChannel) {
        if (!channelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found");
        }
        updatedChannel.setId(id);
        channelRepository.save(updatedChannel);
    }

    @Operation(summary = "Eliminar un canal", description = "Borra un canal de la base de datos por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Canal eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Canal no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!channelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found");
        }
        channelRepository.deleteById(id);
    }
}