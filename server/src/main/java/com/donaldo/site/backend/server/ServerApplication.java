package com.donaldo.site.backend.server;

import com.donaldo.site.backend.server.models.*;
import com.donaldo.site.backend.server.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		System.out.println("Running");
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/spring/comment/**").allowedOrigins("http://localhost:3000");
//			}
//		};
//	}


	final PasswordEncoder encoder;
	public ServerApplication(final PasswordEncoder encoder) {
		this.encoder = encoder;
	}

	final User admin = new User("admin", "admin");
	final User donaldo = new User("donaldo", "donaldo");

	final Titles mushoku = new Titles(
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
	);

	@Bean
	public CommandLineRunner presetRoles(final RoleRepository roleRepository) {
		return (args) -> {
			roleRepository.save(new Role(1, ERole.ROLE_USER));
			roleRepository.save(new Role(2, ERole.ROLE_ADMIN));
		};
	}

	@Bean
	public CommandLineRunner presetUsers(final UserRepository userRepository, final RoleRepository roleRepository) {
		return args -> {
			final Optional<User> adminCopy = userRepository.findByUsername(admin.getUsername());
			if (adminCopy.isPresent()) {
				admin.setPassword(adminCopy.get().getPassword());
				admin.setId(adminCopy.get().getId());
				admin.setRoles(adminCopy.get().getRoles());
			} else {
				admin.setPassword(encoder.encode(admin.getPassword()));

				final Set<Role> roles = new HashSet<>();
				roles.add(roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("ROLE_USER not found at initialize")));
				roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found at initialize")));
				admin.setRoles(roles);

				userRepository.save(admin);
			}

			final Optional<User> donaldoCopy = userRepository.findByUsername(donaldo.getUsername());
			if (donaldoCopy.isPresent()) {
				donaldo.setId(donaldoCopy.get().getId());
				donaldo.setRoles(donaldoCopy.get().getRoles());
			} else {
				donaldo.setPassword(encoder.encode(donaldo.getPassword()));

				final Set<Role> roles = new HashSet<>();
				roles.add(roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("ROLE_USER not found at initialize")));
				donaldo.setRoles(roles);

				userRepository.save(donaldo);
			}
		};
	}

	@Bean
	public CommandLineRunner presetTitles(final TitlesRepository titlesRepository) {
		return (args) -> {
			titlesRepository.save(mushoku);
			titlesRepository.save(new Titles(
					2,
					"Steins; Gate",
					"//s1.zerochan.net/Steins%3BGate.600.274963.jpg",
					"The self-proclaimed mad scientist Rintarou Okabe rents out a room in a rickety old " +
							"building in Akihabara, where he indulges himself in his hobby of inventing prospective " +
							"\"future gadgets\" with fellow lab members: Mayuri Shiina, his air-headed childhood " +
							"friend, and Hashida Itaru, a perverted hacker nicknamed \"Daru.\" The three pass the " +
							"time by tinkering with their most promising contraption yet, a machine dubbed the " +
							"\"Phone Microwave,\" which performs the strange function of morphing bananas into piles " +
							"of green gel.\n" +
							"\n" +
							"Though miraculous in itself, the phenomenon doesn't provide anything concrete in Okabe's " +
							"search for a scientific breakthrough; that is, until the lab members are spurred into " +
							"action by a string of mysterious happenings before stumbling upon an unexpected success " +
							"— the Phone Microwave can send emails to the past, altering the flow of history.\n" +
							"\n" +
							"Adapted from the critically acclaimed visual novel by 5pb. and Nitroplus, Steins;Gate " +
							"takes Okabe through the depths of scientific theory and practicality. Forced across " +
							"the diverging threads of past and present, Okabe must shoulder the burdens that come " +
							"with holding the key to the realm of time.\n"
			));
			titlesRepository.save(new Titles(
					3,
					"Overlord",
					"https://external-preview.redd.it/7OBYJFbOoaM_ZoBQhMKEM8tF0PeZyvs6SZdOeYtHl5o.png?" +
							"auto=webp&s=4382f2f2ffaae8f34925a4d293e62a7804df1377",
					"The story begins with Yggdrasil, a popular online game which is quietly shut down one" +
							" day; however, the protagonist Momonga decides to not log out. Momonga is then " +
							"transformed into the image of a skeleton as \"the most powerful wizard.\" The world " +
							"continues to change, with non-player characters (NPCs) begining to feel emotion. Having " +
							"no parents, friends, or place in society, this ordinary young man Momonga then strives " +
							"to take over the new world the game has become. "
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
	public CommandLineRunner presetComments(final CommentsRepository commentsRepository) {
		return (args) -> {
			if (commentsRepository.findById(1).isEmpty()) {
				final Comments adminComment = new Comments(mushoku, admin,
						"You may be used to the Isekai formula by now but Mushoku Tensei is where it started. " +
								"Absolutely give it a try."
				);
				adminComment.setId(1);
				commentsRepository.save(adminComment);
			}

			if (commentsRepository.findById(2).isEmpty()) {
				final Comments donaldoComment = new Comments(mushoku, donaldo,
						"This anime is a gem.\n" +
								"Truly must watch."
				);
				donaldoComment.setId(2);
				commentsRepository.save(donaldoComment);
			}
		};
	}

}
