package sbankpaymentms.exception;

import sbankpaymentms.exception.base.InvalidStateException;

import java.io.Serial;

public class RefundAmountConflictException extends InvalidStateException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "REFUND_AMOUNT_CONFLICT";

    public RefundAmountConflictException() {
        super(MESSAGE);
    }

}
