package sbankpaymentms.exception;

import sbankpaymentms.exception.base.InvalidStateException;
import sbankpaymentms.exception.base.NotFoundException;

import java.io.Serial;

public class NotEnoughBalanceException extends InvalidStateException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "NOT_ENOUGH_BALANCE";

    public NotEnoughBalanceException() {
        super(MESSAGE);
    }

}
