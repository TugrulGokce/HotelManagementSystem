/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpringCrud.HotelSpring.repository;

import SpringCrud.HotelSpring.model.Rezervation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RezervationRepository extends CrudRepository<Rezervation, Long> {

}
