package com.codetest.bookingsystem.job;

import com.codetest.bookingsystem.enums.UserPackageStatus;
import com.codetest.bookingsystem.model.UserPackage;
import com.codetest.bookingsystem.repository.UserPackageRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPackageExpirationJob implements Job {

    @Autowired
    private UserPackageRepository userPackageRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long userPackageId = (Long) context.getJobDetail().getJobDataMap().get("userPackageId");
        UserPackage userPackage = userPackageRepository.findById(userPackageId)
                .orElseThrow(() -> new RuntimeException("UserPackage not found"));
        userPackage.setUserPackageStatus(UserPackageStatus.EXPIRED);
        userPackageRepository.save(userPackage);
    }
}
