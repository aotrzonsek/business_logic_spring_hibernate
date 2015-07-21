package pl.spring.demo.entity;

import pl.spring.demo.type.AudioBookFormat;

public class AudioBookExemplarEntity extends BookExemplarEntity {

    private AudioBookFormat format;

    public AudioBookExemplarEntity(String serialNumber, AudioBookFormat format) {
        this(null, serialNumber, format);
    }

    public AudioBookExemplarEntity(Long id, String serialNumber, AudioBookFormat format) {
        super(id, serialNumber);
        this.format = format;
    }

    public AudioBookFormat getFormat() {
        return format;
    }
}
