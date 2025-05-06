package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.ExpenseDTO;
import in.abdllahtrgt.restapi.entity.ExpenseEntity;
import in.abdllahtrgt.restapi.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Expense module
 *
 * @author Abdullah
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the expenses from db
     *
     * @return list
     */
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // call the repository method
        List<ExpenseEntity> list = expenseRepository.findAll();
        log.info("Printing the data from repository {}", list);
        //convert the entity object to dto
        List<ExpenseDTO> listOfExpenses = list.stream()
                .map(this::mapToExpenseDTO)
                .collect(Collectors.toList());
        // return the list
        return listOfExpenses;
    }

    /**
     * Mapper method for converting expense entity object to expense dto
     *
     * @param expenseEntity
     * @return expenseDto
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}
