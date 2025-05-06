package in.abdllahtrgt.restapi.service;

import in.abdllahtrgt.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 * Service interface for Expense module
 *
 * @author Abdullah
 */
public interface IExpenseService {

    /**
     * It will fetch the expenses from db
     *
     * @return list
     */
    List<ExpenseDTO> getAllExpenses();


}
