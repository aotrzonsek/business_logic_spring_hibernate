package pl.spring.demo.entity;

import pl.spring.demo.type.LiteraryGenre;
import pl.spring.demo.type.PersonalData;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("PROFES")
public class ProfessorEntity extends AuthorEntity implements Serializable {

    @Column(nullable = true)
    private String university;

    // for hibernate
    protected ProfessorEntity() {
        super();
    }

    public ProfessorEntity(PersonalData personalData, String university, String nickName) {
        this(null, personalData, university, nickName, 0);
    }

    public ProfessorEntity(Long id, PersonalData personalData, String university, String nickName, long version) {
        super(id, personalData, nickName, version);
        this.university = university;
    }

    public String getUniversity() {
        return university;
    }
}
