package in.abdllahtrgt.restapi.handleException;

public class ItemsExistsException extends RuntimeException {

    public ItemsExistsException(String message) {
        super(message);
    }
}
