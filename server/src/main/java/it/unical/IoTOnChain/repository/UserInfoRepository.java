package it.unical.IoTOnChain.repository;

import it.unical.IoTOnChain.data.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
  Optional<UserInfo> findByUsername(String username);

  Optional<UserInfo> findByEmail(String email);

  Optional<UserInfo> findByKeycloakUsername(String keycloakUsername);

  Optional<UserInfo> findByKeycloakId(String keycloakId);
}
