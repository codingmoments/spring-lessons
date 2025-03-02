package lessons.spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Property {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long propertyId;

  @Column
  private String propertyKey;

  @Column
  private String propertyValue;

  @Column
  private Long shapeId;

  public Long getPropertyId() {
    return propertyId;
  }

  public void setPropertyId(Long propertyId) {
    this.propertyId = propertyId;
  }

  public String getPropertyKey() {
    return propertyKey;
  }

  public void setPropertyKey(String propertyKey) {
    this.propertyKey = propertyKey;
  }

  public String getPropertyValue() {
    return propertyValue;
  }

  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
  }

  public Long getShapeId() {
    return shapeId;
  }

  public void setShapeId(Long shapeId) {
    this.shapeId = shapeId;
  }

}
