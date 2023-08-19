package br.com.denys.repository;

import br.com.denys.model.Holiday;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepository extends MongoRepository<Holiday, String> {

    @Query(value = "{'date':{ $gte: ?0, $lte: ?1}}")
    List<Holiday> findByDateBetween(LocalDate start, LocalDate finish);

    Optional<Holiday> findFirstByDate(LocalDate date);

}
