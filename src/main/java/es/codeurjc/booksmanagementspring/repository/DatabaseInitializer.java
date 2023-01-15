package es.codeurjc.booksmanagementspring.repository;

import es.codeurjc.booksmanagementspring.model.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DatabaseInitializer {
    private final BookRepository bookRepository;

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    public DatabaseInitializer(BookRepository bookRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {

        Role initAdminRole = new Role(ERole.ROLE_ADMIN);
        Role initUserRole = new Role(ERole.ROLE_USER);
        roleRepository.save(initAdminRole);
        roleRepository.save(initUserRole);


        User admin = new User("admin", "jesus@gmail.com", encoder.encode("secure"));


        Set<Role> rolesAdmin = new HashSet<>();
        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        rolesAdmin.add(adminRole);
        admin.setRoles(rolesAdmin);


        User user = new User("user", "alex@gmail.com", encoder.encode("secure"));

        Set<Role> rolesUser = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        rolesUser.add(userRole);
        user.setRoles(rolesUser);


        Book book1 = new Book("Tomás Nevinson",
                "Dos hombres, uno en la ficción y otro en la realidad...",
                "Javier Marías",
                "Alfaguara",
                "2021");

        Book book2 = new Book("Los vencejos",
                "Una espléndida novela humanista sobre la dignidad y la esperanza",
                "Fernando Aramburu",
                "TUSQUETS EDITORES",
                "2021");

        Review review1 = new Review("Me ha gustado", 4, user, book1);
        Review review2 = new Review("No me ha gustado", 1, user, book2);
        Review review3 = new Review( "Un libro muy interesante", 3, admin, book1);
        Review review4 = new Review("Muy aburrido", 1, admin, book2);

        book1.getReviews().add(review1);
        book1.getReviews().add(review3);
        book2.getReviews().add(review2);
        book2.getReviews().add(review4);

        user.getReviews().add(review1);
        user.getReviews().add(review2);
        admin.getReviews().add(review3);
        admin.getReviews().add(review4);

        userRepository.save(user);
        userRepository.save(admin);

        bookRepository.save(book1);
        bookRepository.save(book2);

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
        reviewRepository.save(review4);
    }
}
