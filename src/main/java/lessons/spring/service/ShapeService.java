package lessons.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import lessons.spring.domain.Property;
import lessons.spring.domain.Shape;
import lessons.spring.repository.PropertyRepository;
import lessons.spring.repository.ShapeRepository;

@Service
public class ShapeService {
  private final ShapeRepository shapeRepository;
  private final PropertyRepository propertyRepository;
  private final EntityManager entityManager;

  @Autowired
  public ShapeService(ShapeRepository shapeRepository, PropertyRepository propertyRepository, EntityManager entityManager) {
    this.shapeRepository = shapeRepository;
    this.propertyRepository = propertyRepository;
    this.entityManager = entityManager;
  }

  public Shape createShape(Shape shape) {
    shapeRepository.save(shape);

    shape.getProperties().forEach(prop -> {
      prop.setShapeId(shape.getShapeId());
      propertyRepository.save(prop);
    });

    return shape;
  }

  public List<Shape> getAllShapes() {
    return shapeRepository.findAll();
  }

  public ResponseEntity<Shape> getShapeById(Long id) {
    Optional<Shape> shape = shapeRepository.findById(id);
    return shape.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  public ResponseEntity<List<Property>> updateShapeProperties(Long shapeId, List<Property> properties) {
    Optional<Shape> dbShapeOptional = shapeRepository.findById(shapeId);
    if (dbShapeOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Shape dbShape = dbShapeOptional.get();

    List<Property> resultProperties = new ArrayList<>();

    // resultProperties = deleteAndAddProperties(dbShape, properties);

    // resultProperties = deleteFlushAndAddProperties(dbShape, properties);

    resultProperties = mergeProperties(dbShape, properties);

    return ResponseEntity.ok(resultProperties);
  }

  @SuppressWarnings("unused")
  private List<Property> deleteAndAddProperties(Shape dbShape, List<Property> properties) {
    // Deleting all the existing properties for the shape
    dbShape.getProperties().forEach(prop -> propertyRepository.deleteById(prop.getPropertyId()));

    // Inserting new properties for the shape
    properties.forEach(prop -> {
      prop.setShapeId(dbShape.getShapeId());
      propertyRepository.save(prop);
      dbShape.getProperties().add(prop);
    });

    return properties;
  }

  @SuppressWarnings("unused")
  private List<Property> deleteFlushAndAddProperties(Shape dbShape, List<Property> properties) {
    // Deleting all the existing properties for the shape
    dbShape.getProperties().forEach(prop -> propertyRepository.deleteById(prop.getPropertyId()));

    entityManager.flush();

    // Inserting new properties for the shape
    properties.forEach(prop -> {
      prop.setShapeId(dbShape.getShapeId());
      propertyRepository.save(prop);
      dbShape.getProperties().add(prop);
    });

    return properties;
  }

  private List<Property> mergeProperties(Shape dbShape, List<Property> properties) {
    Map<String, Property> newKeyToPropertyMap = properties.stream().collect(Collectors.toMap(Property::getPropertyKey, Function.identity()));
    Map<String, Property> existingKeyToPropertyMap = dbShape.getProperties().stream().collect(Collectors.toMap(Property::getPropertyKey, Function.identity()));

    List<Property> resultProperties = new ArrayList<>();

    // Deleting the properties that are not present in the request body
    dbShape.getProperties().stream().filter(prop -> !newKeyToPropertyMap.containsKey(prop.getPropertyKey())).forEach(prop -> propertyRepository.deleteById(prop.getPropertyId()));
    // Updating the existing properties present in the request body
    dbShape.getProperties().stream().filter(prop -> newKeyToPropertyMap.containsKey(prop.getPropertyKey())).forEach(prop -> {
      Property newProperty = newKeyToPropertyMap.get(prop.getPropertyKey());
      prop.setPropertyValue(newProperty.getPropertyValue());
      resultProperties.add(prop);
    });
    // Inserting the properties not present in database
    properties.stream().filter(prop -> !existingKeyToPropertyMap.containsKey(prop.getPropertyKey())).forEach(prop -> {
      prop.setShapeId(dbShape.getShapeId());
      propertyRepository.save(prop);
      resultProperties.add(prop);
    });

    return resultProperties;
  }

}
