package org.msa.hub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class HubApplicationTests {

    @Autowired
    private DataSource dataSource;

	@Test
	void contextLoads() {
	}

    @Test
    void dbConnectionTest(){
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
