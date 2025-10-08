package br.com.unex.edutrack.model;

import br.com.unex.edutrack.dto.user.UserRequestDto;
import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id",updatable = false,nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String nome;

    @Column(name= "email", nullable = false,unique = true)
    private String email;

    @Column(name= "password",nullable = false)
    private String password;


    public User() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static class UserBuilder{
        private String name;
        private String email;
        private String password;

        public UserBuilder comName(String name){
            this.name = name;
            return this;
        }

        public UserBuilder comEmail(String email){
            this.email = email;
            return this;
        }

        public UserBuilder comPassword(String password){
            this.password = password;
            return this;
        }

        public User build(){
            User user = new User();
            user.setNome(this.name);
            user.setEmail(this.email);
            user.setPassword(this.password);
            return user;
        }
    }

    public void atualizarCom(UserRequestDto data){
        this.nome = data.nome();
        this.email = data.email();
    }
}
