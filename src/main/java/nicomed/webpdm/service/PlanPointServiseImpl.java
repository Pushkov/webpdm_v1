package nicomed.webpdm.service;

import nicomed.webpdm.models.PlanMonth;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.repository.PlanPointRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
public class PlanPointServiseImpl implements PlanPointServise {

    private final PlanPointRepository planPointRepository;

    public PlanPointServiseImpl(PlanPointRepository planPointRepository) {
        this.planPointRepository = planPointRepository;
    }

    @Override
    public List<PlanPoint> findAll() {
        return planPointRepository.findAll();
    }

    @Override
    public List<PlanPoint> findByPlanmonth(PlanMonth plan) {
        return planPointRepository.findByPlanmonth(plan);
    }

    @Override
    public PlanPoint getOne(Long id) {
        return planPointRepository.getOne(id);
    }

//    @Override
//    public List<PlanPoint> findAllByPlanmonthMonthIn(Month month) {
//        return planPointRepository.findAllByMonthInPlanmonth(month);
//    }


//    @Override
//    public List<PlanPoint> findAllByPlanmonthYearValueIn(Year year) {
//        return planPointRepository.findAllByPlanmonthYearValueIn(year);
//    }

    @Override
    public void save(PlanPoint planPoint) {
        planPointRepository.save(planPoint);
    }

    @Override
    public void delete(PlanPoint planPoint) {
        planPointRepository.delete(planPoint);
    }
}
