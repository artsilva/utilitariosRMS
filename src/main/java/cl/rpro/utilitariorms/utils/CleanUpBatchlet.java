/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rpro.utilitariorms.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.AbstractBatchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Artsk
 */
@Named
public class CleanUpBatchlet extends AbstractBatchlet{
     private @Inject
    SingletonRMSBean rmsBean;

    private @Inject
    JobContext jobCtx;

    @Override
    public String process() throws Exception {
        Logger.getLogger(CleanUpBatchlet.class.getName()).log(Level.INFO, "CleanUpBatchlet.process()");
        //limipamos el map debido a que es un SINGLETON , por lo tanto aunque termine el job, el ciclo
        //de vida del singleton continua.
        rmsBean.clearMap();

        return "COMPLETED";
    }
}
