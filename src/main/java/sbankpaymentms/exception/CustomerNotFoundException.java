package sbankpaymentms.exception;

import sbankpaymentms.exception.base.NotFoundException;

import java.io.Serial;

public class CustomerNotFoundException extends NotFoundException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "CUSTOMER_NOT_FOUND";

    public CustomerNotFoundException() {
        super(MESSAGE);
    }

}
