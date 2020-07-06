package ru.ase.ims.enomanager.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.ase.ims.enomanager.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserIMS extends org.springframework.security.core.userdetails.User {

    private final User user;

    public UserIMS(User user) {
        super(user.getUserName(), user.getPassword(), Collections.EMPTY_LIST);
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.getProject() != null) {
            authorities.add(new SimpleGrantedAuthority(user.getProject().getProjectName()));
        }
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return authorities;
    }
}
