package project.realestate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.realestate.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
