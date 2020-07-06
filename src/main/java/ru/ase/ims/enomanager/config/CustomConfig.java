package ru.ase.ims.enomanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import ru.ase.ims.enomanager.service.git.GitClient;
import ru.ase.ims.enomanager.service.git.SimpleGitClient;

import java.util.function.Supplier;

@Configuration
@EnableAsync
public class CustomConfig {
    @Bean
    public Supplier<GitClient> gitClientSupplier(GitClient gitClient){
        return SimpleGitClient::new;
    }
}
