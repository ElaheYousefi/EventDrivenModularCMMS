package domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DefineTaskRepository extends JpaRepository<DefinedTask, Integer> {
}
