package aiss.videominer.controller;

import aiss.videominer.model.Caption;
import aiss.videominer.model.Video;
import aiss.videominer.repository.CaptionRepository;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository captionRepository;

    @Autowired
    VideoRepository videoRepository;

    @GetMapping
    public List<Caption> findAll() {
        return captionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Caption findOne(@PathVariable String id) {
        Optional<Caption> caption = captionRepository.findById(id);
        if (caption.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caption not found");
        }
        return caption.get();
    }

    // REQUISITO: Devolver las captions de un vídeo dado su id
    @GetMapping("/video/{videoId}")
    public List<Caption> findByVideo(@PathVariable String videoId) {
        Optional<Video> video = videoRepository.findById(videoId);
        if (video.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found");
        }
        return video.get().getCaptions();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Caption create(@RequestBody Caption caption) {
        return captionRepository.save(caption);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable String id, @RequestBody Caption updatedCaption) {
        if (!captionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caption not found");
        }
        updatedCaption.setId(id);
        captionRepository.save(updatedCaption);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!captionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Caption not found");
        }
        captionRepository.deleteById(id);
    }
}