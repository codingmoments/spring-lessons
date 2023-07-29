package lessons.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lessons.spring.domain.Property;
import lessons.spring.domain.Shape;
import lessons.spring.service.ShapeService;

@RestController
@RequestMapping("/shapes")
public class ShapeController {

  private final ShapeService shapeService;

  @Autowired
  public ShapeController(ShapeService shapeService) {
    this.shapeService = shapeService;
  }

  @PostMapping
  public Shape createShape(@RequestBody Shape shape) {
    return shapeService.createShape(shape);
  }

  @GetMapping
  public List<Shape> getAllShapes() {
    return shapeService.getAllShapes();
  }

  @GetMapping("/{shapeId}")
  public ResponseEntity<Shape> getShapeById(@PathVariable Long shapeId) {
    return shapeService.getShapeById(shapeId);
  }

  @PutMapping("/{shapeId}/properties")
  @Transactional
  public ResponseEntity<List<Property>> updateShapeProperties(@PathVariable Long shapeId, @RequestBody List<Property> properties) {
    return shapeService.updateShapeProperties(shapeId, properties);
  }
}
