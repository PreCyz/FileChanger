package pg.filechanger.service.impl;

import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import pg.exception.ProgramException;
import pg.filechanger.service.AbstractFileService;
import pg.filechanger.dto.ChangeDetails;
import pg.filechanger.dto.FileChangerParams;
import pg.filechanger.validator.ArgsValidator;
import pg.filechanger.validator.impl.FileChangerValidator;
import pg.helper.PropertiesHelper;
import pg.logger.AppLogger;

/**
 * @author Paweł Gawędzki
 */
public class ConsoleFileService extends AbstractFileService {

	private String[] params;
	private ArgsValidator validator;

	public ConsoleFileService(String[] params, ResourceBundle bundle, AppLogger logger) {
        super(logger, bundle);
        this.params = params;
        this.validator = new FileChangerValidator(params, bundle);
    }

	public void run() throws ProgramException {
		validator.validate();
		if (changeDetails == null) {
			changeDetails = createChangeDetails(params);
		}
		createDestinationIfNotExists();
		Map<String, Integer> maxExtIdxMap = createMaxIndexMap(changeDetails);
		processChange(maxExtIdxMap, changeDetails);
	}

    protected ChangeDetails createChangeDetails(String[] parameters) {
        Properties properties = PropertiesHelper.transformArgumentsToProperties(parameters);
        return new ChangeDetails(
                properties.getProperty(FileChangerParams.source.name()),
                properties.getProperty(FileChangerParams.destination.name()),
                properties.getProperty(FileChangerParams.coreName.name()),
                properties.getProperty(FileChangerParams.nameConnector.name()));
    }

}
