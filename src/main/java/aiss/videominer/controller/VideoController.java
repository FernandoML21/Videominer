package aiss.videominer.controller;

import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
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

@Tag(name = "Videos", description = "API para la gestión de vídeos")
@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository videoRepository;

    @Operation(summary = "Obtener todos los vídeos")
    @GetMapping
    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    @Operation(summary = "Obtener un vídeo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vídeo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vídeo no encontrado")
    })
    @GetMapping("/{id}")
    public Video findOne(@PathVariable String id) {
        Optional<Video> video = videoRepository.findById(id);

        if (video.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }
        return video.get();
    }

 

    @Operation(summary = "Crear un nuevo video",
            description = "Añade un nuevo video a la base de datos H2")
    @ApiResponse(responseCode = "201", description = "Video creado con éxito")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Video create(@RequestBody Video video) {return videoRepository.save(video);}

    @Operation(summary = "Actualizar un video",
            description = "Modifica los datos de un video existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Canal actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Canal no encontrado")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody Video updatedVideo) {
        if (!videoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }
        updatedVideo.setId(id);
        videoRepository.save(updatedVideo);
    }

    @Operation(summary = "Eliminar un video", description = "Borra un canal de la base de datos por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Canal eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Canal no encontrado")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!videoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }
        videoRepository.deleteById(id);
    }
}