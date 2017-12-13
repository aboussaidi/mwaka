package io.mwaka.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;


@Entity("user")
@Getter
@Setter
public class User {

    @Id
    private String alias = "";
    private String firstName = "";
    private String lastName = "";
    private String passwordHash = "";
    private byte[] passwordSalt;
    private String email = "";
    private String verificationId;
    private boolean activated = false;
    @Transient
    private Subject subject;
    @Transient
    private Datastore datastore;

    public User() {
    }

    public User(String alias, String email, String password,
                ByteSource salt, String firstName, String lastName) {
        setLastName(lastName);
        setFirstName(firstName);
        setAlias(alias);
        setEmail(email);
        String b64 = new Sha256Hash(password, salt, 1024).toBase64();
        setPasswordHash(b64);
        setPasswordSalt(salt.getBytes());
    }
}