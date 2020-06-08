package br.com.apirest.users.aceptancetest.stepDefinitions;

import br.com.apirest.users.UsersApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(
        classes = UsersApplication.class,
        loader = SpringBootContextLoader.class
)
public class AppContextConfig {
}
