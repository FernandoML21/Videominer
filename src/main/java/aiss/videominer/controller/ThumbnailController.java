package aiss.videominer.controller;


import aiss.videominer.model.Thumbnail;
import aiss.videominer.repository.ThumbnailRepository;
import aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Thumbnails", description = "API para la gestión de miniaturas de vídeo")
@RestController
@RequestMapping("/videominer")
public class ThumbnailController {

    @Autowired
    ThumbnailRepository thumbnailRepository;

    @Autowired
    VideoRepository videoRepository;

    @Operation(summary = "Obtener todas las miniaturas")
    @ApiResponse(responseCode = "200", description = "Lista de miniaturas obtenida con éxito")
    @GetMapping("/thumbnails")
    public List<Thumbnail> findAll() {
        return thumbnailRepository.findAll();
    }

    @Operation(summary = "Obtener miniatura por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Miniatura encontrada"),
            @ApiResponse(responseCode = "404", description = "Miniatura no encontrada")
    })
    @GetMapping("/thumbnails/{id}")
    public Thumbnail findById(
            @Parameter(description = "ID de la miniatura")
            @PathVariable String id) {
        return thumbnailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Thumbnail not found"));
    }

    @Operation(summary = "Obtener miniatura de un vídeo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Miniatura del vídeo encontrada"),
            @ApiResponse(responseCode = "404", description = "Vídeo no encontrado")
    })
    @GetMapping("/videos/{videoId}/thumbnail")
    public Thumbnail findByVideoId(
            @Parameter(description = "ID del vídeo")
            @PathVariable String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Video not found"))
                .getThumbnail();
    }
}