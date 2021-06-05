package nicomed.webpdm.repository;

import nicomed.webpdm.models.PlanMonth;
import nicomed.webpdm.models.PlanPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface PlanPointRepository extends JpaRepository<PlanPoint, Long> {
    List<PlanPoint> findByPlanmonth(PlanMonth plan);
//    List<PlanPoint> findAllByMonthInPlanmonth(Month month);
//    List<PlanPoint> findAllByPlanmonthYearValueIn(Year year);

}
