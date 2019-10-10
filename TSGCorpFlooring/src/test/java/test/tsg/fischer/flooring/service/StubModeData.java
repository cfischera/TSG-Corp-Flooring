package test.tsg.fischer.flooring.service;

import com.tsg.fischer.flooring.dao.ModeAccessor;
import com.tsg.fischer.flooring.dao.PersistenceException;

public class StubModeData implements ModeAccessor {

    private String mode;

    @Override
    public void load() throws PersistenceException {
        if(null == mode) {
            mode = "";
        } else if(mode.equals("Training")) {
            mode = "Production";
        } else if(mode.equals("")) {
            mode = "Training";
        } else
            mode = "";
    }

    @Override
    public String getMode() {
        return this.mode;
    }
}
