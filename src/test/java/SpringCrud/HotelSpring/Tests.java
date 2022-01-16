/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpringCrud.HotelSpring;

import SpringCrud.HotelSpring.model.User;
import SpringCrud.HotelSpring.repository.RezervationRepository;
import SpringCrud.HotelSpring.repository.RoomRepository;
import SpringCrud.HotelSpring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class Tests {

    @Autowired
    private UserRepository repo;

    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private RezervationRepository reservRepo;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("us12");
        user.setEnabled(true);
        repo.save(user);
    }
}
