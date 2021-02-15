package com.disqo.assessment.notes.repositories;

import com.disqo.assessment.notes.models.db.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 3:27 AM.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
