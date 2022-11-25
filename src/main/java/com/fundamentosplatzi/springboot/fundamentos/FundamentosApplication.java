package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyOperationImplement;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import com.fundamentosplatzi.springboot.fundamentos.services.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private UserPojo userPojo;

	private UserRepository userRepository;

	private UserService userService;

	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
								  MyBean myBean, MyBeanWithDependency myBeanWithDependency, UserPojo userPojo,
								  UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;

	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();
		saveWithErrorTransactional();
	}

		private void saveWithErrorTransactional(){
			User test1 = new User("TestTransactional1", "TestTransactional1@gmail.com", LocalDate.now());
			User test2 = new User("TestTransactional2", "TestTransactional2@gmail.com", LocalDate.now());
			User test3 = new User("TestTransactional3", "TestTransactional3@gmail.com", LocalDate.now());
			User test4 = new User("TestTransactional4", "TestTransactional4@gmail.com", LocalDate.now());

			List<User> users = Arrays.asList(test1,test2,test3, test4);

			try {
				userService.saveTransactional(users);
			} catch (Exception e) {
				LOGGER.error("Esto es una excepcion dentro del metodo transaccional" + e);
			}

			userService.getAllUsers()
					.stream()
					.forEach(user -> LOGGER.info("Este es el usuario dentro del metodo transaccional "+ user));
	}

		private void getInformationJpqlFromUser(){
		/*LOGGER.info("User con el metodo findByUserEmail: "+
				userRepository.findByUserEmail("maria@hmail.com")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario ")));

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("usuario con metodo sort"+ user) );

		userRepository.findByName("john")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query Method" + user));

		LOGGER.info(userRepository.findByEmailAndName("user3@hmail.com", "user3")
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado")));

		userRepository.findByNameLike("%user%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario findByNameLike:" + user));

		userRepository.findByNameOrEmail(null,"%user%")
					.stream()
					.forEach(user -> LOGGER.info("Usuario findByNameOrEmail:" + user));*/

		userRepository.findByBirthdayDateBetween(LocalDate.of(2010,03,20), LocalDate.of(2022,24,11))
					.stream()
					.forEach(user -> LOGGER.info("Usuario con intervalo de fechas:" + user));

		userRepository.findByNameLikeOrderByIdDesc("%user4%")
				.stream()
				.forEach( user -> System.out.println(("usuarios encontrados con like y ordenados")));

		userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021,01,01), "daniela")
				.orElseThrow(() ->
						new RuntimeException("No se encontro el parametro a apartir del named parameter" +
								"") );

	}

	private void saveUsersInDataBase(){
		User user1 = new User( "John", "John@hmail.com", LocalDate.of(2023,05,05));
		User user2 = new User( "Maria", "maria@hmail.com", LocalDate.of(2010,9,30));
		User user3 = new User( "user3", "user3@hmail.com", LocalDate.of(2000,1,2));
		User user4 = new User( "user4", "user4@hmail.com", LocalDate.of(2021,7,25));
		User user5 = new User( "user5", "user5@hmail.com", LocalDate.of(1995,10,9));

		List<User> list = Arrays.asList(user1, user2, user3, user4, user5);
		list.stream().forEach(userRepository::save);

	}

	private void ejemplosAnteriores(){
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(userPojo.getEmail()+"-"+userPojo.getPassword());
		LOGGER.error("Esto es un error del aplicativo");
	}
}
