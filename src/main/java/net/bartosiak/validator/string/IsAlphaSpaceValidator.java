package net.bartosiak.validator.string;

import net.bartosiak.validator.IValidator;
import org.apache.commons.lang.StringUtils;

import java.util.logging.Logger;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public class IsAlphaSpaceValidator implements IValidator {
    private static Logger logger = Logger.getLogger(IsAlphaSpaceValidator.class.getName());

    private String value;

    /**
     * IValidator constructor with "value" to test
     * @param value to test
     */
    public IsAlphaSpaceValidator(String value) {
        this.value = value;
    }

    public boolean validate() {
        boolean result = StringUtils.isAlphaSpace(this.value);
        if (! result) {
            logger.warning("Negative result for value: '" + this.value + '"');
        } else {
            logger.info("OK");
        }
        return result;
    }
}
