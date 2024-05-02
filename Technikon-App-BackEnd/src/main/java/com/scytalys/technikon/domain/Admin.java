package com.scytalys.technikon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Admin extends User {
    // What fields should the admins have compared to normal users?
    // Should they be able to add repairs and have their own properties
    // linked to the Admin account? or a separate account as a normal user?
    // If not should they sign Up with their TIN or Autoincrement id by the DB?
}
