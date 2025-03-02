package lessons.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lessons.spring.domain.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
