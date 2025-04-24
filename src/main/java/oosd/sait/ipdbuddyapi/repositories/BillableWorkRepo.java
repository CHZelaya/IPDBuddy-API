package oosd.sait.ipdbuddyapi.repositories;

import oosd.sait.ipdbuddyapi.entities.BillableWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillableWorkRepo extends JpaRepository<BillableWork, Integer> {
}
