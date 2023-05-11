package platform.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import platform.entity.Code;

import java.util.UUID;

public interface CodeRepository extends JpaRepository<Code, String> {
}
