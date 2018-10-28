package com.xlscoder.boot;

import com.xlscoder.model.Key;
import com.xlscoder.model.Role;
import com.xlscoder.model.User;
import com.xlscoder.repository.KeyRepository;
import com.xlscoder.repository.RoleRepository;
import com.xlscoder.repository.UserRepository;
import com.xlscoder.security.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired private KeyRepository keyRepository;

    @Override
    public void run(String... strings) throws Exception {
        Role adminRole = new Role();
        adminRole.setName(Roles.ROLE_ADMIN.toString());
        adminRole.setHumanReadableName("Администратор");
        roleRepository.save(adminRole);

        Role producerRole = new Role();
        producerRole.setName(Roles.ROLE_PRODUCER.toString());
        producerRole.setHumanReadableName("Исчтоник данных");
        roleRepository.save(producerRole);

        Role consumerRole = new Role();
        consumerRole.setName(Roles.ROLE_CONSUMER.toString());
        consumerRole.setHumanReadableName("Потребитель данных");
        roleRepository.save(consumerRole);

        Role noobRole = new Role();
        noobRole.setName(Roles.ROLE_NOOB.toString());
        noobRole.setHumanReadableName("Новозарегистрированный пользователь");
        roleRepository.save(noobRole);


        User adminUser = new User();
        adminUser.setLogin("admin");
        adminUser.setPassword(bCryptPasswordEncoder.encode("admin"));
        adminUser.getRoles().add(adminRole);
        adminUser.setUserName("Admin");
        adminUser.setUserSurname("Admin");
        adminUser.setUserEmail("oleg@graalvm.io");
        userRepository.save(adminUser);

        User producerUser = new User();
        producerUser.setLogin("producer");
        producerUser.setPassword(bCryptPasswordEncoder.encode("producer"));
        producerUser.getRoles().add(producerRole);
        producerUser.setUserName("Vladimir");
        producerUser.setUserSurname("Putin");
        producerUser.setUserEmail("v@fsb.ru");
        userRepository.save(producerUser);

        User consumerUser = new User();
        consumerUser.setLogin("consumer");
        consumerUser.setPassword(bCryptPasswordEncoder.encode("producer"));
        consumerUser.getRoles().add(consumerRole);
        consumerUser.setUserName("Dmitriy");
        consumerUser.setUserSurname("Medvedev");
        consumerUser.setUserEmail("medvedko@fsb.ru");
        userRepository.save(consumerUser);
    }
}
