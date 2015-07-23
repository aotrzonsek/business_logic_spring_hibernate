package pl.spring.demo.mapper;

import org.hibernate.Hibernate;
import pl.spring.demo.entity.AuthorEntity;
import pl.spring.demo.entity.ProfessorEntity;
import pl.spring.demo.entity.WriterEntity;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.ProfessorTo;
import pl.spring.demo.to.WriterTo;
import pl.spring.demo.type.PersonalData;

public class AuthorMapper extends AbstractMapper<AuthorEntity, AuthorTo> {

    @Override
    public AuthorTo mapSource(AuthorEntity source) {
        if (isWriter(source)) {
            return mapWriter((WriterEntity) HibernateProxyHelper.unwrap(source));
        }
        if (isProfessor(source)) {
            return mapProfessor((ProfessorEntity) HibernateProxyHelper.unwrap(source));
        }
        return null;
    }

    @Override
    public AuthorEntity mapTarget(AuthorTo target) {
        if (isWriter(target)) {
            return mapWriter((WriterTo) target);
        }
        if (isProfessor(target)) {
            return mapProfessor((ProfessorTo) target);
        }
        return null;
    }

    private WriterTo mapWriter(WriterEntity source) {
        WriterTo writer = new WriterTo();
        mapSource(source, writer);
        writer.setGenre(source.getLiteraryGenre());
        return writer;
    }

    private WriterEntity mapWriter(WriterTo target) {
        return new WriterEntity(target.getId(), copyPersonalData(target.getPersonalData()), target.getGenre(), target.getNickName(),
                target.getVersion());
    }

    private ProfessorEntity mapProfessor(ProfessorTo target) {
        return new ProfessorEntity(target.getId(), copyPersonalData(target.getPersonalData()), target.getUniversity(), target.getNickName(),
                target.getVersion());
    }
    private AuthorTo mapProfessor(ProfessorEntity source) {
        ProfessorTo professor = new ProfessorTo();
        mapSource(source, professor);
        professor.setUniversity(source.getUniversity());
        return professor;
    }

    protected void mapSource(AuthorEntity source, AuthorTo target) {
        target.setId(source.getId());
        target.setNickName(source.getNickName());
        target.setPersonalData(copyPersonalData(source.getPersonalData()));
        target.setVersion(source.getVersion());
    }

    private PersonalData copyPersonalData(PersonalData personalDataToCopy) {
        PersonalData personalData = null;
        if (personalDataToCopy != null) {
            personalData = new PersonalData(personalDataToCopy.getFirstName(), personalDataToCopy.getLastName(), personalDataToCopy.getBirthDate());
        }
        return personalData;
    }

    private boolean isWriter(AuthorEntity entity) {
        return entity != null && Hibernate.getClass(entity) == WriterEntity.class;
    }

    private boolean isWriter(AuthorTo to) {
        return to != null && to instanceof WriterTo;
    }

    private boolean isProfessor(AuthorTo to) {
        return to != null && to instanceof ProfessorTo;
    }

    private boolean isProfessor(AuthorEntity entity) {
        return entity != null && Hibernate.getClass(entity) == ProfessorEntity.class;
    }
}
