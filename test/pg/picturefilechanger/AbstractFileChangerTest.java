package pg.picturefilechanger;

import java.util.Map;
import java.util.Properties;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gawa
 */
public class AbstractFileChangerTest {

    @Test
    public void givenAbstractFileChangerImplWhenRunThenProperOrder() {
        String expected = "transformArgumentsToProperties,exitOnEmptyProperties"
                + ",displayPropertiesDetails,exitOnPropertiesValidationError"
                + ",displaySourceInfo,createChangeDetails,createDestinationIfNotExists,createMaxIndexMap,processChange,";
        AbstractFileChangerImpl afc = new AbstractFileChangerImpl();
        afc.run();
        assertEquals(expected, afc.getOrder());
    }

    public class AbstractFileChangerImpl extends AbstractFileChanger {
        
        private String order = "";
        public String getOrder(){
            return order;
        }

        public AbstractFileChangerImpl() {
            super(null);
        }

        @Override
        public Properties transformArgumentsToProperties(String[] params) {
            order += String.format("%s,", "transformArgumentsToProperties");
            return null;
        }

        @Override
        public void exitOnEmptyProperties(Properties properties) {
            order += String.format("%s,", "exitOnEmptyProperties");
        }

        @Override
        public void displayPropertiesDetails(Properties properties) {
            order += String.format("%s,", "displayPropertiesDetails");
        }

        @Override
        public void exitOnPropertiesValidationError(Properties properties) {
            order += String.format("%s,", "exitOnPropertiesValidationError");
            
        }

        @Override
        protected ChangeDetails createChangeDetails(Properties properties) {
            order += String.format("%s,", "createChangeDetails");
            return null;
        }
        
        @Override
        protected void createDestinationIfNotExists(ChangeDetails chnageDetails) {
            order += String.format("%s,", "createDestinationIfNotExists");
        }
        
        @Override
        public Map<String, Integer> createMaxIndexMap(Properties properties) {
            order += String.format("%s,", "createMaxIndexMap");
            return null;
        }

        @Override
        public void processChange(Map<String, Integer> maxExtIdxMap, ChangeDetails changeDetails) {
            order += String.format("%s,", "processChange");
        }

        @Override
        protected void displaySourceInfo(Properties properties) {
            order += String.format("%s,", "displaySourceInfo");
        }
    }
    
}
