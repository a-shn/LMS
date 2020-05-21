package com.company.config;

import com.company.repositories.*;
import com.company.services.*;
import com.company.services.TorrentDownloader;
import com.company.services.TorrentDownloaderImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Component
@Configuration
@ComponentScan(basePackages = "com.company")
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class ApplicationContextConfig {
    @Autowired
    private Environment environment;

    @Bean
    public CourseLessonsRepository courseLessonsRepository() {
        return new CourseLessonsJdbcTemplateImpl(jdbcTemplate());
    }

    @Bean
    public CourseBuilder courseBuilder() {
        return new CourseBuilderImpl();
    }

    @Bean
    public RepositoriesProxy repositoriesProxy() {
        return new RepositoriesProxyImpl(userRepository(), coursesRepository());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SignUpService signUpService() {
        return new SignUpServiceImpl(signUpPool(), userRepository(), emailSender());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public TemplateResolver templateResolver() {
        return new TemplateResolverImpl();
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates");
        freeMarkerConfigurer.setConfiguration(Objects.requireNonNull(freeMarkerConfiguration()));
        return freeMarkerConfigurer;
    }

    @Bean
    public freemarker.template.Configuration freeMarkerConfiguration() {
        return (freemarker.template.Configuration) freeMarkerConfigurationFactoryBean().getObject();
    }

    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
        FreeMarkerConfigurationFactoryBean freeMarkerFactoryBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerFactoryBean.setTemplateLoaderPath("/WEB-INF/templates/");
        freeMarkerFactoryBean.setPreferFileSystemAccess(true);
        freeMarkerFactoryBean.setDefaultEncoding("UTF-8");
        return freeMarkerFactoryBean;
    }

    @Bean
    public TorrentDownloader torrentDownloader() {
        return new TorrentDownloaderImpl();
    }

    @Bean
    public StorageFilenameGenerator storageFilenameGenerator() {
        return new StorageFilenameGeneratorImpl();
    }

    @Bean
    public FileUploader fileUploader() {
        String directory = environment.getProperty("path.filesDirectory");
        return new FileUploaderImpl(directory, fileRepository(), coursesRepository(), storageFilenameGenerator(), torrentDownloader());
    }

    @Bean
    public UsersCoursesRepository usersCoursesRepository() {
        return new UsersCoursesJdbcTemplateImpl(jdbcTemplate());
    }

    @Bean
    public CoursesRepository coursesRepository() {
        return new CoursesJdbcTemplateImpl(jdbcTemplate());
    }

    @Bean
    public FileRepository fileRepository() {
        return new FileJdbcTemplateImpl(jdbcTemplate());
    }

    @Bean
    public ChatRepository chatRepository() {
        return new ChatJdbcTemplateImpl(jdbcTemplate());
    }

    @Bean
    public UsersRepository userRepository() {
        return new UsersJdbcTemplateImpl(jdbcTemplate());
    }

    @Bean
    public EmailSender emailSender() {
        return new EmailSenderImpl();
    }

    @Bean
    public SignUpPool signUpPool() {
        return new SignUpPool(userRepository());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(environment.getProperty("db.url"));
        config.setUsername(environment.getProperty("db.user"));
        config.setPassword(environment.getProperty("db.password"));
        config.setDriverClassName(environment.getProperty("db.driver"));
        return config;
    }

    @Bean
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }
}
