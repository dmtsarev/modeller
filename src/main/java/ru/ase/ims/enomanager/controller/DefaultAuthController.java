package ru.ase.ims.enomanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ase.ims.enomanager.api.v1.dto.JwtRequestDTO;
import ru.ase.ims.enomanager.api.v1.dto.JwtResponseDTO;
import ru.ase.ims.enomanager.service.EnoManagerUserDetailsService;
import ru.ase.ims.enomanager.config.JwtToken;

@RestController
@RequestMapping(path = "")
public class DefaultAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private JwtToken jwtToken = new JwtToken();

    @Autowired
    private EnoManagerUserDetailsService enoManagerUserDetailsService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {


        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = enoManagerUserDetailsService

                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponseDTO(token));

    }

    private void authenticate(String username, String password) throws Exception {

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);

        }
    }
}
