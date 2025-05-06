package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.ExpenseDTO;
import in.abdllahtrgt.restapi.entity.ExpenseEntity;
import in.abdllahtrgt.restapi.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        // call the repository method
        List<ExpenseEntity> list = expenseRepository.findAll();
        //convert the entity object to dto object
        List<ExpenseDTO> listOfExpenses = list.stream()
                .map(this::mapToExpenseDTO)
                .collect(Collectors.toList());
        // return the list
        return listOfExpenses;
    }

    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
}
