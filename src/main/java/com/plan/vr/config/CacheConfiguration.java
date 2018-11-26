package com.plan.vr.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.plan.vr.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.plan.vr.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.AkcioniPlan.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.AkcioniPlan.class.getName() + ".kriterijums", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.AkcioniPlan.class.getName() + ".projekats", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.Projekat.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.Projekat.class.getName() + ".projekatBodovanjes", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.Kriterijum.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.Kriterijum.class.getName() + ".kriterijumBodovanjes", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.Kriterijum.class.getName() + ".projekatBodovanjes", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.KriterijumBodovanje.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.ProjekatBodovanje.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.AdminKriterijum.class.getName(), jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.AdminKriterijum.class.getName() + ".adminKriterijumBodovanjes", jcacheConfiguration);
            cm.createCache(com.plan.vr.domain.AdminKriterijumBodovanje.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
