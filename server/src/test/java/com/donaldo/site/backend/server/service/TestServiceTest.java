package com.donaldo.site.backend.server.service;

import com.donaldo.site.backend.server.models.*;
import com.donaldo.site.backend.server.models.projections.IdAndTitle;
import com.donaldo.site.backend.server.payload.request.AccessRequest;
import com.donaldo.site.backend.server.repository.RoleRepository;
import com.donaldo.site.backend.server.repository.StreamsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import com.donaldo.site.backend.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestServiceTest {

    private TestService service;

    final List<IdAndTitle> titles = List.of(
            new IdAndTitle(1, "Mushoku Tensei"),
            new IdAndTitle(2, "Steins; Gate"),
            new IdAndTitle(3, "Overlord"),
            new IdAndTitle(4, "Hunter x Hunter"),
            new IdAndTitle(5, "Re:Zero"),
            new IdAndTitle(6, "World Trigger"),
            new IdAndTitle(7, "Odd Taxi")
    );

    final List<Streams> streams = List.of(
            new Streams(),
            new Streams("href", "name"),
            new Streams(1, "https://2ip.ru", "2chan")
    );

    final User admin = new User("admin", "admin");
    final User donaldo = new User("donaldo", "donaldo");
    final User unknown = new User("...", "???");

    final Role adminRole = new Role(ERole.ROLE_ADMIN);
    final Role userRole = new Role(ERole.ROLE_USER);

    @BeforeEach
    public void prepare() {
        final TitlesRepository titlesRepository = Mockito.mock(TitlesRepository.class);
        Mockito.when(titlesRepository.findAllTitles()).thenReturn(titles);
        Mockito.when(titlesRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        for (int i = 0; i < titles.size(); ++i) {
            Mockito.when(titlesRepository.findById(i + 1)).thenReturn(Optional.of(new Titles(
                    titles.get(i).getId(),
                    titles.get(i).getTitle(),
                    "imageUrl",
                    "description"
            )));
        }

        final StreamsRepository streamsRepository = Mockito.mock(StreamsRepository.class);
        Mockito.when(streamsRepository.findAll()).thenReturn(streams);

        admin.setRoles(Set.of(adminRole, userRole));
        donaldo.setRoles(Set.of(userRole));

        final ArgumentMatcher<String> adminMatcher = admin.getUsername()::equals;
        final ArgumentMatcher<String> donaldoMatcher = donaldo.getUsername()::equals;
        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByUsername(Mockito.argThat(adminMatcher))).thenReturn(Optional.of(admin));
        Mockito.when(userRepository.findByUsername(Mockito.argThat(donaldoMatcher))).thenReturn(Optional.of(donaldo));

        final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        Mockito.when(roleRepository.findByName(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(adminRole));
        Mockito.when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(userRole));

        service = new TestService(titlesRepository, streamsRepository, userRepository, roleRepository);
    }

    @Test
    public void allAccessTest() {
        Assertions.assertTrue(service.all().contains("Public"));
    }

    private AccessRequest makeAccessRequest(final User user) {
        final AccessRequest accessRequest = new AccessRequest();
        accessRequest.setUsername(user.getUsername());
        return accessRequest;
    }

    @Test
    public void userAccessTest() {
        Assertions.assertTrue(service.user(makeAccessRequest(admin)).endsWith("tent."));
        Assertions.assertTrue(service.user(makeAccessRequest(donaldo)).startsWith("User"));
        Assertions.assertEquals("Forbidden.", service.user(makeAccessRequest(unknown)));
    }

    @Test
    public void adminAccessTest() {
        Assertions.assertTrue(service.admin(makeAccessRequest(admin)).endsWith("Board."));
        Assertions.assertFalse(service.admin(makeAccessRequest(donaldo)).startsWith("User"));
        Assertions.assertEquals("Forbidden.", service.admin(makeAccessRequest(unknown)));
    }

    @Test
    public void titlesTest() {
        Assertions.assertEquals(titles.size(), service.getTitles().size());
        Assertions.assertEquals("Mushoku Tensei", service.getTitles().get(0).getTitle());
        final List<IdAndTitle> list = service.getTitles();
        for (int i = 0; i < list.size(); ++i) {
            Assertions.assertEquals(1, list.get(i).getId() - i);
        }
    }

    @Test
    public void exactTitleTest() {
        final Random random = new Random();
        for (int test = 1; test <= 100; ++test) {
            final int id = random.nextInt(test);
            if (id < 1 || id > titles.size()) {
                Assertions.assertNull(service.getFollowingSeries(id));
            } else {
                Assertions.assertEquals(id, service.getFollowingSeries(id).getId());
            }
        }
    }

    @Test
    public void streamsTest() {
        Assertions.assertEquals(streams.size(), service.getStreams().size());
        final List<Streams> list = service.getStreams();
        for (int i = 1; i < list.size(); ++i) {
            Assertions.assertNotNull(list.get(i).getHref());
        }
        Assertions.assertNull(list.get(0).getName());
    }
}
