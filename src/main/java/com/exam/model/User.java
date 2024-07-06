package com.exam.model;

import com.exam.model.exam.Providers;
import com.exam.model.exam.Report;
import com.exam.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
  private Integer id;

  @Column( nullable = false)
  private String firstname;
  @Column(nullable = false)
  private String lastname;
  @Column(unique = true, nullable = false)
  private String email;
  private String password;
  @Column(unique = true, nullable = false)
  private String username;
  private String phone;
  private boolean emailVerified = false;
  private boolean phoneVerified = false;
  private boolean enabled = true;
  @Column(length = 1000)
  private String profilePic;
  @Enumerated(EnumType.STRING)
  private Role role;
  @Enumerated(value = EnumType.STRING)
  private Providers provider = Providers.SELF;
  private String providerUserId;
  @Column(length = 1000)
  private String about;


//Trying to check for one quiz attempts

  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Report> reports = new LinkedHashSet<>();


  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    this.emailVerified = emailVerified;
  }

  public boolean isPhoneVerified() {
    return phoneVerified;
  }

  public void setPhoneVerified(boolean phoneVerified) {
    this.phoneVerified = phoneVerified;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }

  public Providers getProvider() {
    return provider;
  }

  public void setProvider(Providers provider) {
    this.provider = provider;
  }

  public String getProviderUserId() {
    return providerUserId;
  }

  public void setProviderUserId(String providerUserId) {
    this.providerUserId = providerUserId;
  }

  public String getAbout() {
    return about;
  }

  public void setAbout(String about) {
    this.about = about;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
