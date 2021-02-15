package com.disqo.assessment.notes.repositories;

import com.disqo.assessment.notes.models.db.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 3:27 AM.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByIdIn(Collection<Long> noteIdColl);
    void deleteAllByUserIdAndIdIn(long userId, Collection<Long> noteIdColl);
}
