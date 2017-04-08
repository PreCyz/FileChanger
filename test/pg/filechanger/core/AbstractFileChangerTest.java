package pg.filechanger.core;

import java.util.Map;
import org.junit.Test;
import pg.filechanger.dto.ChangeDetails;
import pg.logger.impl.ConsoleLogger;

import static org.junit.Assert.*;

/**
 *
 * @author Gawa
 */
public class AbstractFileChangerTest {

    @Test
    public void givenAbstractFileChangerImplWhenRunThenProperOrder() throws Exception {
        String expected = "createChangeDetails,createDestinationIfNotExists,createMaxIndexMap,processChange";
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
        String getOrder(){
            return order;
        }

        AbstractFileChangerImpl() {
            super(new String[] {}, null, new ConsoleLogger());
        }

        @Override
        protected ChangeDetails createChangeDetails(String[] parameters) {
            order += String.format("%s,", "createChangeDetails");
            return null;
        }
        
        @Override
        protected void createDestinationIfNotExists() {
            order += String.format("%s,", "createDestinationIfNotExists");
        }
        
        @Override
        public Map<String, Integer> createMaxIndexMap(ChangeDetails changeDetails) {
            order += String.format("%s,", "createMaxIndexMap");
            return null;
        }

        @Override
        public void processChange(Map<String, Integer> maxExtIdxMap, ChangeDetails changeDetails) {
            order += String.format("%s", "processChange");
        }

    }
    
}
