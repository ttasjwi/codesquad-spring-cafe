package com.ttasjwi.cafe.repository;

import com.ttasjwi.cafe.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    private MemoryUserRepository memoryUserRepository;

    @BeforeEach
    void beforeEach() {
        this.memoryUserRepository = new MemoryUserRepository();
    }

    @Test
    @DisplayName("저장 후 사용자 이름으로 찾기 -> 같은 사용자 찾아짐")
    void saveTest1() {
        // given (준비)
        String userName = "ttasjwi";
        String userEmail = "ttasjwi920@gmail.com";
        String password = "1234";

        User saveUser = new User(userName, userEmail, password);

        // when (실행)
        memoryUserRepository.save(saveUser);
        User findUser = memoryUserRepository.findByUserName(userName).get();

        // then (검증)
        assertThat(findUser).isEqualTo(saveUser);
    }

    @Test
    @DisplayName("저장 후 사용자 이메일로 찾기 -> 같은 사용자 찾아짐")
    void saveTest2() {
        // given (준비)
        String userName = "ttasjwi";
        String userEmail = "ttasjwi920@gmail.com";
        String password = "1234";

        User saveUser = new User(userName, userEmail, password);

        // when (실행)
        memoryUserRepository.save(saveUser);
        User findUser = memoryUserRepository.findByUserEmail(userEmail).get();

        // then (검증)
        assertThat(findUser).isEqualTo(saveUser);
    }

    @Test
    @DisplayName("중복된 이름의 사용자 저장 -> IllegalStateException 발생")
    void saveFail1() {
        // given (준비)
        User user1 = new User();
        user1.setUserName("ttasjwi");
        memoryUserRepository.save(user1);

        User user2 = new User();
        user2.setUserName(user1.getUserName());

        // when, then(실행, 검증)
        assertThatThrownBy(() -> memoryUserRepository.save(user2)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("중복된 이메일의 사용자 저장 -> IllegalStateException 발생")
    void saveFail2() {
        // given (준비)
        User user1 = new User();
        user1.setUserName("user1");
        user1.setUserEmail("ttasjwi@gmail.com");

        memoryUserRepository.save(user1);

        User user2 = new User();
        user2.setUserName("user2");
        user2.setUserEmail(user1.getUserEmail());

        // when, then(실행, 검증)
        assertThatThrownBy(() -> memoryUserRepository.save(user2)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("findAll 메서드 호출 -> 모든 사용자 List 반환")
    void findAllTest() {
        // given
        User user1 = new User("user1", "user1@gmail.com", "1234");
        User user2 = new User("user2", "user2@gmail.com", "5678");

        memoryUserRepository.save(user1);
        memoryUserRepository.save(user2);

        // when
        List<User> list = memoryUserRepository.findAll();
        int size = list.size();

        // then
        assertThat(size).isEqualTo(2);
    }
}