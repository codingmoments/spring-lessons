package lessons.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lessons.spring.domain.Shape;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Long> {
}
