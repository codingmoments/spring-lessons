CREATE TABLE shape (
  shape_id INT AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR2(50) NOT NULL
);

CREATE TABLE property (
  property_id     INT AUTO_INCREMENT PRIMARY KEY,
  shape_id        INT NOT NULL,
  property_key    VARCHAR2(50) NOT NULL,
  property_value  VARCHAR2(50) NOT NULL,
  FOREIGN KEY ( shape_id ) REFERENCES shape ( shape_id ),
  UNIQUE (shape_id, property_key)  
);
