package com.devspotlight.devspotlight.repository;

import com.devspotlight.devspotlight.model.FavRepositories;
import com.devspotlight.devspotlight.model.Role;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.record.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProjectRepositoryTest {
    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUserId() {
        List<FavRepositories> favRepositories = Collections.emptyList();
        UserDTO userDTO = new UserDTO("Gabriel", "ugabb", "gsb@gmail.com",
                "https://github.com/ugabb", "https://avatars.githubusercontent.com/u/76067595?v=4",
                4, favRepositories, Role.USER);

        User created = this.createUser(userDTO);

        Optional<User> result = userRepository.findById(created.getId());

        assertThat(result.isPresent()).isTrue();
    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        entityManager.persist(newUser);
        return newUser;
    }

}