package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.ExpenseDTO;
import in.abdllahtrgt.restapi.response.ExpenseResponse;
import in.abdllahtrgt.restapi.service.IExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is controller class for Expense module
 *
 * @author Abdullah
 */
@RestController
@RequiredArgsConstructor
@Slf4j // for loggers
public class ExpenseController {
    private final IExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     * It will fetch the expenses from db
     *
     * @return list
     */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses() {
        log.info("API GET /expenses called");
        // call the service method
        List<ExpenseDTO> expenseList = expenseService.getAllExpenses();
        log.info("Printing the data from service {}", expenseList);
        // Convert expenseDTO to expenseResponse
        List<ExpenseResponse> responseList = expenseList.stream()
                .map(expenseDTO -> mapToResponse(expenseDTO))
                .collect(Collectors.toList());
        // return the list
        return responseList;
    }

    /**
     * Mapper method for converting expense dto object to expense response
     *
     * @param expenseDTO
     * @return ExpenseResponse
     */
    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
    }

}
