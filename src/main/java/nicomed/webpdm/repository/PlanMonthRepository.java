package nicomed.webpdm.repository;

import nicomed.webpdm.models.PlanMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public interface PlanMonthRepository extends JpaRepository<PlanMonth, Long> {

    PlanMonth findByMonthAndYear(int month, int year);
    List<PlanMonth> findAllByYear(int year);
//    PlanMonth findByDateMonthAndDateYear(int month, int year);

}
