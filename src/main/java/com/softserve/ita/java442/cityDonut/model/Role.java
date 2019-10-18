package com.softserve.ita.java442.cityDonut.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "role")

public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;


        @Column(name = "role", length = 25, nullable = false)
        private String role;

        public Role (){

        }

        public Role(long id, String role){
            this.id = id;
            this.role = role;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            com.softserve.ita.java442.cityDonut.model.Role role1 = (com.softserve.ita.java442.cityDonut.model.Role) o;
            return Objects.equals(id, role1.id) &&
                    Objects.equals(role, role1.role);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, role);
        }

        @Override
        public String toString() {
            return "Role{" +
                    "id=" + id +
                    ", role='" + role + '\'' +
                    '}';
        }
    }



