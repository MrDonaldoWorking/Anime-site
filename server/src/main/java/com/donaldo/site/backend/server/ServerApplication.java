package com.donaldo.site.backend.server;

import com.donaldo.site.backend.server.models.ERole;
import com.donaldo.site.backend.server.models.Role;
import com.donaldo.site.backend.server.models.Streams;
import com.donaldo.site.backend.server.models.Titles;
import com.donaldo.site.backend.server.repository.RoleRepository;
import com.donaldo.site.backend.server.repository.StreamsRepository;
import com.donaldo.site.backend.server.repository.TitlesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		System.out.println("Running");
	}

	@Bean
	public CommandLineRunner presetTitles(final TitlesRepository titlesRepository) {
		return (args) -> {
			titlesRepository.save(new Titles(
					1,
					"Mushoku Tensei",
					"//animego.org/media/cache/thumbs_300x420/upload/anime/images/6159cdce16165870171196.jpg",
					"A 34-year-old NEET loses his life saving a stranger in a traffic accident, only to wake" +
							" up to find himself reborn as the baby Rudeus Greyrat in a new world filled with magic. " +
							"The child of the adventurers Paul and Zenith Greyrat, his innate talent for magic is " +
							"immediately recognized when he turns two, and the magic tutor Roxy Migurdia is brought" +
							" to his home to refine him into a mage. With a tremendous amount of magical power and a " +
							"wealth of knowledge from his original world, Rudeus seeks to fulfill his only desires " +
							"from his previous life—to not repeat the mistakes of his past and die with no regrets." +
							" Follow Rudeus from infancy to adulthood, as he struggles to redeem himself in a wondrous " +
							"yet dangerous world."
			));
		};
	}

	@Bean
	public CommandLineRunner presetStreams(final StreamsRepository streamsRepository) {
		return (args) -> {
			streamsRepository.save(new Streams(1, "https://www.crunchyroll.com/","Crunchyroll"));
			streamsRepository.save(new Streams(2, "https://www.funimation.com/","Funimation"));
			streamsRepository.save(new Streams(3, "https://www.netflix.com/","Netflix"));
		};
	}

	@Bean
	public CommandLineRunner presetRoles(final RoleRepository roleRepository) {
		return (args) -> {
			roleRepository.save(new Role(1, ERole.ROLE_USER));
			roleRepository.save(new Role(2, ERole.ROLE_ADMIN));
		};
	}

}
