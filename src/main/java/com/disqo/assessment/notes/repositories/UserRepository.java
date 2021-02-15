package com.disqo.assessment.notes.repositories;

import com.disqo.assessment.notes.models.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 12:30 AM.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
