package br.com.denys.repository;

import br.com.denys.model.Holiday;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HolidayRepository extends MongoRepository<Long, Holiday> {
}
