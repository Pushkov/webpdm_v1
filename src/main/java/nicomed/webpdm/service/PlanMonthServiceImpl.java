package nicomed.webpdm.service;

import nicomed.webpdm.models.PlanMonth;
import nicomed.webpdm.repository.PlanMonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PlanMonthServiceImpl implements PlanMonthService {

    final private PlanMonthRepository planMonthRepository;

    public PlanMonthServiceImpl(PlanMonthRepository planMonthRepository) {
        this.planMonthRepository = planMonthRepository;
    }

    @Override
    public PlanMonth getOne(Long id) {
        return planMonthRepository.getOne(id);
    }

    @Override
    public List<PlanMonth> findAll() {
        return planMonthRepository.findAll();
    }

    @Override
    public void save(PlanMonth plan) {
        planMonthRepository.save(plan);
    }

    @Override
    public void delete(PlanMonth plan) {
        planMonthRepository.delete(plan);
    }

    @Override
    public PlanMonth findByMonthAndYear(int month, int year) {
        return planMonthRepository.findByMonthAndYear(month, year);
    }

    @Override
    public List<PlanMonth> findAllByYear(int year) {
        return planMonthRepository.findAllByYear(year);
    }
}
