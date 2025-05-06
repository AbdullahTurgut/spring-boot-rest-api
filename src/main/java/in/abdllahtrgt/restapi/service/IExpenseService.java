package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.ExpenseDTO;

import java.util.List;

public interface IExpenseService {

    List<ExpenseDTO> getAllExpenses();

    
}
