package com.beta.limited.repository;

import com.beta.limited.entity.Report;
import com.beta.limited.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {
    @Query("select report from Report report where report.runner = ?1 and report.deliveryDate = ?2 order by report.executed, report.address.routing")
    List<Report> findAllByRunnerAndDeliveryDateEqualsOrderByExecuted(User runner, Date date);

    List<Report> findAllByDeliveryDateEqualsOrderByExecuted(Date date);

    void deleteAllByRunner(User runner);
}
