package lessons.spring.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Shape {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long shapeId;

  @Column
  private String name;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "shapeId")
  private List<Property> properties = new ArrayList<>();

  public Long getShapeId() {
    return shapeId;
  }

  public void setShapeId(Long shapeId) {
    this.shapeId = shapeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

}
