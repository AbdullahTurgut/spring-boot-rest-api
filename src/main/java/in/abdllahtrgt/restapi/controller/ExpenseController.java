package in.abdllahtrgt.restapi.controller;

import in.abdllahtrgt.restapi.dto.ExpenseDTO;
import in.abdllahtrgt.restapi.response.ExpenseResponse;
import in.abdllahtrgt.restapi.service.IExpenseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final IExpenseService expenseService;
    private final ModelMapper modelMapper;

    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses() {
        // call the service method
        List<ExpenseDTO> expenseList = expenseService.getAllExpenses();
        // Convert expenseDTO to expenseResponse
        List<ExpenseResponse> responseList = expenseList.stream()
                .map(expenseDTO -> mapToResponse(expenseDTO))
                .collect(Collectors.toList());
        // return the list
        return responseList;
    }

    private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
    }

}
