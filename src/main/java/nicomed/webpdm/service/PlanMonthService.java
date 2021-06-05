package nicomed.webpdm.service;

import nicomed.webpdm.models.PlanMonth;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface PlanMonthService {
    PlanMonth getOne(Long id);
    List<PlanMonth> findAll();
    void save(PlanMonth plan);
    void delete(PlanMonth plan);

//    PlanMonth findByDate(Date date);

    PlanMonth findByMonthAndYear(int month, int year);
    List<PlanMonth> findAllByYear(int year);


}
