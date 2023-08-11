package com.neonex.payment.service.dataaccess.credithistory.adapter;

import com.neonex.domain.valueobject.CustomerId;
import com.neonex.payment.service.dataaccess.credithistory.entity.CreditHistoryEntity;
import com.neonex.payment.service.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper;
import com.neonex.payment.service.dataaccess.credithistory.repository.CreditHistoryJpaRepository;
import com.neonex.payment.service.domain.entity.CreditHistory;
import com.neonex.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    public CreditHistoryRepositoryImpl(CreditHistoryJpaRepository creditHistoryJpaRepository,
                                       CreditHistoryDataAccessMapper creditHistoryDataAccessMapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.creditHistoryDataAccessMapper = creditHistoryDataAccessMapper;
    }

    @Override
    public void save(CreditHistory creditHistory) {
        creditHistoryJpaRepository.save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        Optional<List<CreditHistoryEntity>> creditHistory =
                creditHistoryJpaRepository.findByCustomerId(customerId.id());
        return creditHistory
                .map(creditHistoryList ->
                        creditHistoryList.stream()
                                .map(creditHistoryDataAccessMapper::creditHistoryEntityToCreditHistory)
                                .collect(Collectors.toList()));
    }
}
