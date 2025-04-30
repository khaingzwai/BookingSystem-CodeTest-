package com.codetest.bookingsystem.service;

import com.codetest.bookingsystem.controller.SessionContext;
import com.codetest.bookingsystem.dto.PurchaseResponseDTO;
import com.codetest.bookingsystem.dto.PurchaseUserPackageDTO;
import com.codetest.bookingsystem.dto.UserPackageDTO;
import com.codetest.bookingsystem.enums.UserPackageStatus;
import com.codetest.bookingsystem.interfaces.UserPackageService;
import com.codetest.bookingsystem.job.UserPackageExpirationJob;
import com.codetest.bookingsystem.mapper.SchedulerClassMapper;
import com.codetest.bookingsystem.mapper.UserPackageMapper;
import com.codetest.bookingsystem.model.Packages;
import com.codetest.bookingsystem.model.ScheduleClass;
import com.codetest.bookingsystem.model.UserPackage;
import com.codetest.bookingsystem.repository.PackageRepository;
import com.codetest.bookingsystem.repository.ScheduleClassRepository;
import com.codetest.bookingsystem.repository.UserPackageRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Calendar;
import java.util.stream.Collectors;

@Service
public class UserPackageServiceImpl implements UserPackageService {

    @Autowired
    private UserPackageRepository userPackageRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private Scheduler scheduler;

    @Override
    public UserPackageDTO getUserPackageById(SessionContext sessionContext,Long id) {
        try {
            UserPackage pEntity = userPackageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User Package not found"));
            return UserPackageMapper.toDTO(pEntity);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @CachePut(value = "package", key = "#purchaseUserPackageDTO.packageId")
    public PurchaseResponseDTO purchaseUserPackage(SessionContext sessionContext,Long userId, PurchaseUserPackageDTO purchaseUserPackageDTO) {
        try {
            if (purchaseUserPackageDTO.getPackageId() != null) {
                Packages pEntity = packageRepository.findById(purchaseUserPackageDTO.getPackageId())
                        .orElseThrow(() -> new RuntimeException("User Package not found"));

                boolean paymentSuccess = paymentCard();
                if (paymentSuccess) {
                    UserPackage userPackage = new UserPackage();
                    userPackage.setPackageId(purchaseUserPackageDTO.getPackageId());
                    userPackage.setPackageName(pEntity.getName());
                    Date currentDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.DAY_OF_MONTH, pEntity.getNoOfAvailableDays());
                    Date newDate = calendar.getTime();
                    userPackage.setExpireDate(newDate);
                    userPackage.setCostOfAmount(pEntity.getCostOfAmount());
                    userPackage.setNoOfRemainCredits(pEntity.getNoOfAvailableCredits());
                    userPackage.setNoOfCredits(pEntity.getNoOfAvailableCredits());
                    userPackage.setUserPackageStatus(UserPackageStatus.AVAILABLE);

                    userPackage.setCountryId(pEntity.getCountry().getId());
                    
                    userPackage.setUserId(sessionContext.getUserId());
                    userPackageRepository.save(userPackage);

                    JobDetail jobDetail = JobBuilder.newJob(UserPackageExpirationJob.class)
                            .withIdentity("expire-job-" + userPackage.getId())  // Unique job key
                            .usingJobData("userPackageId", userPackage.getId())  // Pass the userPackageId
                            .build();

                    Trigger trigger = TriggerBuilder.newTrigger()
                            .withIdentity("expire-trigger-" + userPackage.getId())
                            .startAt(userPackage.getExpireDate())  // Start the job when expireDate arrives
                            .build();

                    scheduler.scheduleJob(jobDetail, trigger);

                    PurchaseResponseDTO purchaseResponseDTO = new PurchaseResponseDTO();
                    purchaseResponseDTO.setMessage("Success");
                    purchaseResponseDTO.setSuccess(true);
                    return purchaseResponseDTO;
                }

            }
            return null;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error processing purchase: " + e.getMessage(), e);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean paymentCard() {
        return true;
    }

    @Override
    public List<UserPackageDTO> getAllUserPackagesByUserId(SessionContext sessionContext,Long userId) {
        try {
            Optional<List<UserPackage>> optionalUserPackages = userPackageRepository.findByUserId(userId);
            if (optionalUserPackages.isPresent()) {
                List<UserPackageDTO> userPackageDTOs = optionalUserPackages.get().stream()
                        .map(UserPackageMapper::toDTO)  // Convert each UserPackage to UserPackageDTO
                        .collect(Collectors.toList());
                return userPackageDTOs;
            } else {
                return new ArrayList<>();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Error retrieving user packages", e);
        }
    }


    @Override
    public UserPackageDTO updateUserPackageClass(SessionContext sessionContext,Long id, UserPackageDTO userPackageDTO) {
        return null;
    }

    @Override
    public List<UserPackageDTO> getAllUserPackages(SessionContext sessionContext) {
        try {
            List<UserPackage> userPackageDTOS = userPackageRepository.findAll();
            return userPackageDTOS.stream().map(UserPackageMapper::toDTO).collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Error retrieving user packages", e);
        }
    }
}
