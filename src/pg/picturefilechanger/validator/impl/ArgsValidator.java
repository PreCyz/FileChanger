package pg.picturefilechanger.validator.impl;

import pg.constant.ProgramConstants;
import pg.exception.ErrorCode;
import pg.exception.ProgramException;
import pg.helper.MessageHelper;
import pg.picturefilechanger.AbstractFileChanger;
import pg.picturefilechanger.validator.Validator;

import java.util.Properties;
import java.util.ResourceBundle;

import static pg.picturefilechanger.AbstractFileChanger.transformArgumentsToProperties;

/**
 * Created by gawa on 02.03.17.
 */
public class ArgsValidator implements Validator {

    private final String[] args;
    private final MessageHelper messageHelper;

    public ArgsValidator(String[] args, ResourceBundle bundle) {
        this.args = args;
        this.messageHelper = MessageHelper.getInstance(bundle);
    }

    @Override
    public void validate() throws ProgramException {
        argumentsValidation();
        Properties properties = transformArgumentsToProperties(args);
        throwProgramExceptionOnEmptyProperties(properties);
        displayPropertiesDetails(properties);
        throwProgramExceptionWhenPropertiesValidationError(properties);
        displaySourceInfo(properties);
    }

    private void argumentsValidation() throws ProgramException {
        if(args == null || args.length == 0){
            throw new ProgramException(ErrorCode.NO_ARGUMENTS);
        }

        for(String arg : args){
            if(MessageHelper.empty(arg)){
                throw new ProgramException(ErrorCode.NULL_ARGUMENT, arg);
            }
        }
    }

    private void throwProgramExceptionOnEmptyProperties(Properties properties) throws ProgramException {
        if (properties.isEmpty()) {
            System.err.println(messageHelper.getFullMessage("argument.empty"));
            throw new ProgramException(ErrorCode.NO_ARGUMENTS);
        }
    }

    private void displayPropertiesDetails(Properties properties) {
        StringBuilder sb = new StringBuilder(ProgramConstants.LINE_SEPARATOR);
        properties.entrySet().stream().forEach((entry) -> {
            sb.append(
                    String.format("%s[%s]=%s %s",
                            entry.getKey(),
                            AbstractFileChanger.Params.valueOf(entry.getKey() + "").message(),
                            entry.getValue(),
                            ProgramConstants.LINE_SEPARATOR)
            );
        });
        System.out.println(
                messageHelper.getFullMessage(messageHelper.getFullMessage("argument.details", sb.toString()))
        );
    }

    private void throwProgramExceptionWhenPropertiesValidationError(Properties properties)
            throws ProgramException {
        for (AbstractFileChanger.Params param : AbstractFileChanger.Params.values()) {
            if (properties.getProperty(param.name()) == null || "".equals(properties.getProperty(param.name()))) {
                String errMsg = messageHelper.getFullMessage("argument.wrong.value",
                        param.name(), param.message(), properties.getProperty(param.name()));
                System.err.println(errMsg);
                throw new ProgramException(ErrorCode.ARGUMENT_WRONG_VALUE, errMsg);
            }
        }
    }

    private void displaySourceInfo(Properties properties) {
        System.out.println(messageHelper.getFullMessage("file.source", properties.getProperty(AbstractFileChanger.Params.source.name())));
    }
}
