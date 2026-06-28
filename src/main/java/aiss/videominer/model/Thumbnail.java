package aiss.videominer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Thumbnail")
public class Thumbnail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("id")
    private String id;

    @JsonProperty("url")
    @NotEmpty(message = "Thumbnail url cannot be empty")
    @Column(columnDefinition = "TEXT")
    private String url;

    @JsonProperty("size_type")
    @NotEmpty(message = "Thumbnail size type cannot be empty")
    private String sizeType;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getSizeType() { return sizeType; }
    public void setSizeType(String sizeType) { this.sizeType = sizeType; }
}
