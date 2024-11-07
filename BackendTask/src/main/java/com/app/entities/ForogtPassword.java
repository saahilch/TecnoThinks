package com.app.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.core.env.Profiles;

@Entity
@Table(name = "forogtpassword")
public class ForogtPassword extends Profile {

}
