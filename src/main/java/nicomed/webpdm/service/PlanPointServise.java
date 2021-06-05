package nicomed.webpdm.service;

import nicomed.webpdm.models.PlanMonth;
import nicomed.webpdm.models.PlanPoint;

import java.time.Month;
import java.time.Year;
import java.util.List;


public interface PlanPointServise {
    List<PlanPoint> findAll();
    List<PlanPoint> findByPlanmonth(PlanMonth plan);
    PlanPoint getOne(Long id);
//    List<PlanPoint> findAllByPlanmonthMonthIn(Month month);
//    List<PlanPoint> findAllByPlanmonthYearValueIn(Year year);
    void save(PlanPoint planPoint);
    void delete(PlanPoint planPoint);
}
