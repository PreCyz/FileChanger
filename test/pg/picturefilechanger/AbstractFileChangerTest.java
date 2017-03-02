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
    public void givenAbstractFileChangerImplWhenRunThenProperOrder() throws Exception {
        String expected = "transformArgumentsToProperties,exitOnEmptyProperties"
                + ",displayPropertiesDetails,exitOnPropertiesValidationError"
                + ",displaySourceInfo,createChangeDetails,createDestinationIfNotExists,createMaxIndexMap,processChange,";
        AbstractFileChangerImpl afc = new AbstractFileChangerImpl();
        afc.run();
        assertEquals(expected, afc.getOrder());
    }
    
    @Test
    public void WhenCreateAbstractFileChangerThenMessageHelperNotNull() {
        AbstractFileChangerImpl afc = new AbstractFileChangerImpl();
        assertNotNull("Property messgeHelper should not be null.", afc.messageHelper);
    }

    public class AbstractFileChangerImpl extends AbstractFileChanger {
        
        private String order = "";
        public String getOrder(){
            return order;
        }

        public AbstractFileChangerImpl() {
            super(null, null);
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

    }
    
}
